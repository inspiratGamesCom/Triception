package com.libgdx.triception.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.compression.lz.BinTree;
import com.libgdx.triception.Triception;
import com.libgdx.triception.Utility;
import com.libgdx.triception.profiles.ProfileManager;

public class LoadGameScreen implements Screen {

    private Stage _stage;
    private Triception _game;

    public LoadGameScreen(Triception game) {
        _game = game;

        //create
        _stage = new Stage();
        TextButton loadButton = new TextButton("Load", Utility.STATUSUI_SKIN);
        TextButton backButton = new TextButton("Back", Utility.STATUSUI_SKIN);

        ProfileManager.getInstance().storeAllProfiles();
        Array<String> list = ProfileManager.getInstance().getProfileList();
        final List listItems = new List(Utility.STATUSUI_SKIN, "inventory");
        listItems.setItems(list);
        ScrollPane scrollPane = new ScrollPane(listItems);

        scrollPane.setOverscroll(false, false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setScrollbarsOnTop(true);

        Table table = new Table();
        Table bottomTable = new Table();

        //Layout
        table.center();
        table.setFillParent(true);
        table.padBottom(loadButton.getHeight());
        table.add(scrollPane).center();

        bottomTable.setHeight(loadButton.getHeight());
        bottomTable.setWidth(Gdx.graphics.getWidth());
        bottomTable.center();
        bottomTable.add(loadButton).padRight(50);
        bottomTable.add(backButton);

        _stage.addActor(table);
        _stage.addActor(bottomTable);

        //Listeners
        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                _game.setScreen(_game.getScreenType(Triception.ScreenType.MainMenu));
                return true;
            }
        }
        );

        loadButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                String fileName = listItems.getSelected().toString();
                if (fileName != null) {
                    FileHandle file = ProfileManager.getInstance().getProfileFile(fileName);
                    if (file != null) {
                        ProfileManager.getInstance().setCurrentProfile(fileName);
                        ProfileManager.getInstance().loadProfile();
                        _game.setScreen(_game.getScreenType(Triception.ScreenType.MainGame));
                    }
                }
                return true;
            }
        }
        );
    }

    @Override
    public void render(float delta) {
        if (delta == 0) {
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        _stage.act(delta);
        _stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        _stage.getViewport().setScreenSize(width, height);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(_stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        _stage.clear();
        _stage.dispose();
    }

}
