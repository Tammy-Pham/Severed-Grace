package Screens;

import Engine.Config;
import Engine.GraphicsHandler;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import Engine.Screen;
import Level.Player;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class EndingScreen extends Screen {
    private final Player player;
    private final PlayLevelScreen playLevelScreen;
    private final KeyLocker keyLocker = new KeyLocker();
    private BufferedImage endingImage;

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
    }
}
