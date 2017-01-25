package com.libgdx.triception.maps;

public class TopWorldMap extends Map {

    private static String _mapPath = "maps/topworld.tmx";

    TopWorldMap() {
        super(MapFactory.MapType.TOP_WORLD, _mapPath);
    }
}