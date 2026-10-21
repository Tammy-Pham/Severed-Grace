package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.Mouse;
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
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Closed_Chest.png"), 64, 64,0), TileType.NOT_PASSABLE);

    }

    //array variable for the weapons
    @Override
    public void update(Player player){
        super.update(player);
            //where the player is dealing with update
            //instead of player intersecting the treasure chest, should have where you are not intersecting the treasure chest, and click on it instead 
            //to get the treasure chest to open and drop a wepaon
        if(!hasDropped && Mouse.isButtonDown(1) && player.touching(this)){
            hasDropped = true;
            String chosen = WeaponNames[random.nextInt(WeaponNames.length)];

            Point spawnPoint = new Point(getLocation().x + (getWidth() - 10)/2, getLocation().y + (getHeight() - 11)/2);
                
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
            .withScale(2)
            .withBounds(9,-10,10,11)
            .build();
    return new GameObject(x, y, frame);
    }
}