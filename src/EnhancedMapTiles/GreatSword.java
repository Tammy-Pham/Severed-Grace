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

public class GreatSword extends EnhancedMapTile {
    public GreatSword(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("GreatSword-Severed-Grace.png"), 64, 64,0), TileType.PASSABLE);
    }

    @Override
    public void update(Player player) {
        super.update(player);
       if(player.intersects (this)){
        player.addKarma(35);
        //change this to the new sprites
        if(player instanceof Players.FallenAngel){
            ((Players.FallenAngel) player).equipSword();
        }
       this.setMapEntityStatus(MapEntityStatus.REMOVED);
       
       }

    }

    @Override
    protected GameObject loadBottomLayer(SpriteSheet spriteSheet) {
        Frame frame = new FrameBuilder(spriteSheet.getSubImage(0, 0))
                .withScale(3)
                .withBounds(16,16,16,16)
                .build();
        GameObject obj = new GameObject(x, y, frame);
        obj.setScale(1.5f);  

    return obj;
    }
}
 