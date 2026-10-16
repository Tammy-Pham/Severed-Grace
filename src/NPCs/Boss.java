package NPCs;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.ScreenManager;
import EnhancedMapTiles.RNGWeaponDrop;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.NPC;
import Level.Player;
import Utils.Point;
import java.util.HashMap;

// This class is for the walrus NPC
public class Boss extends NPC {

    private boolean hasSpawnedChest = false;

    private Player lastSeenPlayer;

    private static final int CHEST_WIDTH = 128;
    private static final int CHEST_HEIGHT = 128;

    public Boss(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("jeard.png"), 124, 80,0), "STAND_LEFT");
        
        health = 1000;
    }

    @Override
    protected void performAction(Player player) {
        lastSeenPlayer = player;
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
                            .withScale(1)
                            //.withBounds(7, 13, 11, 7)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build()
            });
            put("STAND_RIGHT", new Frame[] {
                   new FrameBuilder(spriteSheet.getSprite(0, 0))
                           .withScale(1)
                           //.withBounds(7, 13, 11, 7)
                           .build()
           });
        }};
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
