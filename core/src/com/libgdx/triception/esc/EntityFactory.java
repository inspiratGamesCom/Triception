package com.libgdx.triception.esc;

import com.badlogic.gdx.utils.Json;

public class EntityFactory {

    private static final String TAG = EntityFactory.class.getSimpleName();

    public static String PLAYER_CONFIG = "scripts/player.json";
    public static EntityFactory _instance = new EntityFactory();

    public static enum EntityType {
        PLAYER,
        DEMO_PLAYER,
        NPC
    }

    private Json _json = new Json();

    private EntityFactory() {
        this._json = new Json();
    }

    public Entity getEntity(EntityType entityType) {

        Entity entity = null;

        switch (entityType) {
            case PLAYER:
                entity = new Entity(
                        new PlayerInputComponent(),
                        new PlayerPhysicsComponent(),
                        new PlayerGraphicsComponent());
                entity.setEntityConfig(Entity.getEntityConfig(EntityFactory.PLAYER_CONFIG));
                entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS, _json.toJson(entity.getEntityConfig()));
                return entity;
            case DEMO_PLAYER:
                entity = new Entity(
                        new NPCInputComponent(),
                        new PlayerPhysicsComponent(),
                        new PlayerGraphicsComponent());
                return entity;
            case NPC:
                entity = new Entity(
                        new NPCInputComponent(),
                        new NPCPhysicsComponent(),
                        new NPCGraphicsComponent());
                return entity;
            default:
                return null;
        }
    }
}