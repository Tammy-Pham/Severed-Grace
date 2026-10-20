package Maps;

import Level.*;
import NPCs.Demon4;
import Scripts.TestMap.Level4CutSceneScript;

import java.util.ArrayList;


// Represents a test map to be used in a level
public class LevelFourSceneMap extends LevelMap {

    public LevelFourSceneMap() {
        super("level_four_scene_map.txt", false);
        this.playerStartPosition = getMapTile(10, 10).getLocation();
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        npcs.add(new Demon4(4, getMapTile(9, 9).getLocation().subtractX(20)));

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
                48, 48, new Level4CutSceneScript()));
        return triggers;
    }

    @Override
    public void loadScripts() {

    }
}
