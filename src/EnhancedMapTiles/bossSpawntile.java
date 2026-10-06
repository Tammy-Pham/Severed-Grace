package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.MapEntityStatus;
import Level.NPC;
import Level.Player;
import Level.TileType;
import NPCs.Boss;
import NPCs.jeard;
import Utils.Point;
import java.util.ArrayList;

public class bossSpawntile extends EnhancedMapTile {
    private static final ArrayList<NPC> enemies = new ArrayList<>();
    private boolean bossSpawned = false;
    public bossSpawntile(Point location){
        //need to make another temp pixel art as the boss
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("BossSpawnTile.png"), 16, 16,0), TileType.PASSABLE);
    }

    @Override 
    public void update(Player player){
        super.update(player);

        if(player.intersects(this) && !bossSpawned){
            bossSpawned = true;
            //when player touches this tile
                //you put all of the enemies into a list an array 
                //once all eliminated you spawn the boss
                    //eliminate enemies
            for(NPC npc: map.getNPCs()){
                if (!(npc instanceof Boss) && !(npc instanceof jeard)) {
                    enemies.add(npc);
                }
            }
                    for(NPC enemy : enemies){
                        enemy.setMapEntityStatus(MapEntityStatus.REMOVED);
                    }

                    Boss boss = new Boss(5, this.getLocation());
                    map.addNPC(boss);
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
