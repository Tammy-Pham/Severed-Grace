package NPCs;

import Builders.FrameBuilder;
import Engine.Config;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.NPC;
import Level.Player;
//import Utils.Direction;
import Utils.Point;
import java.util.HashMap;
import java.util.ArrayList;
import Projectiles.Fireball;

// This class is for the walrus NPC
public class RangedEnemy extends NPC {
    private long attackDelay = 2400; // Wait time between sending fireballs
    private long lastAttackTime = 0; // timestamp of the last attack

    private long fireballMoveDelay = 100; // 0.1 seconds
    private long lastFireballMoveTime = 0;

    private ArrayList<Fireball> fireballs = new ArrayList<>(); // list to keep track of spawned fireballs


    public RangedEnemy(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("Ranged_Enemy.png"), 64, 64,0), "DEFAULT");
        //System.out.println("Ranged Enemy Position: " + x + ", " + y);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(1)
                            .build()
            });
        }};
    }

    public void performAction(Player player) {
        // Check if enough time has passed since the last attack
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAttackTime >= attackDelay) {
            //System.out.println("attack performed");
            spawnFireball(player);
            lastAttackTime = currentTime; // Update the last attack time
        }
        
        if (currentTime - lastFireballMoveTime >= fireballMoveDelay) {
            for (int i = 0; i < fireballs.size(); i++) {
                fireballs.get(i).update();
            }
            lastFireballMoveTime = currentTime; // Update the last fireball move time
        }
    }

    public void spawnFireball(Player player) {
        float deltaX = player.getX() - this.x;
        float deltaY = player.getY() - this.y;
        float angle = (float) Math.atan2(deltaY, deltaX);

        Fireball f = new Fireball(this.x - map.getCamera().getX(), this.y - map.getCamera().getY(), angle, 4.0f);
        fireballs.add(f);
    }
      
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        Player player = map.getPlayer();

        super.draw(graphicsHandler);

        // Fireball drawing and collision detection
        for (int i = 0; i < fireballs.size(); i++) {
            fireballs.get(i).draw(graphicsHandler);
            
            // Remove fireball if it goes off-screen
            if (fireballs.get(i).getX() < 0 || fireballs.get(i).getX() > Config.GAME_WINDOW_WIDTH ||
                fireballs.get(i).getY() < 0 || fireballs.get(i).getY() > Config.GAME_WINDOW_HEIGHT) {
                fireballs.remove(i);
                i--; // Adjust index after removal
            }
            // Remove fireball if it collides with the player (and also take damage)
            else if (fireballs.get(i).touching(player)) {
                player.takeDamage(1); // Assuming 1 is the damage amount
                fireballs.remove(i);
                i--; // Adjust index after removal
            }
        }
    }
}