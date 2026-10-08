package NPCs;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.NPC;
//import Level.Player;
//import Utils.Direction;
import Utils.Point;
import java.util.HashMap;

// This class is for the walrus NPC
public class RangedEnemy extends NPC {
    public RangedEnemy(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("Ranged_Enemy.png"), 64, 64,0), "DEFAULT");
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
    
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}