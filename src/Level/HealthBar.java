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

        int frameNumber = 1;
        if (health <= 5) {
            frameNumber = 1;
        } else if (health <= 10) {
            frameNumber = 2;
        } else if (health <= 15) {
            frameNumber = 3;
        } else if (health <= 20) {
            frameNumber = 4;
        } else if (health <= 25) {
            frameNumber = 5;
        } else if (health <= 30) {
            frameNumber = 6;
        } else if (health <= 35) {
            frameNumber = 7;
        } else if (health <= 40) {
            frameNumber = 8;
        } else if (health <= 45) {
            frameNumber = 9;
        } else if (health <= 50) {
            frameNumber = 10;
        } else if (health <= 55) {
            frameNumber = 11;
        } else if (health <= 60) {
            frameNumber = 12;
        } else if (health <= 65) {
            frameNumber = 13;
        } else if (health <= 70) {
            frameNumber = 14;
        } else if (health <= 75) {
            frameNumber = 15;
        } else if (health <= 80) {
            frameNumber = 16;
        } else if (health <= 85) {
            frameNumber = 17;
        } else if (health <= 90) {
            frameNumber = 18;
        } else if (health <= 95) {
            frameNumber = 19;
        } else {
            frameNumber = 20;
        }

        BufferedImage frame = frames[frameNumber - 1];
        graphicsHandler.drawImage(frame, x, y, frame.getWidth() * scale, frame.getHeight() * scale);
    }
}

