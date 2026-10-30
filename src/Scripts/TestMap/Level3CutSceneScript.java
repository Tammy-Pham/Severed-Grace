package Scripts.TestMap;

import java.util.ArrayList;

import Level.Script;
import ScriptActions.ChangeFlagScriptAction;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.ScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;

public class Level3CutSceneScript extends Script {

    
    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        //lockes player in place so its a cutscene between npc and player
        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new TextboxScriptAction() {{
            //goes through the lines that are given
                addText("???: Fufufu, so you decide to go back to that place,\nare you *really* sure about this decision?");
                addText("Player: Why do so many of you actively try\nto convince me to stay here?");
                addText("???: Because many of use of tried to go there.\nBut got rejected.");
                addText("???: And we are trying to save you from the\nsame pain of that rejection.");
                addText("Player: What do you mean \"rejection\"? Heaven\nis a place where if you repent, you can go back!");
                addText("???: ...You still don't understand.\nThey don't care about us.");
                addText("Player: *sigh*\nWe will see about that.");
        }});
        //unlocks player after the scene is over
        scriptActions.add(new UnlockPlayerScriptAction());
        scriptActions.add(new ChangeFlagScriptAction("cutscene1done",true));
        return scriptActions;
    }

}
