package Maps;

import Level.*;
import NPCs.Demon7;
import Scripts.TestMap.Level7CutSceneScript;

import java.util.ArrayList;


// Represents a test map to be used in a level
public class LevelSevenSceneMap extends LevelMap {

    public LevelSevenSceneMap() {
        super("level_seven_scene_map.txt", false);
        this.playerStartPosition = getMapTile(10, 10).getLocation();
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        /*GoodWeaponPickup goodWeaponPickup = new GoodWeaponPickup(getMapTile(2, 7).getLocation());
        enhancedMapTiles.add(goodWeaponPickup);
        BadWeaponPickup badWeaponPickup = new BadWeaponPickup(getMapTile(3,6).getLocation());
        enhancedMapTiles.add(badWeaponPickup);

        DamageTile damageTile = new DamageTile(getMapTile(6,7).getLocation());
        enhancedMapTiles.add(damageTile);

        RNGWeaponDrop rngWeaponTile = new RNGWeaponDrop(getMapTile(5, 5).getLocation());
        enhancedMapTiles.add(rngWeaponTile);

        bossSpawntile bossSpawnTile = new bossSpawntile(getMapTile(1, 1).getLocation());
        enhancedMapTiles.add(bossSpawnTile);*/

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        Demon7 demon7 = new Demon7(4, getMapTile(9, 9).getLocation().subtractX(20));
        npcs.add(demon7);
        /*Enemy enemy = new Enemy(4, getMapTile(9, 9).getLocation().subtractX(20));
        enemy.setInteractScript(new BugScript());
        npcs.add(enemy);

        jeard jeard = new jeard(4, getMapTile(9, 9).getLocation());
        jeard.setInteractScript(new jeardScript());
        npcs.add(jeard);

        RangedEnemy turret = new RangedEnemy(5, getMapTile(7, 7).getLocation());
        turret.setInteractScript(new RangedEnemyScript());
        npcs.add(turret);
*/
        return npcs;
    } 

    @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        triggers.add(new Trigger(getMapTile(10, 10).getLocation().x, getMapTile(10, 10).getLocation().y,
                48, 48, new Level7CutSceneScript()));
        return triggers;
    }

    @Override
    public void loadScripts() {

    }
}
