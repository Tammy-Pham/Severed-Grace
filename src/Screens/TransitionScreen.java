package Screens;

import Engine.*;
import SpriteFont.SpriteFont;
import java.awt.*;

public class TransitionScreen extends Screen {
    private static final long DISPLAY_DURATION_NANOS = 1_000_000_000L;

    private final SpriteFont levelLabel;
    private long startedAt;

    public TransitionScreen(int levelNumber) {
        levelLabel = new SpriteFont("Level " + levelNumber, 315, 270, "Arial", 36, Color.white);
        initialize();
    }

    @Override
    public void initialize() {
        startedAt = System.nanoTime();
    }

    @Override
    public void update() {
    }

    public boolean isComplete() {
        return System.nanoTime() - startedAt >= DISPLAY_DURATION_NANOS;
    }

    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        levelLabel.draw(graphicsHandler);
    }
}
