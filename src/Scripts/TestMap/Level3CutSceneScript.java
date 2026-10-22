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
                addText("NPC: fufufu, so you decide to go back to that place,\nyou really sure about this decision?");
                addText("Player: why do so many of you try\nto convince me to stay here?");
                addText("NPC: cause many of use of tried to go there,\n but got rejected.");
                addText("NPC:and we are trying to save you from the\nsame pain of that rejection");
                addText("Player: what do you mean rejection,\nit is a place where if you repent, you can go back");
                addText("NPC:you still don't understand\nthey don't care about us");
                addText("Player: we will see about that");
        }});
        //unlocks player after the scene is over
        scriptActions.add(new UnlockPlayerScriptAction());
        scriptActions.add(new ChangeFlagScriptAction("cutscene1done",true));
        return scriptActions;
    }

}
