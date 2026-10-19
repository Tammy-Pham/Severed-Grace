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

public class Level5Scene extends Screen{
    protected ScreenCoordinator screenCoordinator;
    protected BufferedImage titleScreen;
    protected BufferedImage npcImage;
    protected BufferedImage playerImage;
    private SpriteFont dialogue;

    private static final int SPRITE_W = 508;
    private static final int SPRITE_H = 448;

    private int stage = 0;
    private boolean spaceHeld = true;

    public Level5Scene(ScreenCoordinator screenCoordinator) {
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
            return "NPC: not many make it this far, many get tricked or discouraged enough to stay at the lower levels";
        case 1:
            return "Player: I only wish to make it back home, I do not wish to stay here,...my only wish is to go back and make amends";
        case 2:
            return "NPC: your desire is strong, but that one desire can turn against you if you let it.";
        case 3:
            return "Player: I will determine if my desire is strong enough not cause either way I will get there";
        case 4:
            return "NPC: so you will continue, very well I wish you for the best of luck";
        default: return null;
        }
    }

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
