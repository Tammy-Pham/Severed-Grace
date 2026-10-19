package NPCs;

import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.NPC;
import Utils.Point;

public class Demon7 extends NPC {
    //npc for the enemy and such
    public Demon7(int id, Point location){
            super(id, location.x, location.y,
            new SpriteSheet(ImageLoader.load("Enemy-PlaceHold-Severed-Grace.png"), 64, 64,0),
            "DEFAULT");
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(2)
                    .withBounds(3, 5, 32, 32)
                    .build()
            });
        }};
    }

}
