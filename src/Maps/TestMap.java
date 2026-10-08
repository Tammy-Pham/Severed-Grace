package Maps;

import EnhancedMapTiles.BadWeaponPickup;
import EnhancedMapTiles.DamageTile;
import EnhancedMapTiles.GoodWeaponPickup;
import EnhancedMapTiles.RNGWeaponDrop;
import EnhancedMapTiles.bossSpawntile;
import Level.*;
import NPCs.Enemy;
import NPCs.RangedEnemy;
import NPCs.jeard;
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

        RNGWeaponDrop rngWeaponTile = new RNGWeaponDrop(getMapTile(5, 5).getLocation());
        enhancedMapTiles.add(rngWeaponTile);

        bossSpawntile bossSpawnTile = new bossSpawntile(getMapTile(1, 1).getLocation());
        enhancedMapTiles.add(bossSpawnTile);

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        Enemy enemy = new Enemy(4, getMapTile(9, 9).getLocation().subtractX(20));
        enemy.setInteractScript(new BugScript());
        npcs.add(enemy);

        jeard jeard = new jeard(4, getMapTile(9, 9).getLocation());
        jeard.setInteractScript(new jeardScript());
        npcs.add(jeard);

        RangedEnemy turret = new RangedEnemy(4, getMapTile(10, 10).getLocation());
        turret.setInteractScript(new RangedEnemyScript());
        npcs.add(turret);

        return npcs;
    } 

    @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        return triggers;
    }

    @Override
    public void loadScripts() {

    }
}

