package Maps;

import Level.*;
import NPCs.jeard;
import NPCs.Demon1;
import Scripts.TestMap.BugScript;
import Scripts.TestMap.Level1CutSceneScript;

import java.util.ArrayList;


// Represents a test map to be used in a level
public class LevelOneSceneMap extends LevelMap {

    public LevelOneSceneMap() {
        super("level_one_scene_map.txt", false);
        this.playerStartPosition = getMapTile(10, 10).getLocation();
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        Demon1 demon1 = new Demon1(4, getMapTile(9, 9).getLocation().subtractX(20));
        npcs.add(demon1);

        return npcs;
    } 

    @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        triggers.add(new Trigger(getMapTile(10, 10).getLocation().x, getMapTile(10, 10).getLocation().y,
                48, 48, new Level1CutSceneScript()));
        return triggers;
    }

    @Override
    public void loadScripts() {

    }
}
