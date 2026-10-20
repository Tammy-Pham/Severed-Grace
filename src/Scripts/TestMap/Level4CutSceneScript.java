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
                addText("NPC: you want to go back to heaven no");
                addText("Player: are you also going to try to stop me?");
                addText("NPC:  .......i will only give you a warning, going back there will give you nothing, it will only cause more confusion and chaos in ones mind");
                addText("Player: I see, so that's how it works.But I rather try then linger here forever");
                addText("NPC:  do you truely think you will gain anything from going back there, you will only be used as a tool");
                addText("Player: but it is something I wish to fight for and not staying here");
                addText("NPC: Fine, but I have warned you");
        }});
        //unlocks player after the scene is over
        scriptActions.add(new UnlockPlayerScriptAction());
        scriptActions.add(new ChangeFlagScriptAction("cutscene1done",true));
        return scriptActions;
    }

}
