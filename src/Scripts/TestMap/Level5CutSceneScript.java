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
                addText("NPC: not many make it this far");
                addText("NPC:many get tricked or discouraged enough\nto stay at the lower levels.");
                addText("Player: I only wish to make it back home,\nI do not wish to stay here.");
                addText("Player:...my only wish is to go back\nand make amends");
                addText("NPC: your desire is strong, but\nthat desire can turn against you if you let it.");
                addText("Player: I will the one to determine if my desire\nis strong enough");
                addText("Player:cause either way I will get there");
                addText("NPC: so you will continue, very\nwell I wish you for the best of luck");
        }});
        //unlocks player after the scene is over
        scriptActions.add(new UnlockPlayerScriptAction());
        scriptActions.add(new ChangeFlagScriptAction("cutscene1done",true));
        return scriptActions;
    }

}
