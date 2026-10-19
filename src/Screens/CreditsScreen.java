package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import SpriteFont.SpriteFont;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// This class is for the credits screen
public class CreditsScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont creditsLabel;
    protected SpriteFont createdByLabel;
    protected SpriteFont legacybyLabel;
    protected SpriteFont returnInstructionsLabel;
    protected BufferedImage creditScreen;

    public CreditsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        // setup graphics on screen (background map, spritefont text)
      //  background = new TitleScreenMap();
      //  background.setAdjustCamera(false);

      String imagePath = "creditScreen.png";
        try {
            creditScreen = ImageIO.read(new File(Config.RESOURCES_PATH + imagePath));
            ImageLoader.load(imagePath);
        } 
        catch (IOException e) 
        {
            throw new RuntimeException("Unable to find file " + imagePath, e);
        }

        creditsLabel = new SpriteFont("Credits", 15, 7, "Times New Roman", 30, Color.white);
        legacybyLabel = new SpriteFont("Legacy Code by Alex Thimineur", 130, 121, "Times New Roman", 20, Color.white);
        createdByLabel = new SpriteFont("Made by Parker, Alyssa, Reyhan, Tammy, and Jon", 130, 151, "Times New Roman", 20, Color.white);

        returnInstructionsLabel = new SpriteFont("Press Space to return to the menu", 20, 532, "Times New Roman", 30, Color.white);
        keyLocker.lockKey(Key.SPACE);
    }

    public void update() {
       // background.update(null);

        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        // if space is pressed, go back to main menu
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawImage(creditScreen, 0, 0, Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT);
        creditsLabel.draw(graphicsHandler);
        createdByLabel.draw(graphicsHandler);
        legacybyLabel.draw(graphicsHandler);
        returnInstructionsLabel.draw(graphicsHandler);
    }
}
