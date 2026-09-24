package NPCs;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.NPC;
import Level.Player;
import Utils.Direction;
import Utils.Point;
import java.util.HashMap;

public class Enemy extends NPC {
    private int totalAmountMoved = 0;
    private Direction direction = Direction.RIGHT;
    private float speed = 1.5f;

    public Enemy(int id, Point location) {
        super(id, location.x, location.y,
                new SpriteSheet(ImageLoader.load("Enemy-PlaceHold-Severed-Grace.png"), 64, 64),
                "DEFAULT");
    }

    @Override
    public void performAction(Player player) {

        float enemyX = getBounds().getX();
        float enemyY = getBounds().getY();

        float playerX = player.getBounds().getX();
        float playerY = player.getBounds().getY();

        // Try to move horizontally toward the player
        if (enemyX < playerX) {
            if (moveXHandleCollision(speed) == 0) {
                // Can't move right, so try moving vertically
                moveYHandleCollision(speed);
            }
        }
        else if (enemyX > playerX) {
            if (moveXHandleCollision(-speed) == 0) {
                // Can't move left, so try moving vertically
                moveYHandleCollision(speed);
            }
        }

        // Try to move vertically toward the player
        if (enemyY < playerY) {
            if (moveYHandleCollision(speed) == 0) {
                // Can't move down, so try moving horizontally
                moveXHandleCollision(speed);
            }
        }
        else if (enemyY > playerY) {
            if (moveYHandleCollision(-speed) == 0) {
                // Can't move up, so try moving horizontally
                moveXHandleCollision(speed);
            }
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(1)
                    .withBounds(3, 5, 58, 55)
                    .build()
            });
        }};
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}