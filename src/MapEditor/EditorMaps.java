package MapEditor;

import Level.Map;
import Maps.LevelFiveMap;
import Maps.LevelFourMap;
import Maps.LevelOneMap;
import Maps.LevelSevenMap;
import Maps.LevelSixMap;
import Maps.LevelThreeMap;
import Maps.LevelTwoMap;
import Maps.LevelOneSceneMap;
import Maps.LevelTwoSceneMap;
import Maps.LevelThreeSceneMap;
import Maps.LevelFourSceneMap;
import Maps.LevelFiveSceneMap;
import Maps.LevelSixSceneMap;
import Maps.LevelSevenSceneMap;
import Maps.TestMap;
import Maps.TitleScreenMap;
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
            add("LevelOneScene");
            add("LevelTwoScene");
            add("LevelThreeScene");
            add("LevelFourScene");
            add("LevelFiveScene");
            add("LevelSixScene");
            add("LevelSevenScene");
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
            case "LevelOneScene":
                return new LevelOneSceneMap();
            case "LevelTwoScene":
                return new LevelTwoSceneMap();
            case "LevelThreeScene":
                return new LevelThreeSceneMap();
            case "LevelFourScene":
                return new LevelFourSceneMap();
            case "LevelFiveScene":
                return new LevelFiveSceneMap();
            case "LevelSixScene":
                return new LevelSixSceneMap();
            case "LevelSevenScene":
                return new LevelSevenSceneMap();
            default:
                throw new RuntimeException("Unrecognized map name");
        }
    }
}
