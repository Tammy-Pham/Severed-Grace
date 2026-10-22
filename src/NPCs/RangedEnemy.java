package NPCs;

import Builders.FrameBuilder;
import Engine.Config;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.NPC;
import Level.Player;
import Projectiles.Fireball;
import Utils.Point;
import java.util.ArrayList;
import java.util.HashMap;

// This class is for the walrus NPC
public class RangedEnemy extends NPC {
    private long attackDelay = 2400;
    private long regenerationDelay = 1000;

    private long animationStartTime;

    private boolean isRegenerating = false;

    private long fireballMoveDelay = 100; // 0.1 seconds
    private long lastFireballMoveTime = 0;

    private ArrayList<Fireball> fireballs = new ArrayList<>(); // list to keep track of spawned fireballs


    public RangedEnemy(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("Ranged-Enemy-SpriteSheet-Severed-Grace.png"), 64, 64,0), "WINDUP");
        //System.out.println("Ranged Enemy Position: " + x + ", " + y);
    }

    @Override
public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {

    HashMap<String, Frame[]> animations = new HashMap<>();

    // IDLE: Frame 1
    animations.put("IDLE", new Frame[] {
        new FrameBuilder(spriteSheet.getSubImage(0, 0))
            .withScale(1.5f)
            .build()
    });

    // WINDUP: Frames 2-11
    Frame[] windupFrames = new Frame[10];

    for (int i = 0; i < 10; i++) {

        int spriteIndex = i + 1;

        int row = spriteIndex / 5;
        int column = spriteIndex % 5;

        windupFrames[i] = new FrameBuilder(
                spriteSheet.getSubImage(row, column), 14)
                .withScale(1.5f)
                .build();
    }

    animations.put("WINDUP", windupFrames);

    // REGENERATE: Frames 12-23
    Frame[] regenerationFrames = new Frame[12];

    for (int i = 0; i < 12; i++) {

        int spriteIndex = i + 11;

        int row = spriteIndex / 5;
        int column = spriteIndex % 5;

        regenerationFrames[i] = new FrameBuilder(
                spriteSheet.getSubImage(row, column), 5)
                .withScale(1.5f)
                .build();
    }

    animations.put("REGENERATE", regenerationFrames);

    // Generate right-facing versions of every animation
String[] animationNames = {"IDLE", "WINDUP", "REGENERATE"};

for (String name : animationNames) {

    Frame[] originalFrames = animations.get(name);
    Frame[] flippedFrames = new Frame[originalFrames.length];

    for (int i = 0; i < originalFrames.length; i++) {

        int spriteIndex;

        if (name.equals("IDLE")) {
            spriteIndex = 0;
        }
        else if (name.equals("WINDUP")) {
            spriteIndex = i + 1;
        }
        else {
            spriteIndex = i + 11;
        }

        int row = spriteIndex / 5;
        int column = spriteIndex % 5;

        int delay = name.equals("WINDUP") ? 14 :
                    name.equals("REGENERATE") ? 5 : 0;

        FrameBuilder builder;

        if (name.equals("IDLE")) {
            builder = new FrameBuilder(
                spriteSheet.getSubImage(row, column)
            );
        } else {
            builder = new FrameBuilder(
                spriteSheet.getSubImage(row, column), delay
            );
        }

        flippedFrames[i] = builder
                .withScale(1.5f)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();
    }

    animations.put(name + "_RIGHT", flippedFrames);
}

    return animations;
}

    @Override
public void performAction(Player player) {

    long currentTime = System.currentTimeMillis();

    // Always face the player during the current animation
    if (isRegenerating) {
        facePlayer(player, "REGENERATE");
    } else {
        facePlayer(player, "WINDUP");
    }

    // PHASE 1: Regenerating skull after shooting
    if (isRegenerating) {

        if (currentTime - animationStartTime >= regenerationDelay) {

            isRegenerating = false;
            animationStartTime = currentTime;

            facePlayer(player, "WINDUP");
        }
    }

    // PHASE 2: Preparing to shoot
    else {

        if (currentTime - animationStartTime >= attackDelay) {

            // Fireball launches after the wind-up
            spawnFireball(player);

            // Begin skull regeneration
            isRegenerating = true;
            animationStartTime = currentTime;

            facePlayer(player, "REGENERATE");
        }
    }

    // PHASE 3: Update existing fireballs
    if (currentTime - lastFireballMoveTime >= fireballMoveDelay) {

        for (int i = 0; i < fireballs.size(); i++) {
            fireballs.get(i).update();
        }

        lastFireballMoveTime = currentTime;
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

    private void facePlayer(Player player, String animation) {

    String animationName;

    if (player.getX() > this.getX()) {
        animationName = animation + "_RIGHT";
    } else {
        animationName = animation;
    }

    if (!getCurrentAnimationName().equals(animationName)) {
        setCurrentAnimationName(animationName);
    }
}
}