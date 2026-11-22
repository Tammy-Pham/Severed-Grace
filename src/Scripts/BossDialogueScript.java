package Scripts;

import Level.Script;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.NPCFacePlayerScriptAction;
import ScriptActions.ScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;
import java.util.ArrayList;

public class BossDialogueScript extends Script {
    private final int bossFloor;

    public BossDialogueScript(int bossFloor) {
        this.bossFloor = bossFloor;
    }

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        String[] dialogue;
        switch (bossFloor) {
            case 1:
                dialogue = new String[] {
                        "Sloth: Why go back and work so hard for one\nwho never rewards you?",
                        "Player: My reward is honor."
                };
                break;
            case 2:
                dialogue = new String[] {
                        "Gluttony: Many have tried and failed to return.\nWhy not join my feast instead?",
                        "Player: I will not be one of them."
                };
                break;
            case 3:
                dialogue = new String[] {
                        "Lust: Why return to a home that cast you away\nand will not accept who you are?",
                        "Player: Even if they reject me, it is still\nmy home. I won't give up on my love."
                };
                break;
            case 4:
                dialogue = new String[] {
                        "Pride: Heaven will never give you true\nfreedom.",
                        "Player: I have made it this far.\nI will keep going."
                };
                break;
            case 5:
                dialogue = new String[] {
                        "Envy: They only envy the love you have found.",
                        "Player: Their envy will not change\nwhat matters to me."
                };
                break;
            case 6:
                dialogue = new String[] {
                        "Greed: You still want to return? Has God\nreally blinded you?",
                        "Player: My future is not yours to\nbargain over."
                };
                break;
            case 7:
                dialogue = new String[] {
                        "Wrath: If you return, I will show you\nno mercy. Do you still insist?",
                        "Player: I will face whatever waits beyond you."
                };
                break;
            default:
                throw new IllegalArgumentException("Invalid boss floor: " + bossFloor);
        }

        ArrayList<ScriptAction> actions = new ArrayList<>();
        actions.add(new LockPlayerScriptAction());
        actions.add(new NPCFacePlayerScriptAction());
        actions.add(new TextboxScriptAction(dialogue));
        actions.add(new UnlockPlayerScriptAction());
        return actions;
    }
}
