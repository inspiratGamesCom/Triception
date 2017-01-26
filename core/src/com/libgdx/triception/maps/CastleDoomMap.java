package com.libgdx.triception.maps;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.libgdx.triception.esc.Entity;

public class CastleDoomMap extends Map{
    private static String _mapPath = "maps/castle_of_doom.tmx";

    CastleDoomMap(){
        super(MapFactory.MapType.CASTLE_OF_DOOM, _mapPath);
    }

    @Override
    public void updateMapEntities(MapManager mapMgr, Batch batch, float delta){
        for( Entity entity : _mapEntities ){
            entity.update(mapMgr, batch, delta);
        }
    }

}
