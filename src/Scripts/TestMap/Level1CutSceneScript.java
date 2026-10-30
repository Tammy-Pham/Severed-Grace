package Scripts.TestMap;

import java.util.ArrayList;

import Level.Script;
import ScriptActions.ChangeFlagScriptAction;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.ScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;

public class Level1CutSceneScript extends Script {

    
    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        //lockes player in place so its a cutscene between npc and player
        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new TextboxScriptAction() {{
            //goes through the lines that are given
                addText("Prologue");
                addText("God: You are now banished from this realm.");
                addText("Player: What?Why?I don't understand why I got\npunished! I did nothing wrong.");
                addText("God: Your love that you desire is a forbidden one;\ntherefore...");
                addText("God:YOU ARE NOW BANISHED DOWN TO *******");
                addText("The clouds split beneath your feet\nwhere you stand.");
                addText("God's sword slashes one of your wings\naway to display your status as a fallen angel");
                addText("Your weapon disappears, and your\nsurroundings starts darken");
                addText("and the white clouds seen further and further away\nuntil they disappear from your sight");
                addText("As the atmosphere darkens, you lose\nconsciousness and wake up in a dungeon");
                addText("where no light can reach");
                addText("and you see only one of your\nwhite wings in the corner of your eyes");
                addText("End of Prologue");
                addText("???: Hello, there. You seem lost.");
                addText("Player: ...where am I? What is this place?");
                addText("???: You would have to climb through hell,\nbut what's the point? It was just a rumor.");
                addText("Player: I see... so that's how it works.");
        }});
        //unlocks player after the scene is over
        scriptActions.add(new UnlockPlayerScriptAction());
        scriptActions.add(new ChangeFlagScriptAction("cutscene1done",true));
        return scriptActions;
    }

}
