package Players;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Player;
import java.util.HashMap;

public class FallenAngel extends Player {
        public boolean hasGoodSword = false;
        public boolean hasBadAxe = false;

        /*public void equipSword(){
                if(hasGoodSword) return;
                hasGoodSword = true;

                SpriteSheet swordSheet = new SpriteSheet(ImageLoader.load("Main-Character-Full-SpriteSheet-Severed-Grace.png"), 64, 64, 0);
                this.animations = loadAnimations(swordSheet);

                this.currentAnimationName = "STAND_RIGHT";
                this.currentFrameIndex = 0;
        }
        public void equipAxe(){
                if(hasBadAxe) return;
                hasBadAxe = true;

                SpriteSheet axeSheet = new SpriteSheet(ImageLoader.load("Main-Character-Full-Ax-SpriteSheet-Severed-Grace.png"), 64, 64, 0);
                this.animations = loadAnimations(axeSheet);

                this.currentAnimationName = "STAND_RIGHT";
                this.currentFrameIndex = 0;
        }*/
       public void equipSword() {
        if (hasGoodSword) return;

                SpriteSheet swordSheet = new SpriteSheet(
                ImageLoader.load("Main-Character-Full-SpriteSheet-Severed-Grace.png"),
                64, 64, 0
        );

        changeAnimations(loadAnimations(swordSheet), "STAND_RIGHT");

        hasGoodSword = true;
        }     

        public void equipAxe() {
                if (hasBadAxe) return;
                attackDamage = 35; // Set the attack damage to 35 when the axe is equipped
                SpriteSheet axeSheet = new SpriteSheet(
                ImageLoader.load("Main-Character-Full-Ax-SpriteSheet-Severed-Grace.png"),
                64, 64, 0
        );

        changeAnimations(loadAnimations(axeSheet), "STAND_RIGHT");

        hasBadAxe = true;
        }

    public FallenAngel(float x, float y) {
        super(new SpriteSheet(ImageLoader.load("Main-Character-Full-SpriteSheet-Severed-Grace.png"), 64, 64,0), x, y, "STATIC");
        walkSpeed = 2.3f;
    }
    @Override 
    public void update() {
        super.update();
    }

    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STATIC", new Frame[] {
                    new FrameBuilder(spriteSheet.getSubImage(0, 0))
                            .withScale(2)
                            .withBounds(14, 0, 24, 32)
                            .build()
            });

            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSubImage(0, 0))
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(14, 0, 24, 32)
                            .build()
            });

            put("STAND_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSubImage(0, 0))
                            .withScale(2)
                            .withBounds(14, 0, 24, 32)
                            .build()
            });

            put("MOVE_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSubImage(0, 1), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(0, 2), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(0, 3), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(0, 4), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                new FrameBuilder(spriteSheet.getSubImage(1, 0), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(1, 1), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(1, 2), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(1, 3), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(14, 0, 24, 32)
                            .build(),
            });

            /*put("MOVE_DOWN", new Frame[] {
                    new FrameBuilder(spriteSheet.getSubImage(2, 0), 14)
                            .withScale(2)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(2, 1), 14)
                            .withScale(2)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(2, 2), 14)
                            .withScale(2)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(2, 3), 14)
                            .withScale(2)
                            .withBounds(14, 0, 24, 32)
                            .build()
            }); */

            put("MOVE_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSubImage(0, 1), 14)
                            .withScale(2)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(0, 2), 14)
                            .withScale(2)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(0, 3), 14)
                            .withScale(2)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(0, 4), 14)
                            .withScale(2)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                new FrameBuilder(spriteSheet.getSubImage(1, 0), 14)
                            .withScale(2)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(1, 1), 14)
                            .withScale(2)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(1, 2), 14)
                            .withScale(2)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(1, 3), 14)
                            .withScale(2)
                            .withBounds(14, 0, 24, 32)
                            .build(),
            });

            put("ATTACK_RIGHT", new Frame[] {
        new FrameBuilder(spriteSheet.getSubImage(1, 4), 6)
                .withScale(2)
                .withBounds(14, 0, 24, 32)
                .build(),
        new FrameBuilder(spriteSheet.getSubImage(2, 0), 6)
                .withScale(2)
                .withBounds(14, 0, 24, 32)
                .build(),
        new FrameBuilder(spriteSheet.getSubImage(2, 1), 6)
                .withScale(2)
                .withBounds(14, 0, 24, 32)
                .build(),
        new FrameBuilder(spriteSheet.getSubImage(2, 2), 6)
                .withScale(2)
                .withBounds(14, 0, 24, 32)
                .build(),
});

put("ATTACK_LEFT", new Frame[] {
        new FrameBuilder(spriteSheet.getSubImage(1, 4), 6)
                .withScale(2)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .withBounds(14, 0, 24, 32)
                .build(),
        new FrameBuilder(spriteSheet.getSubImage(2, 0), 6)
                .withScale(2)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .withBounds(14, 0, 24, 32)
                .build(),
        new FrameBuilder(spriteSheet.getSubImage(2, 1), 6)
                .withScale(2)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .withBounds(14, 0, 24, 32)
                .build(),
        new FrameBuilder(spriteSheet.getSubImage(2, 2), 6)
                .withScale(2)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .withBounds(14, 0, 24, 32)
                .build(),
});

            /*put("MOVE_UP", new Frame[] {
                    new FrameBuilder(spriteSheet.getSubImage(4, 0), 14)
                            .withScale(2)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(4, 1), 14)
                            .withScale(2)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(4, 2), 14)
                            .withScale(2)
                            .withBounds(14, 0, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(4, 3), 14)
                            .withScale(2)
                            .withBounds(14, 0, 24, 32)
                            .build()
            });*/

        }};
    }
}
