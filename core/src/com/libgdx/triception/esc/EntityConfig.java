package com.libgdx.triception.esc;

import com.badlogic.gdx.utils.Array;
import com.libgdx.triception.ui.InventoryItem;

public class EntityConfig {

    private Array<AnimationConfig> animationConfig;
    private Array<InventoryItem.ItemTypeID> inventory;
    private Entity.State state = Entity.State.IDLE;
    private Entity.Direction direction = Entity.Direction.DOWN;
    private String entityID;
    private String textureAtlasPath;
    private String conversationConfigPath;

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

    public String getTextureAtlasPath() {
        return textureAtlasPath;
    }

    public void setTextureAtlasPath(String textureAtlasPath) {
        this.textureAtlasPath = textureAtlasPath;
    }

    public String getConversationConfigPath() {
        return conversationConfigPath;
    }

    public void setConversationConfigPath(String conversationConfigPath) {
        this.conversationConfigPath = conversationConfigPath;
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
        private String atlasRegionsName;

        public AnimationConfig() {
            animationType = Entity.AnimationType.IDLE;
        }

        public float getFrameDuration() {
            return frameDuration;
        }

        public void setFrameDuration(float frameDuration) {
            this.frameDuration = frameDuration;
        }

        public String getAtlasRegionsName() {
            return atlasRegionsName;
        }

        public void setAtlasRegionsName(String atlasRegionsName) {
            this.atlasRegionsName = atlasRegionsName;
        }

        public Entity.AnimationType getAnimationType() {
            return animationType;
        }

        public void setAnimationType(Entity.AnimationType animationType) {
            this.animationType = animationType;
        }
    }

}
