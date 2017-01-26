package com.libgdx.triception.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Json;
import com.libgdx.triception.esc.Component;
import com.libgdx.triception.esc.Entity;
import com.libgdx.triception.esc.EntityFactory;
import com.libgdx.triception.maps.Map;
import com.libgdx.triception.maps.MapFactory;
import com.libgdx.triception.maps.MapManager;

public class MainGameScreen implements Screen {

    private static final String TAG = MainGameScreen.class.getSimpleName();

    private static class VIEWPORT {
        static float viewportWidth;
        static float viewportHeight;
        static float virtualWidth;
        static float virtualHeight;
        static float physicalWidth;
        static float physicalHeight;
        static float aspectRatio;
    }

    protected OrthogonalTiledMapRenderer _mapRenderer = null;
    protected MapManager _mapMgr;
    protected OrthographicCamera _camera = null;

    private Json _json;

    private Entity _player;

    public MainGameScreen() {

        _mapMgr = new MapManager();
        _json = new Json();

        //_camera setup
        setupViewport(10, 10);

        //get the current size
        _camera = new OrthographicCamera();
        _camera.setToOrtho(false, VIEWPORT.viewportWidth, VIEWPORT.viewportHeight);

        _player = EntityFactory._instance.getEntity(EntityFactory.EntityType.PLAYER);
        _mapMgr.setPlayer(_player);
        _mapMgr.setCamera(_camera);

       // _multiplexer = new InputMultiplexer();
       // _multiplexer.addProcessor(_player.getInputProcessor());
     //   Gdx.input.setInputProcessor(_player);

        //Gdx.app.debug(TAG, "UnitScale value is: " + _mapRenderer.getUnitScale());
    }

    @Override
    public void show() {

       // Gdx.input.setInputProcessor(_multiplexer);


        if (_mapRenderer == null) {
            _mapRenderer = new OrthogonalTiledMapRenderer(_mapMgr.getCurrentTiledMap(), Map.UNIT_SCALE);
        }
    }

    @Override
    public void hide() {

        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _mapRenderer.setView(_camera);

        _mapRenderer.getBatch().enableBlending();
        _mapRenderer.getBatch().setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        if (_mapMgr.hasMapChanged()) {
            _mapRenderer.setMap(_mapMgr.getCurrentTiledMap());
            _player.sendMessage(Component.MESSAGE.INIT_START_POSITION, _json.toJson(_mapMgr.getPlayerStartUnitScaled()));

            _camera.position.set(_mapMgr.getPlayerStartUnitScaled().x, _mapMgr.getPlayerStartUnitScaled().y, 0f);
            _camera.update();

            _mapMgr.setMapChanged(false);
        }


        _mapRenderer.getBatch().begin();
        TiledMapTileLayer backgroundMapLayer = (TiledMapTileLayer) _mapMgr.getCurrentTiledMap().getLayers().get(Map.BACKGROUND_LAYER);
        if (backgroundMapLayer != null) {
            _mapRenderer.renderTileLayer(backgroundMapLayer);


            TiledMapTileLayer groundMapLayer = (TiledMapTileLayer) _mapMgr.getCurrentTiledMap().getLayers().get(Map.GROUND_LAYER);
            if (groundMapLayer != null) {
                _mapRenderer.renderTileLayer(groundMapLayer);
            }

            TiledMapTileLayer decorationMapLayer = (TiledMapTileLayer) _mapMgr.getCurrentTiledMap().getLayers().get(Map.DECORATION_LAYER);
            if (decorationMapLayer != null) {
                _mapRenderer.renderTileLayer(decorationMapLayer);
            }

            _mapRenderer.getBatch().end();

            _mapMgr.updateCurrentMapEntities(_mapMgr, _mapRenderer.getBatch(), delta);
            _player.update(_mapMgr, _mapRenderer.getBatch(), delta);

            _mapRenderer.getBatch().begin();
            _mapRenderer.getBatch().setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ONE_MINUS_SRC_ALPHA);

            _mapRenderer.getBatch().setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            _mapRenderer.getBatch().end();

        } else {
            _mapRenderer.render();
            _mapMgr.updateCurrentMapEntities(_mapMgr, _mapRenderer.getBatch(), delta);
            _player.update(_mapMgr, _mapRenderer.getBatch(), delta);
        }


    }

    @Override
    public void resize(int width, int height) {

        setupViewport(10, 10);
        _camera.setToOrtho(false, VIEWPORT.viewportWidth, VIEWPORT.viewportHeight);
        /*camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        camera.update();

        cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
        cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float) height) * (float) width;
        cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
        cameraGUI.update();*/
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        if (_player != null) {
            _player.dispose();
        }

        if (_mapRenderer != null) {
            _mapRenderer.dispose();
        }
        MapFactory.clearCache();
    }

    private void setupViewport(int width, int height) {
        //Make the viewport a percentage of the total display area
        VIEWPORT.virtualWidth = width;
        VIEWPORT.virtualHeight = height;

        //Current viewport dimensions
        VIEWPORT.viewportWidth = VIEWPORT.virtualWidth;
        VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;

        //pixel dimensions of display
        VIEWPORT.physicalWidth = Gdx.graphics.getWidth();
        VIEWPORT.physicalHeight = Gdx.graphics.getHeight();

        //aspect ratio for current viewport
        VIEWPORT.aspectRatio = (VIEWPORT.virtualWidth / VIEWPORT.virtualHeight);

        //update viewport if there could be skewing
        if (VIEWPORT.physicalWidth / VIEWPORT.physicalHeight >= VIEWPORT.aspectRatio) {
            //Letterbox left and right
            VIEWPORT.viewportWidth = VIEWPORT.viewportHeight * (VIEWPORT.physicalWidth / VIEWPORT.physicalHeight);
            VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;
        } else {
            //letterbox above and below
            VIEWPORT.viewportWidth = VIEWPORT.virtualWidth;
            VIEWPORT.viewportHeight = VIEWPORT.viewportWidth * (VIEWPORT.physicalHeight / VIEWPORT.physicalWidth);
        }

        Gdx.app.debug(TAG, "WorldRenderer: virtual: (" + VIEWPORT.virtualWidth + "," + VIEWPORT.virtualHeight + ")");
        Gdx.app.debug(TAG, "WorldRenderer: viewport: (" + VIEWPORT.viewportWidth + "," + VIEWPORT.viewportHeight + ")");
        Gdx.app.debug(TAG, "WorldRenderer: physical: (" + VIEWPORT.physicalWidth + "," + VIEWPORT.physicalHeight + ")");
    }
}






