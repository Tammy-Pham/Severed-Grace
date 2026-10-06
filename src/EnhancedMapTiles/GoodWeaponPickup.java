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

public class GoodWeaponPickup extends EnhancedMapTile {
    public GoodWeaponPickup(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Sword.png"), 64, 64,0), TileType.PASSABLE);
    }

    @Override
    public void update(Player player) {
        super.update(player);
       if(player.intersects (this)){
        player.addKarma(5);
        if(player instanceof Players.FallenAngel){
            ((Players.FallenAngel) player).equipSword();
        }
        //the code line below deals with removing the weapon
       this.setMapEntityStatus(MapEntityStatus.REMOVED);
       
       }

    }

    @Override
    protected GameObject loadBottomLayer(SpriteSheet spriteSheet) {
        Frame frame = new FrameBuilder(spriteSheet.getSubImage(0, 0))
                .withScale(3)
                .build();
        GameObject obj = new GameObject(x, y, frame);
        obj.setScale(1f);  

    return obj;
    }
}
 