package com.libgdx.triception.esc;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

public class EntityConfig {

    private String entityID;
    private Entity.State state;
    private Entity.Direction direction;
    private Array<AnimationConfig> animationConfig;

    public EntityConfig() {
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public void setDirection(Entity.Direction direction) {
        this.direction = direction;
    }

    public void setAnimationConfig(Array<AnimationConfig> animationConfig) {
        this.animationConfig = animationConfig;
    }

    public String getEntityID() {
        return entityID;
    }

    public Entity.State getState() {
        return state;
    }

    public Entity.Direction getDirection() {
        return direction;
    }

    public Array<AnimationConfig> getAnimationConfig() {
        return animationConfig;
    }

    static public class AnimationConfig {

        private float frameDuration = 1.0f;
        private Entity.AnimationType animationType;
        private Array<String> texturePaths;
        private Array<GridPoint2> gridPoints;

        public AnimationConfig() {
            animationType = Entity.AnimationType.IDLE;
            texturePaths = new Array<String>();
            gridPoints = new Array<GridPoint2>();
        }

        public float getFrameDuration() {
            return frameDuration;
        }

        public void setFrameDuration(float frameDuration) {
            this.frameDuration = frameDuration;
        }

        public Array<String> getTexturePaths() {
            return texturePaths;
        }

        public void setTexturePaths(Array<String> texturePaths) {
            this.texturePaths = texturePaths;
        }

        public Array<GridPoint2> getGridPoints() {
            return gridPoints;
        }

        public void setGridPoints(Array<GridPoint2> gridPoints) {
            this.gridPoints = gridPoints;
        }

        public Entity.AnimationType getAnimationType() {
            return animationType;
        }

        public void setAnimationType(Entity.AnimationType animationType) {
            this.animationType = animationType;
        }
    }
}
