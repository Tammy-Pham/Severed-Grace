package Players;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.Player;
import java.util.HashMap;

public class FallenAngel extends Player {
        public boolean hasGoodSword = false;

        public void equipSword(){
                if(hasGoodSword) return;
                hasGoodSword = true;

                SpriteSheet swordSheet = new SpriteSheet(ImageLoader.load("FallenAngel_GoodSword.png"), 64, 64, 0);
                this.animations = loadAnimations(swordSheet);

                this.currentAnimationName = "STAND_RIGHT";
                this.currentFrameIndex = 0;
        }

    public FallenAngel(float x, float y) {
        super(new SpriteSheet(ImageLoader.load("FallenAngel_Default.png"), 64, 64,0), x, y, "STATIC");
        walkSpeed = 2.3f;
    }

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
                            .withBounds(0, 0, 32, 32)
                            .build()
            });

            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSubImage(0, 0))
                            .withScale(2)
                            .withBounds(0, 0, 32, 32)
                            .build()
            });

            put("STAND_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSubImage(0, 0))
                            .withScale(2)
                            .withBounds(0, 0, 32, 32)
                            .build()
            });

            put("MOVE_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSubImage(1, 0), 14)
                            .withScale(2)
                            .withBounds(0, 0, 32, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(1, 1), 14)
                            .withScale(2)
                            .withBounds(0, 0, 32, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(1, 2), 14)
                            .withScale(2)
                            .withBounds(0, 0, 32, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(1, 3), 14)
                            .withScale(2)
                            .withBounds(0, 0, 32, 32)
                            .build()
            });

            put("MOVE_DOWN", new Frame[] {
                    new FrameBuilder(spriteSheet.getSubImage(2, 0), 14)
                            .withScale(2)
                            .withBounds(0, 0, 32, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(2, 1), 14)
                            .withScale(2)
                            .withBounds(0, 0, 32, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(2, 2), 14)
                            .withScale(2)
                            .withBounds(0, 0, 32, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(2, 3), 14)
                            .withScale(2)
                            .withBounds(0, 0, 32, 32)
                            .build()
            });

            put("MOVE_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSubImage(3, 0), 14)
                            .withScale(2)
                            .withBounds(0, 0, 32, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(3, 1), 14)
                            .withScale(2)
                            .withBounds(0, 0, 32, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(3, 2), 14)
                            .withScale(2)
                            .withBounds(0, 0, 32, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(3, 3), 14)
                            .withScale(2)
                            .withBounds(0, 0, 32, 32)
                            .build()
            });

            put("MOVE_UP", new Frame[] {
                    new FrameBuilder(spriteSheet.getSubImage(4, 0), 14)
                            .withScale(2)
                            .withBounds(0, 0, 32, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(4, 1), 14)
                            .withScale(2)
                            .withBounds(0, 0, 32, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(4, 2), 14)
                            .withScale(2)
                            .withBounds(0, 0, 32, 32)
                            .build(),
                    new FrameBuilder(spriteSheet.getSubImage(4, 3), 14)
                            .withScale(2)
                            .withBounds(0, 0, 32, 32)
                            .build()
            });

        }};
    }
}
