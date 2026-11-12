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

public class SettingsScreen extends Screen {
    private final ScreenCoordinator screenCoordinator;
    private final KeyLocker keyLocker = new KeyLocker();
    private SpriteFont title;
    private SpriteFont returnInstructions;
    private SpriteFont movementLabel;
    private SpriteFont dialogueLabel;
    protected BufferedImage settingsScreen;


    public SettingsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {

              String imagePath = "creditScreen.png";
        try {
            settingsScreen = ImageIO.read(new File(Config.RESOURCES_PATH + imagePath));
            ImageLoader.load(imagePath);
        } 
        catch (IOException e) 
        {
            throw new RuntimeException("Unable to find file " + imagePath, e);
        }

        title = new SpriteFont("Settings", 15, 7, "Times New Roman", 30, Color.white);
        movementLabel = new SpriteFont("Movement: W - Foward, S - Down, A - Left, S - Right, E - Roll", 130, 121, "Times New Roman", 20, Color.white);
        dialogueLabel = new SpriteFont("Skip Dialoge - Space Bar", 130, 151, "Times New Roman", 20, Color.white);
        //logobyLabel = new SpriteFont("Logo Art by Ali", 130, 181, "Times New Roman", 20, Color.white);
        returnInstructions = new SpriteFont("Press Space to return to the menu", 20, 532, "Times New Roman", 30, Color.white);
        keyLocker.lockKey(Key.SPACE);
    }

    @Override
    public void update() {
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawImage(settingsScreen, 0, 0, Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT);
        title.draw(graphicsHandler);
        returnInstructions.draw(graphicsHandler);
        movementLabel.draw(graphicsHandler);
        dialogueLabel.draw(graphicsHandler);
    }
}