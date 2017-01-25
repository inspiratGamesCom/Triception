package com.libgdx.triception.esc;

import com.badlogic.gdx.utils.Json;
import com.libgdx.triception.esc.Component;

import java.util.HashMap;
import java.util.Map;

public abstract class InputComponent implements Component {

    protected enum Keys {
        LEFT, RIGHT, UP, DOWN, QUIT
    }

    protected enum Mouse {
        SELECT, DOACTION
    }

    protected static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();
    protected static Map<Mouse, Boolean> mouseButtons = new HashMap<Mouse, Boolean>();

    //initialize the hashmap for inputs
    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.UP, false);
        keys.put(Keys.DOWN, false);
        keys.put(Keys.QUIT, false);
    };

    static {
        mouseButtons.put(Mouse.SELECT, false);
        mouseButtons.put(Mouse.DOACTION, false);
    };

    protected Entity.Direction _currentDirection;
    protected Entity.State _currentState;
    protected Json _json;

    protected InputComponent() {
        _json = new Json();
    }

    public abstract void update(Entity entity, float delta);
}
