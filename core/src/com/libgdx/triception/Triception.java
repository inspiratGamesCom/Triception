package com.libgdx.triception;

import com.badlogic.gdx.ApplicationAdapter;

public class Triception extends ApplicationAdapter {

    public static final MainGameScreen _mainGameScreen = new MainGameScreen();

    @Override
    public void create() {
        setScreen(_mainGameScreen);
    }

    @Override
    public void dispose() {
        _mainGameScreen.dispose();
    }
}
