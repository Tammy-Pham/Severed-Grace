package NPCs;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
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
    private boolean isPerformingAttack = false;
    private long attackAnimationStartTime;

    private static final long ATTACK_ANIMATION_DURATION = 300;

    public Enemy(int id, Point location) {
        super(id, location.x, location.y,
                new SpriteSheet(ImageLoader.load("Enemy-SpriteSheet-Severed-Grace.png"), 64, 64,0),
                "IDLE");
    }

    private void facePlayer(Player player, String animation) {
        String animationName;

        // Original sprite faces LEFT
        if (player.getX() < this.getX()) {
            animationName = animation;
        } else {
            // Flip sprite to face RIGHT
            animationName = animation + "_LEFT";
        }

        if (!getCurrentAnimationName().equals(animationName)) {
            setCurrentAnimationName(animationName);
        }
    }

    @Override
public void performAction(Player player) {

    // Check if the player is touching the enemy
    boolean playerTouching = player.touching(this);
long currentTime = System.currentTimeMillis();

// PHASE 1: Play the actual attack animation
if (isPerformingAttack) {

    if (currentTime - attackAnimationStartTime
            >= ATTACK_ANIMATION_DURATION) {

        isPerformingAttack = false;
    } else {
        return;
    }
}

// PHASE 2: Enemy is winding up
if (isAttacking) {

    if (currentTime - attackStartTime >= attackDelay) {

        // Check if player is STILL touching
        if (player.touching(this)) {
            player.takeDamage(damageAmount);
            hasTakenDamage = true;
        }

        // Wind-up finished
        isAttacking = false;

        // Begin attack animation
        isPerformingAttack = true;
        attackAnimationStartTime = currentTime;

        if (getCurrentAnimationName().equals("WINDUP_LEFT")) {
            setCurrentAnimationName("ATTACK_LEFT");
        } else {
            setCurrentAnimationName("ATTACK");
        }

    }

    return;
}

// PHASE 3: Player enters attack range
if (playerTouching) {

    isAttacking = true;
    attackStartTime = currentTime;

    facePlayer(player, "WINDUP");

    return;
}

// PHASE 4: Chase player
facePlayer(player, "WALK");

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

    HashMap<String, Frame[]> animations = new HashMap<>();

    // IDLE
    animations.put("IDLE", new Frame[] {
        new FrameBuilder(spriteSheet.getSubImage(0, 0))
            .withScale(2)
            .withBounds(3, 5, 32, 32)
            .build()
    });

    // WALKING
    animations.put("WALK", new Frame[] {
        new FrameBuilder(spriteSheet.getSubImage(0, 0), 14)
            .withScale(2).withBounds(3, 5, 32, 32).build(),
        new FrameBuilder(spriteSheet.getSubImage(0, 1), 14)
            .withScale(2).withBounds(3, 5, 32, 32).build(),
        new FrameBuilder(spriteSheet.getSubImage(0, 2), 14)
            .withScale(2).withBounds(3, 5, 32, 32).build(),
        new FrameBuilder(spriteSheet.getSubImage(0, 3), 14)
            .withScale(2).withBounds(3, 5, 32, 32).build(),
        new FrameBuilder(spriteSheet.getSubImage(0, 4), 14)
            .withScale(2).withBounds(3, 5, 32, 32).build(),
        new FrameBuilder(spriteSheet.getSubImage(1, 0), 14)
            .withScale(2).withBounds(3, 5, 32, 32).build(),
        new FrameBuilder(spriteSheet.getSubImage(1, 1), 14)
            .withScale(2).withBounds(3, 5, 32, 32).build(),
        new FrameBuilder(spriteSheet.getSubImage(1, 2), 14)
            .withScale(2).withBounds(3, 5, 32, 32).build()
    });
    animations.put("WALK_LEFT", new Frame[] {
    new FrameBuilder(spriteSheet.getSubImage(0, 0), 14)
        .withScale(2)
        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        .withBounds(3, 5, 32, 32)
        .build(),

    new FrameBuilder(spriteSheet.getSubImage(0, 1), 14)
        .withScale(2)
        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        .withBounds(3, 5, 32, 32)
        .build(),

    new FrameBuilder(spriteSheet.getSubImage(0, 2), 14)
        .withScale(2)
        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        .withBounds(3, 5, 32, 32)
        .build(),

    new FrameBuilder(spriteSheet.getSubImage(0, 3), 14)
        .withScale(2)
        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        .withBounds(3, 5, 32, 32)
        .build(),

    new FrameBuilder(spriteSheet.getSubImage(0, 4), 14)
        .withScale(2)
        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        .withBounds(3, 5, 32, 32)
        .build(),

    new FrameBuilder(spriteSheet.getSubImage(1, 0), 14)
        .withScale(2)
        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        .withBounds(3, 5, 32, 32)
        .build(),

    new FrameBuilder(spriteSheet.getSubImage(1, 1), 14)
        .withScale(2)
        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        .withBounds(3, 5, 32, 32)
        .build(),

    new FrameBuilder(spriteSheet.getSubImage(1, 2), 14)
        .withScale(2)
        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        .withBounds(3, 5, 32, 32)
        .build()
});

    // WIND-UP
    animations.put("WINDUP", new Frame[] {
        new FrameBuilder(spriteSheet.getSubImage(1, 3), 9)
            .withScale(2).withBounds(3, 5, 32, 32).build(),
        new FrameBuilder(spriteSheet.getSubImage(1, 4), 9)
            .withScale(2).withBounds(3, 5, 32, 32).build(),
        new FrameBuilder(spriteSheet.getSubImage(2, 0), 9)
            .withScale(2).withBounds(3, 5, 32, 32).build(),
        new FrameBuilder(spriteSheet.getSubImage(2, 1), 9)
            .withScale(2).withBounds(3, 5, 32, 32).build(),
        new FrameBuilder(spriteSheet.getSubImage(2, 2), 9)
            .withScale(2).withBounds(3, 5, 32, 32).build(),
        new FrameBuilder(spriteSheet.getSubImage(2, 3), 9)
            .withScale(2).withBounds(3, 5, 32, 32).build(),
        new FrameBuilder(spriteSheet.getSubImage(2, 4), 9)
            .withScale(2).withBounds(3, 5, 32, 32).build()
    });
    animations.put("WINDUP_LEFT", new Frame[] {
    new FrameBuilder(spriteSheet.getSubImage(1, 3), 9)
        .withScale(2)
        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        .withBounds(3, 5, 32, 32)
        .build(),

    new FrameBuilder(spriteSheet.getSubImage(1, 4), 9)
        .withScale(2)
        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        .withBounds(3, 5, 32, 32)
        .build(),

    new FrameBuilder(spriteSheet.getSubImage(2, 0), 9)
        .withScale(2)
        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        .withBounds(3, 5, 32, 32)
        .build(),

    new FrameBuilder(spriteSheet.getSubImage(2, 1), 9)
        .withScale(2)
        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        .withBounds(3, 5, 32, 32)
        .build(),

    new FrameBuilder(spriteSheet.getSubImage(2, 2), 9)
        .withScale(2)
        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        .withBounds(3, 5, 32, 32)
        .build(),

    new FrameBuilder(spriteSheet.getSubImage(2, 3), 9)
        .withScale(2)
        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        .withBounds(3, 5, 32, 32)
        .build(),

    new FrameBuilder(spriteSheet.getSubImage(2, 4), 9)
        .withScale(2)
        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        .withBounds(3, 5, 32, 32)
        .build()
});

    // ATTACK
    animations.put("ATTACK", new Frame[] {
        new FrameBuilder(spriteSheet.getSubImage(3, 0), 6)
            .withScale(2).withBounds(3, 5, 32, 32).build(),
        new FrameBuilder(spriteSheet.getSubImage(3, 1), 6)
            .withScale(2).withBounds(3, 5, 32, 32).build(),
        new FrameBuilder(spriteSheet.getSubImage(3, 2), 6)
            .withScale(2).withBounds(3, 5, 32, 32).build()
    });

    animations.put("ATTACK_LEFT", new Frame[] {
    new FrameBuilder(spriteSheet.getSubImage(3, 0), 6)
        .withScale(2)
        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        .withBounds(3, 5, 32, 32)
        .build(),

    new FrameBuilder(spriteSheet.getSubImage(3, 1), 6)
        .withScale(2)
        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        .withBounds(3, 5, 32, 32)
        .build(),

    new FrameBuilder(spriteSheet.getSubImage(3, 2), 6)
        .withScale(2)
        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        .withBounds(3, 5, 32, 32)
        .build()
});

    return animations;
}

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}