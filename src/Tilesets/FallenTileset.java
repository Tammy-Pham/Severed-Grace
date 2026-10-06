package Tilesets;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import Level.TileType;
import Level.Tileset;
import java.util.ArrayList;

public class FallenTileset extends Tileset {

    public FallenTileset() {
        super(ImageLoader.load("Fallen_Tileset.png"), 64, 64, 0);
    }

    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        tileScale = 1;
        ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

        // Brickwall
        Frame brickwallFrame = new FrameBuilder(getSubImage(0, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder brickwallTile = new MapTileBuilder(brickwallFrame).withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(brickwallTile);

        // Brickwall with moss
        Frame mossBrickwallFrame = new FrameBuilder(getSubImage(1, 0))
                .withScale(tileScale)
                .build();
            

    
        MapTileBuilder mossBrickwallTile = new MapTileBuilder(mossBrickwallFrame).withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(mossBrickwallTile);

        // Floor blank
        Frame floorFrame = new FrameBuilder(getSubImage(2, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder floorTile = new MapTileBuilder(floorFrame).withTileType(TileType.PASSABLE);

        mapTiles.add(floorTile);

        // Floor impassable
        Frame floorImpassableFrame = new FrameBuilder(getSubImage(3, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder floorImpassibleTile = new MapTileBuilder(floorImpassableFrame).withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(floorImpassibleTile);

        //Floor 3 rocks
        Frame floorThreeRocksFrame = new FrameBuilder(getSubImage(4,0))
                .withScale(tileScale)
                .build();
        
        MapTileBuilder floorThreeRocksTile = new MapTileBuilder(floorThreeRocksFrame).withTileType(TileType.PASSABLE);

        mapTiles.add(floorThreeRocksTile);

        // Floor 2 Rocks
        Frame floorTwoRocksFrame = new FrameBuilder(getSubImage(5,0))
                .withScale(tileScale)
                .build();
        
        MapTileBuilder floorTwoRocksTile = new MapTileBuilder(floorTwoRocksFrame).withTileType(TileType.PASSABLE);

        mapTiles.add(floorTwoRocksTile);

        // Floor 1 Rock
        Frame floorOneRockFrame = new FrameBuilder(getSubImage(6,0))
                .withScale(tileScale)
                .build();
        
        MapTileBuilder floorOneRockTile = new MapTileBuilder(floorOneRockFrame).withTileType(TileType.PASSABLE);

        mapTiles.add(floorOneRockTile);
        return mapTiles;
    }
}
