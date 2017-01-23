package com.libgdx.triception;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.libgdx.triception.screens.MainGameScreen;

public class Triception implements ApplicationListener{

    public static final MainGameScreen _mainGameScreen = new MainGameScreen();

    @Override
    public void create() {
        _mainGameScreen.show();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        _mainGameScreen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        _mainGameScreen.dispose();
    }
}
