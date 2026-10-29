package Scripts.TestMap;

import java.util.ArrayList;

import Level.Script;
import ScriptActions.ChangeFlagScriptAction;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.ScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;

public class Level4CutSceneScript extends Script {

    
    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        //lockes player in place so its a cutscene between npc and player
        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new TextboxScriptAction() {{
            //goes through the lines that are given
                addText("???: You want to go back to heaven, no?");
                addText("Player: Are *you* also going to try to stop me?");
                addText("???: ...I will only give you a warning.\nGoing back there? It will give you nothing.");
                addText("???: It will only cause more confusion\nand chaos in one's mind.");
                addText("Player: I see, so that's how it works.\nBut I'd rather try than linger here forever.");
                addText("???: Do you truly think you will gain\nanything from going back there?");
                addText("???: You will only be used as a tool.");
                addText("Player: But at least it's for a good cause.");
                addText("???: Fine.\nBut I have warned you...");
        }});
        //unlocks player after the scene is over
        scriptActions.add(new UnlockPlayerScriptAction());
        scriptActions.add(new ChangeFlagScriptAction("cutscene1done",true));
        return scriptActions;
    }

}
