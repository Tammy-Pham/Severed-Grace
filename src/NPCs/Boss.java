package NPCs;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.ScreenManager;
import EnhancedMapTiles.RNGWeaponDrop;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import GameObject.Rectangle;
import Level.NPC;
import Level.Player;
import Utils.Point;
import java.util.HashMap;
import java.awt.Color;

// This class is for the walrus NPC
public class Boss extends NPC {

    private boolean hasSpawnedChest = false;

    private Player lastSeenPlayer;

    private static final int CHEST_WIDTH = 128;
    private static final int CHEST_HEIGHT = 128;

    
    private static final int MAX_HEALTH = 100;
    private static final int HEALTH_STEP = 10;         
    private static final int HEALTH_BAR_WIDTH = 200;
    private static final int HEALTH_BAR_HEIGHT = 14;
    private static final int HEALTH_BAR_GAP = 10; 

    private static final Color HEALTH_BAR_FILL = new Color(200, 30, 30);
    private static final Color HEALTH_BAR_EMPTY = new Color(50, 50, 50);
    private static final Color HEALTH_BAR_BORDER = Color.BLACK;

        
    private static final int BOSS_DAMAGE = 10;    
    private boolean isAttacking = false;
    private long attackStartTime;
    private long attackDelay = 1000;
    private boolean isPerformingAttack = false;
    private long attackAnimationStartTime;
    private static final long AttackAnimationDuration = 300;

    public Boss(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("Sin-Of-Sloth-Severed-Grace.png"), 64, 64,0), "STAND_LEFT");
        
        health = MAX_HEALTH;
    }

    @Override
    protected void performAction(Player player) {
        lastSeenPlayer = player;

        // Check if the player is touching the boss
        boolean playerTouching = player.touching(this);
        long currentTime = System.currentTimeMillis();

        // PHASE 1: Boss is finishing its attack
        if (isPerformingAttack) {
            if (currentTime - attackAnimationStartTime >= AttackAnimationDuration) {
                isPerformingAttack = false;
            } else {
                return;
            }
        }

        // PHASE 2: Boss is winding up
        if (isAttacking) {
            if (currentTime - attackStartTime >= attackDelay) {
                // Check if player is STILL touching
                if (player.touching(this)) {
                    player.takeDamage(BOSS_DAMAGE);
                }

                // Wind-up finished, begin attack
                isAttacking = false;
                isPerformingAttack = true;
                attackAnimationStartTime = currentTime;
            }
            return;
        }

        // PHASE 3: Player enters attack range
        if (playerTouching) {
            isAttacking = true;
            attackStartTime = currentTime;
        }
    }

    @Override 
    public void takeDamage(int amount) {
        super.takeDamage(amount);
        
        if (getHealth() == 0 && !hasSpawnedChest) {
            spawnChest();
            hasSpawnedChest = true;
        }
    }
    private void spawnChest() {
        int screenCenterX = Math.round(map.getCamera().getX()) + (ScreenManager.getScreenWidth() / 2);
        int screenCenterY = Math.round(map.getCamera().getY()) + (ScreenManager.getScreenHeight() / 2);
 
        int spawnX = screenCenterX - (CHEST_WIDTH / 2);
        int spawnY = screenCenterY - (CHEST_HEIGHT / 2);
 
        if (lastSeenPlayer != null) {
            int playerX = Math.round(lastSeenPlayer.getX());
            int playerY = Math.round(lastSeenPlayer.getY());
            int playerWidth = lastSeenPlayer.getWidth();
            int playerHeight = lastSeenPlayer.getHeight();
 
            boolean playerIsInTheWay = spawnX < playerX + playerWidth
                    && spawnX + CHEST_WIDTH > playerX
                    && spawnY < playerY + playerHeight
                    && spawnY + CHEST_HEIGHT > playerY;
 
            
            if (playerIsInTheWay) {
                spawnX = playerX + playerWidth + 20;
            }
        }
 
        Point chestLocation = new Point(spawnX, spawnY);
        map.addEnhancedMapTile(new RNGWeaponDrop(chestLocation));
    }


    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(5)
                            .withBounds(4, 27, 56, 32)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build()
            });
            put("STAND_RIGHT", new Frame[] {
                   new FrameBuilder(spriteSheet.getSprite(0, 0))
                           .withScale(5)
                           .withBounds(4, 27, 56, 32)
                           .build()
           });
        }};
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawHealthBar(graphicsHandler);
    }
    private int getDisplayedHealth(){
        return ((getHealth() + HEALTH_STEP - 1) / HEALTH_STEP) * HEALTH_STEP;
    }

    private void drawHealthBar(GraphicsHandler graphicsHandler) {
        if(getHealth() < 0) {
            return;
     }

        Rectangle bounds = getCalibratedBounds();
        int barX = Math.round(bounds.getX1() + (bounds.getWidth() - HEALTH_BAR_WIDTH) / 2f);
        int barY = Math.round(bounds.getY1()) - HEALTH_BAR_HEIGHT - HEALTH_BAR_GAP;

        int filledWidth = HEALTH_BAR_WIDTH * getDisplayedHealth() / MAX_HEALTH;

        graphicsHandler.drawFilledRectangle(barX, barY, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT, HEALTH_BAR_EMPTY);
        graphicsHandler.drawFilledRectangle(barX, barY, filledWidth, HEALTH_BAR_HEIGHT, HEALTH_BAR_FILL);
        graphicsHandler.drawRectangle(barX, barY, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT, HEALTH_BAR_BORDER, 2);
    
    }
}
