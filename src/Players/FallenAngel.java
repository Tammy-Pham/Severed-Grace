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
        private final int xOffset = 20;
        private final int yOffset = 30;

        public boolean hasSword = false;
        public boolean hasSpear = false;
        public boolean hasGreatSword = false;

        public boolean hasBattleAxe = false;
        public boolean hasPitchFork = false;
        public boolean hasScythe = false;


       public void equipSword() {
        if (hasSword) return;
                attackDamage = 35;
                SpriteSheet swordSheet = new SpriteSheet(
                ImageLoader.load("Main-Character-Full-SpriteSheet-Severed-Grace.png"),
                64, 64, 0
        );

        changeAnimations(loadAnimations(swordSheet), "STAND_RIGHT");

        hasSword = true;
        }     

        public void equipSpear(){
         if(hasSpear) return;
                attackDamage = 150;
                //add sprite sheets
                
         hasSpear = true;
        }

        public void equipGreatSword(){
         if(hasGreatSword) return;
                attackDamage = 200;
                //add sprite sheets
         hasGreatSword = true;
        }

        public void equipAxe() {
                if (hasBattleAxe) return;
                attackDamage = 75; // Set the attack damage to 35 when the axe is equipped
                SpriteSheet axeSheet = new SpriteSheet(
                ImageLoader.load("Main-Character-Full-Ax-SpriteSheet-Severed-Grace.png"),
                64, 64, 0
        );

        changeAnimations(loadAnimations(axeSheet), "STAND_RIGHT");

        hasBattleAxe = true;
        }

        public void equipPitchFork(){
         if(hasPitchFork) return;
                attackDamage = 200;
                //add sprite sheets

         hasPitchFork = true;
        }

        public void equipScythe(){
         if(hasScythe) return;
                attackDamage = 350;
                //add sprite sheets
         hasScythe = true;
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
                            .withBounds(xOffset, yOffset, 24, 32)
                            .build()
            });

            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSubImage(0, 0))
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(xOffset, yOffset, 24, 32)
                            .build()
            });

            put("STAND_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSubImage(0, 0))
                            .withScale(2)
                            .withBounds(xOffset, yOffset, 24, 32)
                            .build()
            });

            put("MOVE_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSubImage(0, 1), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(xOffset, yOffset, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(0, 2), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(xOffset, yOffset, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(0, 3), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(xOffset, yOffset, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(0, 4), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(xOffset, yOffset, 24, 32)
                            .build(),
                new FrameBuilder(spriteSheet.getSubImage(1, 0), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(xOffset, yOffset, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(1, 1), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(xOffset, yOffset, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(1, 2), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(xOffset, yOffset, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(1, 3), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(xOffset, yOffset, 24, 32)
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
                            .withBounds(xOffset, yOffset, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(0, 2), 14)
                            .withScale(2)
                            .withBounds(xOffset, yOffset, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(0, 3), 14)
                            .withScale(2)
                            .withBounds(xOffset, yOffset, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(0, 4), 14)
                            .withScale(2)
                            .withBounds(xOffset, yOffset, 24, 32)
                            .build(),
                new FrameBuilder(spriteSheet.getSubImage(1, 0), 14)
                            .withScale(2)
                            .withBounds(xOffset, yOffset, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(1, 1), 14)
                            .withScale(2)
                            .withBounds(xOffset, yOffset, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(1, 2), 14)
                            .withScale(2)
                            .withBounds(xOffset, yOffset, 24, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(1, 3), 14)
                            .withScale(2)
                            .withBounds(xOffset, yOffset, 24, 32)
                            .build(),
            });

            put("ATTACK_RIGHT", new Frame[] {
        new FrameBuilder(spriteSheet.getSubImage(1, 4), 6)
                .withScale(2)
                .withBounds(xOffset, yOffset, 24, 32)
                .build(),
        new FrameBuilder(spriteSheet.getSubImage(2, 0), 6)
                .withScale(2)
                .withBounds(xOffset, yOffset, 24, 32)
                .build(),
        new FrameBuilder(spriteSheet.getSubImage(2, 1), 6)
                .withScale(2)
                .withBounds(xOffset, yOffset, 24, 32)
                .build(),
        new FrameBuilder(spriteSheet.getSubImage(2, 2), 6)
                .withScale(2)
                .withBounds(xOffset, yOffset, 24, 32)
                .build(),
});

put("ATTACK_LEFT", new Frame[] {
        new FrameBuilder(spriteSheet.getSubImage(1, 4), 6)
                .withScale(2)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .withBounds(xOffset, yOffset, 24, 32)
                .build(),
        new FrameBuilder(spriteSheet.getSubImage(2, 0), 6)
                .withScale(2)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .withBounds(xOffset, yOffset, 24, 32)
                .build(),
        new FrameBuilder(spriteSheet.getSubImage(2, 1), 6)
                .withScale(2)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .withBounds(xOffset, yOffset, 24, 32)
                .build(),
        new FrameBuilder(spriteSheet.getSubImage(2, 2), 6)
                .withScale(2)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .withBounds(xOffset, yOffset, 24, 32)
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
