package Scripts.TestMap;

import java.util.ArrayList;

import Level.Script;
import ScriptActions.ChangeFlagScriptAction;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.ScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;

public class Level5CutSceneScript extends Script {

    
    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        //lockes player in place so its a cutscene between npc and player
        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new TextboxScriptAction() {{
            //goes through the lines that are given
                addText("???: I will say, not many make it this\nfar!");
                addText("???: Many get tricked or discouraged enough\nto stay at the lower levels.");
                addText("Player: I only wish to make it back home,\nI do not wish to stay here.");
                addText("Player: My only wish is to go back\nand make amends.");
                addText("???: Your desire is strong, but\nthat desire can turn against you if you let it.");
                addText("Player: I will be the one to determine if my\ndesire is strong enough.");
                addText("Player: Because either way, I will get there.");
                addText("???: So... you will continue. Very\nwell. I wish you for the best of luck.");
                addText("Player: ...");
                addText("Player: ...thank you.");
        }});
        //unlocks player after the scene is over
        scriptActions.add(new UnlockPlayerScriptAction());
        scriptActions.add(new ChangeFlagScriptAction("cutscene1done",true));
        return scriptActions;
    }

}
