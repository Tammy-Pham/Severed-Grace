package Level;

public class LevelSwitch {
    private static Map nextMap = null;

    public static void request(Map map) { nextMap = map; }
    public static boolean isRequested() { return nextMap != null; }

    public static Map consume() {
        Map m = nextMap;
        nextMap = null;
        return m;
    }
}