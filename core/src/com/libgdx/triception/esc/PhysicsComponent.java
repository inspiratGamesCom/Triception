package com.libgdx.triception.esc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.libgdx.triception.maps.MapManager;

public abstract class PhysicsComponent implements Component {

    private static final String TAG = PhysicsComponent.class.getSimpleName();

    protected Vector2 _currentEntityPosition;
    protected Vector2 _nextEntityPosition;
    protected Vector2 _velocity;
    protected Entity.Direction _currentDirection;
    public Rectangle _boundingBox;
    protected BoundingBoxLocation _boundingBoxLocation;
    protected Json _json;

    public static enum BoundingBoxLocation {
        BOTTOM_LEFT,
        BOTTOM_CENTER,
        CENTER,
    }

    protected PhysicsComponent() {
        this._nextEntityPosition = new Vector2(0,0);
        this._currentEntityPosition = new Vector2(0,0);
        this._velocity = new Vector2(2f,2f);
        this._boundingBox = new Rectangle();
        this._boundingBoxLocation = BoundingBoxLocation.BOTTOM_LEFT;
        this._json = new Json();
    }

    public abstract void update(Entity entity, MapManager mapMgr, float delta);

    protected boolean isCollisionWithMapEntities(Entity entity, MapManager mapMgr) {

        Array<Entity> entities = mapMgr.getCurrentMapEntities();

        boolean isCollisionWithMapEntities = false;
        for (Entity mapEntity : entities) {
//Check for testing against self
            if (mapEntity.equals(entity)) {
                continue;
            }
            Rectangle targetRect = mapEntity.getCurrentBoundingBox();

            if (_boundingBox.overlaps(targetRect)) {
//Collision
                entity.sendMessage(MESSAGE.COLLISION_WITH_ENTITY);
                isCollisionWithMapEntities = true;
                break;
            }
        }
        return isCollisionWithMapEntities;
    }

    protected boolean isCollision(Entity entitySource, Entity entityTarget) {

        boolean isCollision = false;
        if (entitySource.equals(entityTarget)) {
            return false;
        }
        if (entitySource.getCurrentBoundingBox().overlaps(entityTarget.getCurrentBoundingBox())) {
//Collision
            entitySource.sendMessage(MESSAGE.COLLISION_WITH_ENTITY);
            isCollision = true;
        }
        return isCollision;
    }

    protected boolean isCollisionWithMapLayer(Entity entity, MapManager mapMgr){
        MapLayer mapCollisionLayer =  mapMgr.getCollisionLayer();

        if( mapCollisionLayer == null )
            return false;

        Rectangle rectangle = null;

        for( MapObject object: mapCollisionLayer.getObjects()){
            if(object instanceof RectangleMapObject) {
                rectangle = ((RectangleMapObject)object).getRectangle();
                if( _boundingBox.overlaps(rectangle) ){

                    entity.sendMessage(MESSAGE.COLLISION_WITH_MAP);
                    return true;
                }
            }
        }
        return false;
    }

    protected void setNextPositionToCurrent(Entity entity){
        this._currentEntityPosition.x = _nextEntityPosition.x;
        this._currentEntityPosition.y = _nextEntityPosition.y;

        entity.sendMessage(MESSAGE.CURRENT_POSITION, _json.toJson(_currentEntityPosition));
    }

    protected void calculateNextPosition(float deltaTime){
        if( _currentDirection == null ) return;

        if( deltaTime > .7) return;

        float testX = _currentEntityPosition.x;
        float testY = _currentEntityPosition.y;

        _velocity.scl(deltaTime);

        switch (_currentDirection) {
            case LEFT :
                testX -=  _velocity.x;
                break;
            case RIGHT :
                testX += _velocity.x;
                break;
            case UP :
                testY += _velocity.y;
                break;
            case DOWN :
                testY -= _velocity.y;
                break;
            default:
                break;
        }

        _nextEntityPosition.x = testX;
        _nextEntityPosition.y = testY;

        //velocity
        _velocity.scl(1 / deltaTime);
    }

    protected void initBoundingBox(float percentageWidthReduced, float percentageHeightReduced) {
//Update the current bounding box
        float width;
        float height;
        float origWidth = com.libgdx.triception.esc.Entity.FRAME_WIDTH;
        float origHeight = com.libgdx.triception.esc.Entity.FRAME_HEIGHT;
//.8f for 20% (1 - .20)
        float widthReductionAmount = 1.0f - percentageWidthReduced;
//.8f for 20% (1 - .20)
        float heightReductionAmount = 1.0f - percentageHeightReduced;

        if (widthReductionAmount > 0 && widthReductionAmount < 1) {
            width = com.libgdx.triception.esc.Entity.FRAME_WIDTH * widthReductionAmount;
        } else {
            width = com.libgdx.triception.esc.Entity.FRAME_WIDTH;
        }
        if (heightReductionAmount > 0 && heightReductionAmount < 1) {
            height = com.libgdx.triception.esc.Entity.FRAME_HEIGHT * heightReductionAmount;
        } else {
            height = com.libgdx.triception.esc.Entity.FRAME_HEIGHT;
        }
        if (width == 0 || height == 0) {
            Gdx.app.debug(TAG, "Width and Height are 0!! " + width + ":" + height);
        }
//Need to account for the unitscale, since the map
//coordinates will be in pixels
        float minX;
        float minY;
        if (MapManager.UNIT_SCALE > 0) {
            minX = _nextEntityPosition.x / MapManager.UNIT_SCALE;
            minY = _nextEntityPosition.y / MapManager.UNIT_SCALE;
        } else {
            minX = _nextEntityPosition.x;
            minY = _nextEntityPosition.y;
        }
        _boundingBox.setWidth(width);
        _boundingBox.setHeight(height);
        switch (_boundingBoxLocation) {
            case BOTTOM_LEFT:
                _boundingBox.set(minX, minY, width, height);
                break;
            case BOTTOM_CENTER:
                _boundingBox.setCenter(minX + origWidth / 2, minY + origHeight / 4);
                break;
            case CENTER:
                _boundingBox.setCenter(minX + origWidth / 2, minY + origHeight / 2);
                break;
        }
    }

    protected void updateBoundingBoxPosition(Vector2 position) {
//Need to account for the unitscale, since the map
//coordinates will be in pixels
        float minX;
        float minY;
        if (MapManager.UNIT_SCALE > 0) {
            minX = position.x / MapManager.UNIT_SCALE;
            minY = position.y / MapManager.UNIT_SCALE;
        } else {
            minX = position.x;
            minY = position.y;
        }
        switch (_boundingBoxLocation) {
            case BOTTOM_LEFT:
                _boundingBox.set(minX, minY, _boundingBox.getWidth(), _boundingBox.getHeight());
                break;
            case BOTTOM_CENTER:
                _boundingBox.setCenter(minX + com.libgdx.triception.esc.Entity.FRAME_WIDTH / 2, minY + Entity.FRAME_HEIGHT / 4);
                break;
            case CENTER:
                _boundingBox.setCenter(minX + com.libgdx.triception.esc.Entity.FRAME_WIDTH / 2, minY + Entity.FRAME_HEIGHT / 2);
                break;
        }
    }
}
