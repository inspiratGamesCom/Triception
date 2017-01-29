package com.libgdx.triception.esc;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.libgdx.triception.ui.InventoryItem;

public class EntityConfig {

    Array<AnimationConfig> animationConfig;
    Array<InventoryItem.ItemTypeID> inventory;
    Entity.State state = Entity.State.IDLE;
    Entity.Direction direction = Entity.Direction.DOWN;
    String entityID;

    EntityConfig() {
        animationConfig = new Array<AnimationConfig>();
        inventory = new Array<InventoryItem.ItemTypeID>();
    }

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public Entity.Direction getDirection() {
        return direction;
    }

    public void setDirection(Entity.Direction direction) {
        this.direction = direction;
    }

    public Entity.State getState() {
        return state;
    }

    public void setState(Entity.State state) {
        this.state = state;
    }

    public Array<AnimationConfig> getAnimationConfig() {
        return animationConfig;
    }

    public void addAnimationConfig(AnimationConfig animationConfig) {
        this.animationConfig.add(animationConfig);
    }

    public Array<InventoryItem.ItemTypeID> getInventory() {
        return inventory;
    }

    public void setInventory(Array<InventoryItem.ItemTypeID> inventory) {
        this.inventory = inventory;
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
