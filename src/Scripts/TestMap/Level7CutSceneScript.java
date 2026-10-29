package Scripts.TestMap;

import java.util.ArrayList;

import Level.Script;
import ScriptActions.ChangeFlagScriptAction;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.ScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;

public class Level7CutSceneScript extends Script {

    
    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        //lockes player in place so its a cutscene between npc and player
        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new TextboxScriptAction() {{
            //goes through the lines that are given
                addText("???: So you have made it this far.");
                addText("???: Many have tried to go back\nto heaven but failed.");
                addText("???: But you seem to be determined to go back?");
                addText("Player: This is the last floor\nuntil I can go back to heaven?");
                addText("???: Yes, but after you walk\nthrough the last doors...");
                addText("???: ...going to heaven is never a guarantee,\nit will always be a gamble.");
                addText("Player: I will be able to go\nback to heaven without fail.");
                addText("???: That's what you think.\nBut heaven is always watching.");
        }});
        //unlocks player after the scene is over
        scriptActions.add(new UnlockPlayerScriptAction());
        scriptActions.add(new ChangeFlagScriptAction("cutscene1done",true));
        return scriptActions;
    }

}
