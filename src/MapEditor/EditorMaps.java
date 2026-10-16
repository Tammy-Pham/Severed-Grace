package MapEditor;

import Level.Map;
import Maps.TestMap;
import Maps.TitleScreenMap;
import Maps.LevelOneMap;
import Maps.LevelTwoMap;
import Maps.LevelThreeMap;
import Maps.LevelFourMap;
import Maps.LevelFiveMap;
import Maps.LevelSixMap;
import Maps.LevelSevenMap;

import java.util.ArrayList;

public class EditorMaps {
    public static ArrayList<String> getMapNames() {
        return new ArrayList<String>() {{
            add("TestMap");
            add("TitleScreen");
            add("LevelOne");
            add("LevelTwo");
            add("LevelThree");
            add("LevelFour");
            add("LevelFive");
            add("LevelSix");
            add("LevelSeven");
        }};
    }

    public static Map getMapByName(String mapName) {
        switch(mapName) {
            case "TestMap":
                return new TestMap();
            case "TitleScreen":
                return new TitleScreenMap();
            case "LevelOne":
                return new LevelOneMap();
            case "LevelTwo":
                return new LevelTwoMap();
            case "LevelThree":
                return new LevelThreeMap();
            case "LevelFour":
                return new LevelFourMap();
            case "LevelFive":
                return new LevelFiveMap();
            case "LevelSix":
                return new LevelSixMap();
            case "LevelSeven":
                return new LevelSevenMap();
            default:
                throw new RuntimeException("Unrecognized map name");
        }
    }
}
