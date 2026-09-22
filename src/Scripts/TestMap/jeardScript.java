package Scripts.TestMap;

import Level.Script;
import ScriptActions.*;
import java.util.ArrayList;

public class jeardScript extends Script {
    
    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        
        scriptActions.add(new NPCFacePlayerScriptAction());

        scriptActions.add(new TextboxScriptAction() {{
                        addText("Hello you see new, what bring you here?", new String[] { 
                            "I was kicked out \n of heaven","that is \n non of your concern"});
        }});       

        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new CustomRequirement() {
                     @Override
                     public boolean isRequirementMet() {
                        int answer = outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                        return answer == 0;
                    }
                });

                addScriptAction(new TextboxScriptAction() {{
                    addText("out of heaven you say, you seem to be an \n interesting one, so ill help you out.");
                }});
            }});

            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new CustomRequirement() {
                    @Override 
                    public boolean isRequirementMet(){
                        int answer = outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                        return answer == 1;
                    }
                });

                addScriptAction(new TextboxScriptAction() {{
                    addText("well aren't you rude,\n but everyone has their own secrets.");
                }});
            }});
        }});

        scriptActions.add(new NPCUnlockScriptAction());
        scriptActions.add(new UnlockPlayerScriptAction());
    
        return scriptActions;
    }


}



