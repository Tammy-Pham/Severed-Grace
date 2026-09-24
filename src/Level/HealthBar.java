package Level;

import Engine.Config;
import Engine.GraphicsHandler;
import Engine.ImageLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HealthBar {

    private BufferedImage[] frames = new BufferedImage[20];
    private int x;
    private int y;
    private int scale;

    public HealthBar(int x, int y, int scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;

        for (int i = 0; i < 20; i++) {
            String fileName = "HealthBar" + (i + 1) + ".png";
            frames[i] = loadFrame(fileName);
        }
    }

    private BufferedImage loadFrame(String fileName) {
        try {
            BufferedImage image = ImageIO.read(new File(Config.RESOURCES_PATH + fileName));
            if (image != null && image.getColorModel().hasAlpha()) {
                return image;
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to find file " + Config.RESOURCES_PATH + fileName, e);
        }
        return ImageLoader.load(fileName);
    }

    public void draw(GraphicsHandler graphicsHandler, int health) {
        if (health <= 0) {
            return;
        }
        int frameNumber = 20;
        for (int i = 1; i <= 20; i++) {
            if (health <= i * 5) {
                frameNumber = i;
                break;
            }
        }

        BufferedImage frame = frames[frameNumber - 1];
        graphicsHandler.drawImage(frame, x, y, frame.getWidth() * scale, frame.getHeight() * scale);
    }
}

