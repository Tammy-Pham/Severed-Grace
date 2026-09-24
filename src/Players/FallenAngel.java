package Players;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
//import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Player;

import java.util.HashMap;

// This is the class for the Cat player character
// basically just sets some values for physics and then defines animations
public class FallenAngel extends Player {

    public FallenAngel(float x, float y) {
        super(new SpriteSheet(ImageLoader.load("FallenAngel.png"), 24, 24), x, y, "STATIC");
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
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build()
            });

            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build()
            });

            put("STAND_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build()
            });

            put("MOVE_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 14)
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 14)
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 14)
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 14)
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build()
            });

            put("MOVE_DOWN", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 0), 14)
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 1), 14)
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 2), 14)
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 3), 14)
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build()
            });

            put("MOVE_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(3, 0), 14)
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(3, 1), 14)
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(3, 2), 14)
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(3, 3), 14)
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build()
            });

            put("MOVE_UP", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(4, 0), 14)
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(4, 1), 14)
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(4, 2), 14)
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(4, 3), 14)
                            .withScale(3)
                            .withBounds(0, 0, 24, 24)
                            .build()
            });

                /*
            put("STAND_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build()
            });

            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build()
            });

            put("WALK_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build()
            });

            put("WALK_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build()
                        });
                */
        }};
    }
}
