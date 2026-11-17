
package GameObject;

import Engine.GraphicsHandler;
import Engine.ImageLoader;

import java.awt.image.BufferedImage;

public class DeathEffect {

    private static final BufferedImage SHEET =
        ImageLoader.load("Temp-Death-Animations-Severed-Grace.png");

    private static final int FRAME_SIZE = 64;
    private static final int FRAME_COUNT = 4;
    private static final long FRAME_DURATION = 100;
    private static final float SCALE = 2.0f;

    private final float x;
    private final float y;
    private final long startTime;

    public DeathEffect(float x, float y) {
        this.x = x;
        this.y = y;
        this.startTime = System.currentTimeMillis();
    }

    public boolean isFinished() {
        return System.currentTimeMillis() - startTime
            >= FRAME_COUNT * FRAME_DURATION;
    }

    public void draw(GraphicsHandler graphicsHandler,
                     float cameraX, float cameraY) {

        long elapsed =
            System.currentTimeMillis() - startTime;

        int frameIndex = (int) (elapsed / FRAME_DURATION);

        if (frameIndex >= FRAME_COUNT) {
            return;
        }

        int row = frameIndex / 2;
        int column = frameIndex % 2;

        BufferedImage frame = SHEET.getSubimage(
            column * FRAME_SIZE,
            row * FRAME_SIZE,
            FRAME_SIZE,
            FRAME_SIZE
        );

        int size = Math.round(FRAME_SIZE * SCALE);

        graphicsHandler.drawImage(
            frame,
            Math.round(x - cameraX),
            Math.round(y - cameraY),
            size,
            size
        );
    }
}
