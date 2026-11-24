package Tilesets;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import Level.TileType;
import Level.Tileset;
import java.util.ArrayList;

public class FallenTileset extends Tileset {
    public static final int OPEN_EXIT_DOOR_TILE_INDEX = 7;
    public static final int CLOSED_EXIT_DOOR_TILE_INDEX = 8;

    public FallenTileset() {
        super(ImageLoader.load("Fallen_Tileset.png"), 64, 64, 0);
    }

    // builds a tile from a row of the tileset image
    private MapTileBuilder tile(int imageRow, TileType tileType) {
        Frame frame = new FrameBuilder(getSubImage(imageRow, 0))
                .withScale(tileScale)
                .build();
        return new MapTileBuilder(frame).withTileType(tileType);
    }

    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        tileScale = 1;
        ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();
        TileType wall = TileType.NOT_PASSABLE;
        TileType floor = TileType.PASSABLE;

        // Level 1: tan brick + dirt
        mapTiles.add(tile(0, wall));    // 0  brick wall
        mapTiles.add(tile(1, wall));    // 1  brick wall with moss
        mapTiles.add(tile(2, floor));   // 2  dirt floor
        mapTiles.add(tile(3, floor));    // 3  dirt floor, impassable
        mapTiles.add(tile(4, floor));   // 4  dirt floor, 3 rocks
        mapTiles.add(tile(5, floor));   // 5  dirt floor, 2 rocks
        mapTiles.add(tile(6, floor));   // 6  dirt floor, 1 rock
        mapTiles.add(tile(7, floor));   // 7  open door
        mapTiles.add(tile(8, wall));    // 8  closed door
        mapTiles.add(tile(9, wall));    // 9  black (scene maps)
        mapTiles.add(tile(10, floor));  // 10 mossy stone floor

        // Level 2: dark brick
        mapTiles.add(tile(11, wall));   // 11 dark brick wall
        mapTiles.add(tile(12, wall));   // 12 dark brick wall, lava drips
        mapTiles.add(tile(13, wall));   // 13 dark brick wall, cracked
        mapTiles.add(tile(14, floor));  // 14 dark floor, rock
        mapTiles.add(tile(15, floor));  // 15 dark floor, plain
        mapTiles.add(tile(16, floor));  // 16 dark floor, scratches

        // Level 3: grey stone
        mapTiles.add(tile(17, wall));   // 17 grey stone wall
        mapTiles.add(tile(18, wall));   // 18 grey stone wall, vines
        mapTiles.add(tile(19, floor));  // 19 grey floor, mossy rock patch
        mapTiles.add(tile(20, floor));  // 20 grey floor, plain
        mapTiles.add(tile(21, floor));  // 21 grey floor, vine + grass

        // Level 4: heaven marble
        mapTiles.add(tile(22, wall));   // 22 marble pillar wall
        mapTiles.add(tile(23, wall));   // 23 marble wall, golden angel
        mapTiles.add(tile(24, floor));  // 24 marble floor, top trim
        mapTiles.add(tile(25, floor));  // 25 marble floor, plain

        // Level 5: lava cave
        mapTiles.add(tile(26, wall));   // 26 lavafall wall
        mapTiles.add(tile(27, wall));   // 27 cave wall, red crack
        mapTiles.add(tile(28, floor));  // 28 cave floor, plain
        mapTiles.add(tile(29, floor));  // 29 cave floor, lava crack
        mapTiles.add(tile(30, floor));  // 30 cave floor, smudges

        // Level 6: overgrown heaven
        mapTiles.add(tile(31, wall));   // 31 vine wall, golden statue
        mapTiles.add(tile(32, wall));   // 32 vine pillar wall
        mapTiles.add(tile(33, floor));  // 33 marble floor, vines + crack
        mapTiles.add(tile(34, floor));  // 34 marble floor, overgrown

        // Level 7: demon temple
        mapTiles.add(tile(35, wall));   // 35 temple wall, demon head
        mapTiles.add(tile(36, wall));   // 36 temple pillar wall
        mapTiles.add(tile(37, floor));  // 37 temple floor, top trim
        mapTiles.add(tile(38, floor));  // 38 temple floor, plain

        // Shading tiles (rows 39-48 of the image)
        mapTiles.add(tile(39, wall));   // 39 dark brick border
        mapTiles.add(tile(40, wall));   // 40 grey stone border
        mapTiles.add(tile(41, wall));   // 41 marble border
        mapTiles.add(tile(42, wall));   // 42 lava cave border
        mapTiles.add(tile(43, wall));   // 43 demon temple border
        mapTiles.add(tile(44, floor));  // 44 dirt floor, wall shadow
        mapTiles.add(tile(45, floor));  // 45 dark floor, wall shadow
        mapTiles.add(tile(46, floor));  // 46 grey floor, wall shadow
        mapTiles.add(tile(47, floor));  // 47 cave floor, wall shadow
        mapTiles.add(tile(48, wall));   // 48 dirt border

        return mapTiles;
    }
}
