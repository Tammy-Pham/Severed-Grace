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
import java.util.Random;

public class RNGWeaponDrop extends EnhancedMapTile{
    private static final String[] WeaponNames = {"sword", "Battle_Axe"};
    private boolean hasDropped = false;
    private final Random random = new Random();
    public RNGWeaponDrop(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("RNGWeaponDropTile.png"), 16, 16), TileType.PASSABLE);

    }

    //array variable for the weapons
    @Override
    public void update(Player player){
        super.update(player);
            //where the player is dealing with update
        if(!hasDropped && player.intersects(this)){
            hasDropped = true;
            String chosen = WeaponNames[random.nextInt(WeaponNames.length)];

            Point spawnPoint = new Point(getLocation().x + 96, getLocation().y);
                
            if (chosen.equals("sword")) {
                map.addEnhancedMapTile(new GoodWeaponPickup(spawnPoint));
            }else if(chosen.equals("Battle_Axe")){
                map.addEnhancedMapTile(new BadWeaponPickup(spawnPoint));
            }
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