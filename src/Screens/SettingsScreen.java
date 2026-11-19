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

    private static final float VOLUME_STEP = 0.1f;
    private static final int BAR_X = 330;
    private static final int BAR_WIDTH = 250;
    private static final int BAR_HEIGHT = 18;
    private static final Color UNSELECTED_COLOR = Color.white;
    private static final Color SELECTED_COLOR = new Color(255, 236, 158);
    private static final Color BAR_FILL = new Color(200, 30, 30);
    private static final Color BAR_EMPTY = new Color(50, 50, 50);

    private SpriteFont volumeHeading;
    private SpriteFont musicLabel;
    private SpriteFont sfxLabel;
    private SpriteFont musicPercent;
    private SpriteFont sfxPercent;
    private SpriteFont volumeInstructions;
    private int selectedRow = 0; // 0 = music, 1 = sound effects
    private int keyPressTimer = 0;


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
        movementLabel = new SpriteFont("Movement: W - Foward, S - Down, A - Left, S - Right, R - Roll, Space - Heal, Q - Lighting Strike, E - Fire Wall", 130, 121, "Times New Roman", 20, Color.white);
        dialogueLabel = new SpriteFont("Skip Dialoge - Space Bar", 130, 151, "Times New Roman", 20, Color.white);
        movementLabel = new SpriteFont("Movement: W - Up, S - Down, A - Left, D - Right, E - Roll", 130, 121, "Times New Roman", 20, Color.white);
        dialogueLabel = new SpriteFont("Skip Dialogue - Space Bar", 130, 151, "Times New Roman", 20, Color.white);
        returnInstructions = new SpriteFont("Press Space to return to the menu", 20, 532, "Times New Roman", 30, Color.white);
        keyLocker.lockKey(Key.SPACE);

        volumeHeading = new SpriteFont("Volume", 130, 215, "Times New Roman", 26, Color.white);
        musicLabel = new SpriteFont("Music", 150, 262, "Times New Roman", 22, UNSELECTED_COLOR);
        sfxLabel = new SpriteFont("Sound Effects", 150, 312, "Times New Roman", 22, UNSELECTED_COLOR);
        musicPercent = new SpriteFont("", BAR_X + BAR_WIDTH + 15, 262, "Times New Roman", 22, Color.white);
        sfxPercent = new SpriteFont("", BAR_X + BAR_WIDTH + 15, 312, "Times New Roman", 22, Color.white);
        volumeInstructions = new SpriteFont("W / S: choose      A / D: adjust", 150, 360, "Times New Roman", 18, Color.lightGray);        for (SpriteFont font : new SpriteFont[] { musicLabel, sfxLabel }) {
            font.setOutlineColor(Color.black);
            font.setOutlineThickness(2);
        }
    }

    @Override
    public void update() {
        updateVolumeControls();

        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }
    
    private void updateVolumeControls() {
        if (keyPressTimer > 0) {
            keyPressTimer--;
        } else if (Keyboard.isKeyDown(Key.W) || Keyboard.isKeyDown(Key.S)) {
            selectedRow = 1 - selectedRow; // only two rows, so W and S both switch
            keyPressTimer = 12;
        } else if (Keyboard.isKeyDown(Key.A)) {
            changeSelectedVolume(-VOLUME_STEP);
            keyPressTimer = 8;
        } else if (Keyboard.isKeyDown(Key.D)) {
            changeSelectedVolume(VOLUME_STEP);
            keyPressTimer = 8;
        }

        musicLabel.setColor(selectedRow == 0 ? SELECTED_COLOR : UNSELECTED_COLOR);
        sfxLabel.setColor(selectedRow == 1 ? SELECTED_COLOR : UNSELECTED_COLOR);
        musicPercent.setText(toPercent(AudioSettings.getMusicVolume()));
        sfxPercent.setText(toPercent(AudioSettings.getSfxVolume()));
    }

    private void changeSelectedVolume(float amount) {
        if (selectedRow == 0) {
            AudioSettings.setMusicVolume(AudioSettings.getMusicVolume() + amount); // music changes live
        } else {
            AudioSettings.setSfxVolume(AudioSettings.getSfxVolume() + amount);
        }
    }

    private String toPercent(float volume) {
        return Math.round(volume * 100) + "%";
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawImage(settingsScreen, 0, 0, Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT);
        title.draw(graphicsHandler);
        returnInstructions.draw(graphicsHandler);
        movementLabel.draw(graphicsHandler);
        dialogueLabel.draw(graphicsHandler);

        volumeHeading.draw(graphicsHandler);
        musicLabel.draw(graphicsHandler);
        sfxLabel.draw(graphicsHandler);
        drawVolumeBar(graphicsHandler, 266, AudioSettings.getMusicVolume(), selectedRow == 0);
        drawVolumeBar(graphicsHandler, 316, AudioSettings.getSfxVolume(), selectedRow == 1);
        musicPercent.draw(graphicsHandler);
        sfxPercent.draw(graphicsHandler);
        volumeInstructions.draw(graphicsHandler);
    }

    private void drawVolumeBar(GraphicsHandler graphicsHandler, int y, float volume, boolean selected) {
        int filled = Math.round(BAR_WIDTH * volume);
        graphicsHandler.drawFilledRectangle(BAR_X, y, BAR_WIDTH, BAR_HEIGHT, BAR_EMPTY);
        graphicsHandler.drawFilledRectangle(BAR_X, y, filled, BAR_HEIGHT, BAR_FILL);
        graphicsHandler.drawRectangle(BAR_X, y, BAR_WIDTH, BAR_HEIGHT, selected ? SELECTED_COLOR : Color.black, 2);
    }
}