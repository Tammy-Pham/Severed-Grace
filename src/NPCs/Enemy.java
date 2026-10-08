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
    private static final int damageAmount= 10; // amount of damage the enemy deals when attacking
    private boolean hasTakenDamage = false;
    private boolean isAttacking = false;  //flag to indicate wether the enemy is in the "wind up" state in the attack
    private long attackStartTime;  //when declared, this will store the timestamp of when the attack started
    private long attackDelay = 1000; // full second

    public Enemy(int id, Point location) {
        super(id, location.x, location.y,
                new SpriteSheet(ImageLoader.load("Enemy-PlaceHold-Severed-Grace.png"), 64, 64,0),
                "DEFAULT");
    }

    @Override
public void performAction(Player player) {

    // Check if the player is touching the enemy
    boolean playerTouching = player.touching(this);

    // If we are currently in the attack wind-up
    if (isAttacking) {

    // Wait the full 500ms before doing anything
    if (System.currentTimeMillis() - attackStartTime >= attackDelay) {

        // Check AGAIN after the wind-up is finished
        boolean playerStillTouching = player.touching(this);

        if (playerStillTouching) {
            player.takeDamage(damageAmount);
            hasTakenDamage = true;
        }

        // Attack is finished
        isAttacking = false;
    }

    // Stay completely still during the entire wind-up
    return;
}

    // Player has just touched the enemy, so start the attack
    if (playerTouching) {
        isAttacking = true;
        attackStartTime = System.currentTimeMillis();
        return;
    }

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
/* 
    old movement method, just keeping it here in case of emergency implementation.
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
*/
    @Override
    public void update(Player player) {
        super.update(player);
        /* 
        rest of the old damage method, keeping it here in case of emergency implementation.
        boolean playerTouching = player.touching(this);

        if (playerTouching == true) {
            attackStartTime = System.currentTimeMillis();
            speed = 0;
            if (System.currentTimeMillis() - attackStartTime >= attackDelay){
                //do animation!
                if(playerTouching == true){
                    player.takeDamage(damageAmount);
                    hasTakenDamage = true;
                }   
            }
            speed = 1.5f;
            //player.takeDamage(damageAmount);
            //hasTakenDamage = true;
        } else if (playerTouching == false) {
            hasTakenDamage = false;
        }
        */
    }


    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(2)
                    .withBounds(3, 5, 32, 32)
                    .build()
            });
        }};
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}