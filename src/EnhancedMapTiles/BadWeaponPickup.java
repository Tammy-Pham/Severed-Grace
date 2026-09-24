 package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.MapEntityStatus;
import Level.Player;
import Level.TileType;
import Utils.Point;

public class BadWeaponPickup extends EnhancedMapTile {
    public BadWeaponPickup(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Battle_Axe.png"), 64, 64), TileType.PASSABLE);
    }

    @Override
    public void update(Player player) {
        super.update(player);
       if(player.intersects (this)){
        player.subtractKarma(5);
       this.setMapEntityStatus(MapEntityStatus.REMOVED);
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
 