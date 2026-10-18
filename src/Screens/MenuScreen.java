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

// This is the class for the main menu screen
public class MenuScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected int currentMenuItemHovered = 0; // current menu item being "hovered" over
    protected int menuItemSelected = -1;
    protected BufferedImage titleScreen;
    protected SpriteFont playGame;
    protected SpriteFont leaderboard;
    protected SpriteFont credits;
    //protected Map background;
    protected int keyPressTimer;
    protected int pointerLocationX, pointerLocationY;
    protected KeyLocker keyLocker = new KeyLocker();
    //protected Sprite titleScreen;

    public MenuScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        String imagePath = "TitleScreen.png";
        try {
            titleScreen = ImageIO.read(new File(Config.RESOURCES_PATH + imagePath));
            ImageLoader.load(imagePath);
        } 
        catch (IOException e) 
        {
            throw new RuntimeException("Unable to find file " + imagePath, e);
        }
        playGame = new SpriteFont("PLAY GAME", 200, 183, "Arial", 30, new Color(49, 207, 240));
        playGame.setOutlineColor(Color.black);
        playGame.setOutlineThickness(3);
        leaderboard = new SpriteFont("LEADERBOARD", 200, 223, "Arial", 30, new Color(49, 207, 240));
        leaderboard.setOutlineColor(Color.black);
        leaderboard.setOutlineThickness(3);
        credits = new SpriteFont("CREDITS", 200, 263, "Arial", 30, new Color(49, 207, 240));
        credits.setOutlineColor(Color.black);
        credits.setOutlineThickness(3);
        //background = new TitleScreenMap();
        //background.setAdjustCamera(false);
        keyPressTimer = 0;
        menuItemSelected = -1;
        keyLocker.lockKey(Key.SPACE);
    }

    public void update() {
        // update background map (to play tile animations)
        //background.update(null);

        // if down or up is pressed, change menu item "hovered" over (blue square in front of text will move along with currentMenuItemHovered changing)
        if (Keyboard.isKeyDown(Key.DOWN) && keyPressTimer == 0) {
            keyPressTimer = 9;
            currentMenuItemHovered++;
        } else if (Keyboard.isKeyDown(Key.UP) && keyPressTimer == 0) {
            keyPressTimer = 9;
            currentMenuItemHovered--;
        } else {
            if (keyPressTimer > 0) {
                keyPressTimer--;
            }
        }

        // if down is pressed on last menu item or up is pressed on first menu item, "loop" the selection back around to the beginning/end
        if (currentMenuItemHovered > 2) {
            currentMenuItemHovered = 0;
        } else if (currentMenuItemHovered < 0) {
            currentMenuItemHovered = 2;
        }

        // sets location for blue square in front of text (pointerLocation) and also sets color of spritefont text based on which menu item is being hovered
        pointerLocationX = 170;
        pointerLocationY = 190 + 40 * currentMenuItemHovered;

        Color unselectedColor = new Color(49, 207, 240);
        Color selectedColor = new Color(255, 215, 0);

        playGame.setColor(unselectedColor);
        leaderboard.setColor(unselectedColor);
        credits.setColor(unselectedColor);

        switch(currentMenuItemHovered)
        {
            case 0:
                playGame.setColor(selectedColor);
                break;
            case 1:
                leaderboard.setColor(selectedColor);
                break;
            case 2:
                credits.setColor(selectedColor);
                break;
        }

        /* 
        if (currentMenuItemHovered == 0) {
            playGame.setColor(new Color(255, 215, 0));
            credits.setColor(new Color(49, 207, 240));
            pointerLocationX = 170;
            pointerLocationY = 190;
        } else if (currentMenuItemHovered == 1) {
            playGame.setColor(new Color(49, 207, 240));
            credits.setColor(new Color(255, 215, 0));
            pointerLocationX = 170;
            pointerLocationY = 230;
        } else if (currentMenuItemHovered == 2) {
            playGame.setColor(new Color(49, 207, 240));
            credits.setColor(new Color(255, 215, 0));
            pointerLocationX = 170;
            pointerLocationY = 270;
        }
        */
        // if space is pressed on menu item, change to appropriate screen based on which menu item was chosen
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            menuItemSelected = currentMenuItemHovered;
            if (menuItemSelected == 0) {
                screenCoordinator.setGameState(GameState.LEVEL);
            } else if (menuItemSelected == 1) {
                screenCoordinator.setGameState(GameState.LEADERBOARD);
            } else if (menuItemSelected == 2) {
                screenCoordinator.setGameState(GameState.CREDITS);
            }
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawImage(titleScreen, 0, 0, Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT);
        //background.draw(graphicsHandler);
        playGame.draw(graphicsHandler);
        leaderboard.draw(graphicsHandler);
        credits.draw(graphicsHandler);
        graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(49, 207, 240), Color.black, 2);
    }
}
