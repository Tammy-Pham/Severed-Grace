package Projectiles;

//import Engine.Config;
import Engine.GraphicsHandler;
//import Engine.GraphicsHandler;
import Engine.ImageLoader;

import GameObject.Frame;
import GameObject.GameObject;
import GameObject.SpriteSheet;

//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;

public class Fireball extends GameObject {
    float angle, speed;

    public Fireball(float x, float y, float angle, float speed) {
        super(x, y, new Frame(new SpriteSheet(ImageLoader.load("Fireball.png"), 24, 24, 0).getSprite(0, 0)));
        this.angle = angle;
        this.speed = speed;
        /* 
        try {
            image = ImageIO.read(new File(Config.RESOURCES_PATH + "Fireball.png"));
            if (image != null && image.getColorModel().hasAlpha()) {
                return;
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to find file " + Config.RESOURCES_PATH + "Fireball.png", e);
        }
        image = ImageLoader.load("Fireball.png");
        */
    }
    
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.moveX(speed * (float)Math.cos(angle));
        super.moveY(speed * (float)Math.sin(angle));
        super.draw(graphicsHandler);

        //System.out.println("Position: (" + x + ", " + y + "), Speed: " + speed + ", Angle: " + angle);
    }
}
