package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.Point;

public class DamageTile extends EnhancedMapTile {
    private static final int damageAmount= 5;
    private boolean hasTakenDamage = false;

    public DamageTile(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("DamageTile.png"), 16, 16), TileType.PASSABLE);
    }

    @Override
    public void update(Player player) {
        super.update(player);
        
        boolean playerTouching = player.touching(this);

        if (playerTouching == true && hasTakenDamage == false) {
            player.takeDamage(damageAmount);
            hasTakenDamage = true;
        } else if (playerTouching == false) {
            hasTakenDamage = false;
        }
    }
    @Override
    protected GameObject loadBottomLayer(SpriteSheet spriteSheet) {
        Frame frame = new FrameBuilder(spriteSheet.getSubImage(0, 0))
            .withScale(3)
            .build();
    return new GameObject(x, y, frame);
    }
}