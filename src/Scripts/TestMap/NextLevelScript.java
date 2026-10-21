package Scripts.TestMap;

import java.util.ArrayList;

import Level.Script;
import ScriptActions.*;

// walking onto a door tile sets the "goToNextLevel" flag, which PlayLevelScreen watches for
public class NextLevelScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new ChangeFlagScriptAction("goToNextLevel", true));
        return scriptActions;
    }
}