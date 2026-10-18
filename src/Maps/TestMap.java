package Maps;

import EnhancedMapTiles.BadWeaponPickup;
import EnhancedMapTiles.DamageTile;
import EnhancedMapTiles.GoodWeaponPickup;
import EnhancedMapTiles.bossSpawntile;
import Level.*;
import NPCs.Enemy;
import NPCs.RangedEnemy;
import NPCs.jeard;
import Scripts.EnemySpawner;
import Scripts.TestMap.*;
import Tilesets.FallenTileset;
import java.util.ArrayList;


// Represents a test map to be used in a level
public class TestMap extends Map {

    public TestMap() {
        super("test_map.txt", new FallenTileset());
        this.playerStartPosition = getMapTile(10, 10).getLocation();
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        GoodWeaponPickup goodWeaponPickup = new GoodWeaponPickup(getMapTile(2, 7).getLocation());
        enhancedMapTiles.add(goodWeaponPickup);
        BadWeaponPickup badWeaponPickup = new BadWeaponPickup(getMapTile(3,6).getLocation());
        enhancedMapTiles.add(badWeaponPickup);

        DamageTile damageTile = new DamageTile(getMapTile(6,7).getLocation());
        enhancedMapTiles.add(damageTile);

        bossSpawntile bossSpawnTile = new bossSpawntile(getMapTile(1, 1).getLocation());
        enhancedMapTiles.add(bossSpawnTile);

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        Enemy enemy = new Enemy(4, getMapTile(9, 9).getLocation().subtractX(20));
       // enemy.setInteractScript(new BugScript());
        npcs.add(enemy);

        jeard jeard = new jeard(4, getMapTile(9, 9).getLocation());
        jeard.setInteractScript(new jeardScript());
        npcs.add(jeard);

        RangedEnemy turret = new RangedEnemy(5, getMapTile(7, 7).getLocation());
      //  turret.setInteractScript(new RangedEnemyScript());
        npcs.add(turret);

        ArrayList<NPC> spawnedNPCs = EnemySpawner.spawnEnemies(3, 2);
        npcs.addAll(spawnedNPCs);

        return npcs;
    } 

  private static final int DOOR_TILE_INDEX = 7; // change to your door tile's index

@Override
public ArrayList<Trigger> loadTriggers() {
    ArrayList<Trigger> triggers = new ArrayList<>();

    for (int y = 0; y < getHeight(); y++) {
        for (int x = 0; x < getWidth(); x++) {
            MapTile tile = getMapTile(x, y);
            if (tile.getTileIndex() == DOOR_TILE_INDEX) {   
                triggers.add(new Trigger(
                    tile.getX(), tile.getY(),
                    tile.getWidth(), tile.getHeight(),
                    new NextLevelScript()));
            }
        }
    }
    return triggers;
}

    @Override
    public void loadScripts() {

    }
}

