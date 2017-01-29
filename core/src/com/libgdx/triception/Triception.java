package com.libgdx.triception;

import com.badlogic.gdx.Screen;
import com.libgdx.triception.screens.LoadGameScreen;
import com.libgdx.triception.screens.MainGameScreen;
import com.badlogic.gdx.Game;
import com.libgdx.triception.screens.MainMenuScreen;
import com.libgdx.triception.screens.NewGameScreen;

public class Triception extends Game {

    private static MainGameScreen _mainGameScreen;
    private static MainMenuScreen _mainMenuScreen;
    private static LoadGameScreen _loadGameScreen;
    private static NewGameScreen _newGameScreen;

    public static enum ScreenType {
        MainMenu,
        MainGame,
        LoadGame,
        NewGame
    }

    public Screen getScreenType(ScreenType screenType) {
        switch (screenType) {
            case MainMenu:
                return _mainMenuScreen;
            case MainGame:
                return _mainGameScreen;
            case LoadGame:
                return _loadGameScreen;
            case NewGame:
                return _newGameScreen;
            default:
                return _mainMenuScreen;
        }

    }

    @Override
    public void create() {
        _mainGameScreen = new MainGameScreen(this);
        _mainMenuScreen = new MainMenuScreen(this);
        _loadGameScreen = new LoadGameScreen(this);
        _newGameScreen = new NewGameScreen(this);
        setScreen(_mainMenuScreen);
    }

    @Override
    public void dispose() {
        _mainGameScreen.dispose();
        _mainMenuScreen.dispose();
        _loadGameScreen.dispose();
        _newGameScreen.dispose();
    }

}
