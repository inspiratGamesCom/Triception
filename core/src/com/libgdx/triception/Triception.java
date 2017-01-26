package com.libgdx.triception;

import com.libgdx.triception.screens.MainGameScreen;
import com.badlogic.gdx.Game;

public class Triception extends Game {

    public static final MainGameScreen _mainGameScreen = new MainGameScreen();

    @Override
    public void create(){
        setScreen(_mainGameScreen);
    }

    @Override
    public void dispose(){
        _mainGameScreen.dispose();
    }

}
