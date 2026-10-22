package Scripts;
import java.util.ArrayList;
import Level.NPC;
import NPCs.Enemy;
import NPCs.RangedEnemy;
import Utils.Point;

// Spawn 3 melee enemies and 3 ranged enemies. 
public class EnemySpawner {
    public static ArrayList<NPC> spawnEnemies(int meleeCount, int rangedCount) {
        ArrayList<NPC> spawnedNPCs = new ArrayList<>();

        for (int i = 0; i < meleeCount; i++) {
            spawnedNPCs.add(new Enemy(4, new Point(100 + i * 50, 100)));
        }
        for (int i = 0; i < rangedCount; i++) {
            spawnedNPCs.add(new RangedEnemy(5, new Point(420 - i * 69, 621)));
        }
        return spawnedNPCs;
    }
}
