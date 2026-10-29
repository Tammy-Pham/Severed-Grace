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

public class DeathScreen extends Screen {
    private final Player player;
    private final PlayLevelScreen playLevelScreen;
    private final KeyLocker keyLocker = new KeyLocker();
    private BufferedImage deathImage;
    private boolean active;

    public DeathScreen(Player player, PlayLevelScreen playLevelScreen) {
        this.player = player;
        this.playLevelScreen = playLevelScreen;
    }

    @Override
    public void initialize() {
        String imagePath = "DeathScreen.png";

        try {
            deathImage = ImageIO.read(new File(Config.RESOURCES_PATH + imagePath));
        } catch (IOException e) {
            throw new RuntimeException("Unable to load ending image " + imagePath, e);
        }

        keyLocker.lockKey(Key.SPACE);
    }

    public boolean isActive(){
        return active;
    }

    @Override
    public void update() {
        if(player.getHealth() <= 0){
            active = true;
        }
        
        if(!active){
            return;
        }
        
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        if (Keyboard.isKeyDown(Key.SPACE) && !keyLocker.isKeyLocked(Key.SPACE)) {
            playLevelScreen.goBackToMenu();
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawImage(deathImage, 0, 0, Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT);
    }
}
