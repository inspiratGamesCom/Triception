package com.libgdx.triception.esc;

import java.util.ArrayList;

public class EntityConfig {

    String entityID;
    com.libgdx.triception.esc.Entity.State state;
    com.libgdx.triception.esc.Entity.Direction direction;
    ArrayList<AnimationConfig> animationConfig;

    public EntityConfig() {}

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public void setDirection(com.libgdx.triception.esc.Entity.Direction direction) {
        this.direction = direction;
    }

    public void setAnimationConfig(ArrayList<AnimationConfig> animationConfig) {
        this.animationConfig = animationConfig;
    }

    public String getEntityID() {

        return entityID;
    }

    public com.libgdx.triception.esc.Entity.State getState() {
        return state;
    }

    public com.libgdx.triception.esc.Entity.Direction getDirection() {
        return direction;
    }

    public ArrayList<AnimationConfig> getAnimationConfig() {
        return animationConfig;
    }
}
