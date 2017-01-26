package com.libgdx.triception.maps;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.libgdx.triception.esc.Entity;

public class TopWorldMap extends Map{
    private static String _mapPath = "maps/topworld.tmx";

    TopWorldMap(){
        super(MapFactory.MapType.TOP_WORLD, _mapPath);
    }

    @Override
    public void updateMapEntities(MapManager mapMgr, Batch batch, float delta){
        for( Entity entity : _mapEntities ){
            entity.update(mapMgr, batch, delta);
        }
    }
}
