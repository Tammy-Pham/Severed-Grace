package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
//import Level.Map;
//import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;

//expends to the screen class
public class Level1Scene extends Screen{
    protected ScreenCoordinator screenCoordinator;
    protected BufferedImage titleScreen;
    protected BufferedImage npcImage;
    protected BufferedImage playerImage;
    private SpriteFont dialogue;
    private TransitionScreen transitionScreen;

    private static final int SPRITE_W = 508;
    private static final int SPRITE_H = 448;

    private int stage = 0;
    private boolean spaceHeld = true;

    public Level1Scene(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    //sets how the dialogue is going to look like on screen
    private void setDialogue(String text){
        dialogue = new SpriteFont(text,50, Config.GAME_WINDOW_HEIGHT - 70,"Arial",24,Color.WHITE);
        dialogue.setOutlineColor(Color.BLACK);
        dialogue.setOutlineThickness(3);

    }

    //sets the dialogue of what is going to be written on the screen
    private String getDialogueForStage(int stage){
     switch(stage){
        case 0:
            return "NPC: Hello, there you see lost.";
        case 1:
            return "Player: where am I? where is this place?";
        case 2:
            return "NPC: you have fallen into this hell, where it determines, if you end up in hell";
        case 3:
            return "Player: I see, so how do I get out of here?";
        case 4:
            return "NPC: you would have to climb the hells,but whats the point it was just a rumor";
        case 5:
            return "Player: I see, so thats how it works";
        default: return null;
        }
    }
    
    //this initalize all of the images that are needed and if could not be found will throw an expection

    @Override
	public void initialize() {
        String imagePath = "background.png";
        try{
            titleScreen = ImageIO.read(new File(Config.RESOURCES_PATH + "background.png"));
            npcImage    = ImageIO.read(new File(Config.RESOURCES_PATH + "NPC.png"));
            playerImage = ImageIO.read(new File(Config.RESOURCES_PATH + "player.png"));
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to find file " + imagePath, e);
        }

        //sets the stage to 0 and you hold the space it continutes and get the dialogue for what stage
        //you want it to start on
        stage = 0;
        spaceHeld = true;
        setDialogue(getDialogueForStage(0));
        transitionScreen = new TransitionScreen(1);
	}

    private int groundY = Config.GAME_WINDOW_HEIGHT - SPRITE_H - 100;

    // Set the NPC on the left and the player on the right.
    private int npcX = 150;
    private int npcY = groundY;
    private int playerX = Config.GAME_WINDOW_WIDTH - 150 - SPRITE_W;
    private int playerY = groundY;

    //when you press space the dialgoue changes and if there is no text left then 
    //it should go to the next level
	@Override
	public void update() {
        if(!transitionScreen.isComplete()){
                spaceHeld = true;
                return;
        }
        boolean spacePressed = Keyboard.isKeyDown(Key.SPACE);
            if (spacePressed && !spaceHeld) {
                stage++;
                String text = getDialogueForStage(stage);
                    if (text != null) {
                        setDialogue(text);
                    } else {
                        screenCoordinator.setGameState(GameState.LEVEL);
                    }
            }
            spaceHeld = spacePressed;
	}

//draw the images of everything
@Override
public void draw(GraphicsHandler graphicsHandler) {
    // show the black "Level 1" card until it finishes
    if (!transitionScreen.isComplete()) {
        transitionScreen.draw(graphicsHandler);
        return;
    }

    graphicsHandler.drawImage(titleScreen, 0, 0, Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT);
    graphicsHandler.drawImage(npcImage, npcX, npcY, SPRITE_W, SPRITE_H);
    graphicsHandler.drawImage(playerImage, playerX, playerY, SPRITE_W, SPRITE_H);
    dialogue.draw(graphicsHandler);
}
}