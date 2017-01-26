package com.libgdx.triception;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.libgdx.triception.screens.MainGameScreen;

public class Triception implements ApplicationListener{

    public MainGameScreen _mainGameScreen;

    @Override
    public void create() {
        _mainGameScreen = new MainGameScreen();
        _mainGameScreen.show();

    }

    @Override
    public void resize(int width, int height) {
        _mainGameScreen.resize(width, height);
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
