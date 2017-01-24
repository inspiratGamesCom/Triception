package com.libgdx.triception.esc;

import com.badlogic.gdx.utils.Json;
import com.libgdx.triception.Entity2;

public class EntityFactory {

    private static Json _json = new Json();

    public static enum EntityType {
        PLAYER,
        DEMO_PLAYER,
        NPC
    }

    public static String PLAYER_CONFIG = "scripts/player.json";

    static public com.libgdx.triception.esc.Entity getEntity(EntityType entityType) {

        com.libgdx.triception.esc.Entity entity = null;

        switch (entityType) {
            case PLAYER:
                entity = new Entity2(
                        new PlayerInputComponent(),
                        new PlayerPhysicsComponent(),
                        new PlayerGraphicsComponent());
                entity.setEntityConfig(
                        com.libgdx.triception.esc.Entity.getEntityConfig(
                                EntityFactory.PLAYER_CONFIG));
                entity.sendMessage(
                        Component.MESSAGE.LOAD_ANIMATIONS,
                        _json.toJson(entity.getEntityConfig()));
                return entity;
            case DEMO_PLAYER:
                entity = new com.libgdx.triception.esc.Entity(
                        new NPCInputComponent(),
                        new PlayerPhysicsComponent(),
                        new PlayerGraphicsComponent());
                return entity;
            case NPC:
                entity = new com.libgdx.triception.esc.Entity(
                        new NPCInputComponent(),
                        new NPCPhysicsComponent(),
                        new NPCGraphicsComponent());
                return entity;
            default:
                return null;
        }
    }
}