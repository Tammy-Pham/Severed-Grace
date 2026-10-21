package Scripts.TestMap;

import java.util.ArrayList;

import Level.Script;
import ScriptActions.ChangeFlagScriptAction;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.ScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;

public class Level2CutSceneScript extends Script {

    
    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        //lockes player in place so its a cutscene between npc and player
        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new TextboxScriptAction() {{
            //goes through the lines that are given
                addText("NPC: so another one has fallen from heaven");
                addText("Player: how did you know that? I just got here");
                addText("NPC:well word here travels fast,and why go\nto a place where they cast you away from heaven");
                addText("Player: It's my home");
                addText("NPC:but would a home really try to cast you away?\nand cast you down here with demons and others");
                addText("Player: no, I shall not stay here\nand will continue my journey");
                addText("NPC: well if thats what you wish,but\ndo not be surprise if they reject you again");
        }});
        //unlocks player after the scene is over
        scriptActions.add(new UnlockPlayerScriptAction());
        scriptActions.add(new ChangeFlagScriptAction("cutscene1done",true));
        return scriptActions;
    }

}
