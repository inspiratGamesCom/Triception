package com.libgdx.triception.esc;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.libgdx.triception.Utility;
import com.libgdx.triception.maps.MapManager;

import java.util.Hashtable;

public abstract class GraphicsComponent implements Component {
    protected TextureRegion _currentFrame = null;
    protected float _frameTime = 0f;
    protected Entity.State _currentState;
    protected Entity.Direction _currentDirection;
    protected Json _json;
    protected Vector2 _currentPosition;
    protected Hashtable<Entity.AnimationType, Animation> _animations;
    protected ShapeRenderer _shapeRenderer;

    protected GraphicsComponent(){
        _currentPosition = new Vector2(0,0);
        _currentState = Entity.State.WALKING;
        _currentDirection = Entity.Direction.DOWN;
        _json = new Json();
        _animations = new Hashtable<Entity.AnimationType, Animation>();
        _shapeRenderer = new ShapeRenderer();
    }

    public abstract void update(Entity entity, MapManager mapManager, Batch batch, float delta);

    protected void updateAnimations(float delta){
        _frameTime = (_frameTime + delta)%5; //Want to avoid overflow

        //Look into the appropriate variable when changing position
        switch (_currentDirection) {
            case DOWN:
                if (_currentState == Entity.State.WALKING) {
                    Animation animation = _animations.get(Entity.AnimationType.WALK_DOWN);
                    if( animation == null ) return;
                    _currentFrame = (TextureRegion) animation.getKeyFrame(_frameTime);
                } else if(_currentState == Entity.State.IDLE) {
                    Animation animation = _animations.get(Entity.AnimationType.WALK_DOWN);
                    if( animation == null ) return;
                    _currentFrame = (TextureRegion) animation.getKeyFrames()[0];
                } else if(_currentState == Entity.State.IMMOBILE) {
                    Animation animation = _animations.get(Entity.AnimationType.IMMOBILE);
                    if( animation == null ) return;
                    _currentFrame = (TextureRegion) animation.getKeyFrame(_frameTime);
                }
                break;
            case UP:
                if (_currentState == Entity.State.WALKING) {
                    Animation animation = _animations.get(Entity.AnimationType.WALK_UP);
                    if( animation == null ) return;
                    _currentFrame = (TextureRegion) animation.getKeyFrame(_frameTime);
                } else if(_currentState == Entity.State.IDLE) {
                    Animation animation = _animations.get(Entity.AnimationType.WALK_UP);
                    if( animation == null ) return;
                    _currentFrame = (TextureRegion) animation.getKeyFrames()[0];
                } else if(_currentState == Entity.State.IMMOBILE) {
                    Animation animation = _animations.get(Entity.AnimationType.IMMOBILE);
                    if( animation == null ) return;
                    _currentFrame = (TextureRegion) animation.getKeyFrame(_frameTime);
                }
                break;
            case RIGHT:
            case LEFT:
                if (_currentState == Entity.State.WALKING) {
                    Animation animation = _animations.get(Entity.AnimationType.WALK_RIGHT);
                    if( animation == null ) return;
                    _currentFrame = (TextureRegion) animation.getKeyFrame(_frameTime);
                } else if(_currentState == Entity.State.IDLE) {
                    Animation animation = _animations.get(Entity.AnimationType.WALK_RIGHT);
                    if( animation == null ) return;
                    _currentFrame = (TextureRegion) animation.getKeyFrames()[0];
                } else if(_currentState == Entity.State.IMMOBILE) {
                    Animation animation = _animations.get(Entity.AnimationType.IMMOBILE);
                    if( animation == null ) return;
                    _currentFrame = (TextureRegion) animation.getKeyFrame(_frameTime);
                }
                break;
            default:
                break;
        }
    }

    protected Animation loadAnimation(String textureAtlasPath, String atlasRegionsName, float frameDuration){

        Utility.loadTextureAtlas(textureAtlasPath);
        TextureAtlas atlas = Utility.getTextureAtlas(textureAtlasPath);

        Array<AtlasRegion> regions = atlas.findRegions(atlasRegionsName);

        return new Animation(frameDuration, regions, Animation.PlayMode.LOOP);
    }
}
