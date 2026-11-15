
package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.Mouse;
import GameObject.Frame;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.Point;
import java.util.HashMap;
import java.util.Random;

public class RNGWeaponDrop extends EnhancedMapTile {

    // All possible weapon drops
    private static final String[] WeaponNames = {
        "sword",
        "Battle_Axe",
        "Scythe",
        "Pitchfork",
        "Gauntlets",
        "Greatsword",
        "Spear",
        "Mace"
    };

    private boolean hasDropped = false;
    private boolean isOpening = false;

    private final Random random = new Random();

    public RNGWeaponDrop(Point location) {
        super(
            location.x,
            location.y,
            new SpriteSheet(
                ImageLoader.load("Closed_Chest.png"),
                64, 64, 0
            ),
            TileType.NOT_PASSABLE
        );
    }

    @Override
    public void update(Player player) {
        super.update(player);

        // STEP 1: Player clicks the closed chest
        if (!hasDropped &&
            !isOpening &&
            Mouse.isButtonDown(1) &&
            player.touching(this)) {

            isOpening = true;
            startOpeningAnimation();
            return;
        }

        // STEP 2: Wait for all 22 frames to finish
        if (isOpening &&
            bottomLayer.getCurrentFrameIndex() >= 21) {

            isOpening = false;
            hasDropped = true;

            // Spawn the reward after opening
            spawnReward();
        }
    }

    // Plays the chest opening animation
    private void startOpeningAnimation() {

        SpriteSheet chestSheet = new SpriteSheet(
            ImageLoader.load("Open-Chest-Severed-Grace.png"),
            64, 64, 0
        );

        // The sprite sheet contains 22 frames
        Frame[] openingFrames = new Frame[22];

        for (int i = 0; i < openingFrames.length; i++) {

            // Five columns per row
            int row = i / 5;
            int column = i % 5;

            // Stop on the final frame
            int delay = (i == 21) ? 0 : 8;

            openingFrames[i] = new FrameBuilder(
                chestSheet.getSubImage(row, column),
                delay
            )
                .withScale(2)
                .withBounds(9, -10, 50, 50)
                .build();
        }

        // Add the opening animation
        HashMap<String, Frame[]> animations =
            bottomLayer.getAnimations();

        animations.put("OPEN", openingFrames);

        // Begin playing it
        bottomLayer.setCurrentAnimationName("OPEN");
    }

    // Spawns one random weapon after opening
    private void spawnReward() {

        String chosen = WeaponNames[
            random.nextInt(WeaponNames.length)
        ];

        Point spawnPoint = new Point(
            getLocation().x + (getWidth() - 10) / 2,
            getLocation().y + (getHeight() - 11) / 2
        );

        if (chosen.equals("sword")) {

            map.addEnhancedMapTile(
                new Sword(spawnPoint)
            );

        } else if (chosen.equals("Battle_Axe")) {

            map.addEnhancedMapTile(
                new BattleAxe(spawnPoint)
            );

        } else if (chosen.equals("Scythe")) {

            map.addEnhancedMapTile(
                new Scythe(spawnPoint)
            );

        } else if (chosen.equals("Pitchfork")) {

            map.addEnhancedMapTile(
                new PitchFork(spawnPoint)
            );

        } else if (chosen.equals("Gauntlets")) {

            map.addEnhancedMapTile(
                new Gauntlets(spawnPoint)
            );

        } else if (chosen.equals("Greatsword")) {

            map.addEnhancedMapTile(
                new GreatSword(spawnPoint)
            );

        } else if (chosen.equals("Spear")) {

            map.addEnhancedMapTile(
                new Spear(spawnPoint)
            );

        } else if (chosen.equals("Mace")) {

            map.addEnhancedMapTile(
                new Mace(spawnPoint)
            );
        }
    }

    // Initial closed chest sprite
    @Override
    protected GameObject loadBottomLayer(
            SpriteSheet spriteSheet) {

        Frame frame = new FrameBuilder(
            spriteSheet.getSubImage(0, 0)
        )
            .withScale(2)
            .withBounds(9, -10, 50, 50)
            .build();

        return new GameObject(x, y, frame);
    }
}
