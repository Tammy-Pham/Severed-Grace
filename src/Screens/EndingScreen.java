package Screens;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Engine.Config;
import Engine.GraphicsHandler;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import Engine.Screen;
import Level.Player;
import SpriteFont.SpriteFont;

public class EndingScreen extends Screen {
    private final Player player;
    private final PlayLevelScreen playLevelScreen;
    private final KeyLocker keyLocker = new KeyLocker();
    private BufferedImage endingImage;
    private SpriteFont menuInstructions;

    public EndingScreen(Player player, PlayLevelScreen playLevelScreen) {
        this.player = player;
        this.playLevelScreen = playLevelScreen;
    }

    @Override
    public void initialize() {
        String imagePath = player.getKarma() < 0 ? "HellEnd.png"
            : player.getKarma() > 0 ? "HeavenEnd.png"
            : "JoeEnd.png";

        try {
            endingImage = ImageIO.read(new File(Config.RESOURCES_PATH + imagePath));
        } catch (IOException e) {
            throw new RuntimeException("Unable to load ending image " + imagePath, e);
        }

        menuInstructions = new SpriteFont("Press Space to return to the main menu",
            20, Config.GAME_WINDOW_HEIGHT - 25, "Arial", 20, Color.white);
        menuInstructions.setOutlineColor(Color.black);
        menuInstructions.setOutlineThickness(3);
        keyLocker.lockKey(Key.SPACE);
    }

    public int getEndingKarma() {
        return player.getKarma();
    }

    @Override
    public void update() {
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        if (Keyboard.isKeyDown(Key.SPACE) && !keyLocker.isKeyLocked(Key.SPACE)) {
            playLevelScreen.goBackToMenu();
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawImage(endingImage, 0, 0, Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT);
        menuInstructions.draw(graphicsHandler);
    }
}
