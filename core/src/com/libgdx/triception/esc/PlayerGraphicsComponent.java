package com.libgdx.triception.esc;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.libgdx.triception.maps.MapManager;

public class PlayerGraphicsComponent extends GraphicsComponent {

    private static final String TAG = PlayerGraphicsComponent.class.getSimpleName();

    public PlayerGraphicsComponent() {
    }

    @Override
    public void receiveMessage(String message) {
        //Gdx.app.debug(TAG, "Got message " + message);
        String[] string = message.split(MESSAGE_TOKEN);

        if (string.length == 0) return;

        //Specifically for messages with 1 object payload
        if (string.length == 2) {
            if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_POSITION.toString())) {
                _currentPosition = _json.fromJson(Vector2.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_START_POSITION.toString())) {
                _currentPosition = _json.fromJson(Vector2.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_STATE.toString())) {
                _currentState = _json.fromJson(Entity.State.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_DIRECTION.toString())) {
                _currentDirection = _json.fromJson(Entity.Direction.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.LOAD_ANIMATIONS.toString())) {
                EntityConfig entityConfig = _json.fromJson(EntityConfig.class, string[1]);
                String textureName = entityConfig.getTextureAtlasPath();
                Array<EntityConfig.AnimationConfig> animationConfigs = entityConfig.getAnimationConfig();

                for (EntityConfig.AnimationConfig animationConfig : animationConfigs) {
                    String regionsName = animationConfig.getAtlasRegionsName();
                    Entity.AnimationType animationType = animationConfig.getAnimationType();
                    float frameDuration = animationConfig.getFrameDuration();
                    Animation animation = loadAnimation(textureName, regionsName, frameDuration);

                    _animations.put(animationType, animation);
                }
            }
        }
    }

    @Override
    public void update(Entity entity, MapManager mapMgr, Batch batch, float delta) {
        updateAnimations(delta);

        Camera camera = mapMgr.getCamera();
        camera.position.set(_currentPosition.x, _currentPosition.y, 0f);
        camera.update();

        batch.begin();
        if (_currentDirection == Entity.Direction.LEFT)
            batch.draw(_currentFrame, _currentPosition.x, _currentPosition.y, 0.5f, 0.5f, 1, 1, -1, 1, 0);
        else
            batch.draw(_currentFrame, _currentPosition.x, _currentPosition.y, 1, 1);
        batch.end();

        //Used to graphically debug boundingboxes
        /*
        Rectangle rect = entity.getCurrentBoundingBox();
        _shapeRenderer.setProjectionMatrix(camera.combined);
        _shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        _shapeRenderer.setColor(Color.RED);
        _shapeRenderer.rect(rect.getX() * Map.UNIT_SCALE , rect.getY() * Map.UNIT_SCALE, rect.getWidth() * Map.UNIT_SCALE, rect.getHeight()*Map.UNIT_SCALE);
        _shapeRenderer.end();
        */
    }

    @Override
    public void dispose() {
    }

}
