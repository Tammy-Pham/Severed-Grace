package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import SpriteFont.SpriteFont;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Level2Scene extends Screen{
    protected ScreenCoordinator screenCoordinator;
    protected BufferedImage titleScreen;
    protected BufferedImage npcImage;
    protected BufferedImage playerImage;
    private SpriteFont dialogue;

    private static final int SPRITE_W = 508;
    private static final int SPRITE_H = 448;

    private int stage = 0;
    private boolean spaceHeld = true;

    public Level2Scene(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    private void setDialogue(String text){
        dialogue = new SpriteFont(text,50, Config.GAME_WINDOW_HEIGHT - 70,"Arial",24,Color.WHITE);
        dialogue.setOutlineColor(Color.BLACK);
        dialogue.setOutlineThickness(3);

    }

    private String getDialogueForStage(int stage){
     switch(stage){
        case 0:
            return "NPC: so another one has fallen from heaven";
        case 1:
            return "Player: how did you know that? I just got here";
        case 2:
            return "NPC: well word here travels fast,and why to go to a place where they cast you away from heaven";
        case 3:
            return "Player: It's my home";
        case 4:
            return "NPC: but would a home really try to cast you away from it? and cast you down here with demons and others";
        case 5:
            return "Player: no, I shall not stay here and continue my journey";
        case 6:
            return "NPC: well if thats what you wish,but do not be surprise if they reject you again";
        default: return null;
        }
    }

    @Override
	public void initialize() {
        String imagePath = "Level1Scene.png";
        try{
            titleScreen = ImageIO.read(new File(Config.RESOURCES_PATH + "Level1Scene.png"));
            npcImage    = ImageIO.read(new File(Config.RESOURCES_PATH + "NPC.png"));
            playerImage = ImageIO.read(new File(Config.RESOURCES_PATH + "player.png"));
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to find file " + imagePath, e);
        }

        stage = 0;
        spaceHeld = true;
        setDialogue(getDialogueForStage(0));
	}

    private int groundY = Config.GAME_WINDOW_HEIGHT - SPRITE_H - 100;

    // Set the NPC on the left and the player on the right.
    private int npcX = 150;
    private int npcY = groundY;
    private int playerX = Config.GAME_WINDOW_WIDTH - 150 - SPRITE_W;
    private int playerY = groundY;


	@Override
	public void update() {
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

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawImage(titleScreen, 0, 0, Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT);
        graphicsHandler.drawImage(npcImage, npcX, npcY, SPRITE_W, SPRITE_H);
        graphicsHandler.drawImage(playerImage, playerX, playerY, SPRITE_W, SPRITE_H);
        dialogue.draw(graphicsHandler);
	}
}
