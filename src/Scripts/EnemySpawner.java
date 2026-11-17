package Scripts;
import java.util.ArrayList;
import Level.Map;
import Level.MapTile;
import Level.MapEntityStatus;
import Level.NPC;
import Level.TileType;
import GameObject.Rectangle;
import NPCs.Enemy;
import NPCs.RangedEnemy;
import Utils.Point;

public class EnemySpawner {

    private static final int PLAYER_START_TILE_X = 10;
    private static final int PLAYER_START_TILE_Y = 10;
    private static final int PLAYER_START_BOX_SIZE = 128;

    private static final int MIN_DISTANCE_FROM_PLAYER = 200;

    private static final int MIN_GAP_FROM_PLAYER = 48;
    private static final int MIN_GAP_BETWEEN_ENEMIES = 16;
    private static final int MAX_ATTEMPTS = 200;

    
    public static ArrayList<NPC> spawnEnemies(Map map, int meleeCount, int rangedCount) {
        ArrayList<NPC> spawnedNPCs = new ArrayList<>();

        for (int i = 0; i < meleeCount; i++) {
            Enemy enemy = new Enemy(4, new Point(0, 0));
            placeAtRandomSpot(enemy, map, spawnedNPCs);
            spawnedNPCs.add(enemy);
        }
        for (int i = 0; i < rangedCount; i++) {
            RangedEnemy rangedEnemy = new RangedEnemy(5, new Point(0, 0));
            placeAtRandomSpot(rangedEnemy, map, spawnedNPCs);
            spawnedNPCs.add(rangedEnemy);
        }
        return spawnedNPCs;
    }

    private static void placeAtRandomSpot(NPC enemy, Map map, ArrayList<NPC> spawnedSoFar) {
        Rectangle playerBounds = getPlayerBounds(map);
        Point closeToPlayerSpot = null;   
        Point crowdedSpot = null;         

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            int tileX = (int) (Math.random() * map.getWidth());
            int tileY = (int) (Math.random() * map.getHeight());
            Point tileLocation = map.getMapTile(tileX, tileY).getLocation();

            enemy.setX(tileLocation.x);
            enemy.setY(tileLocation.y);

            if (!isOnOpenFloor(enemy, map)) {
                continue;
            }

           
            if (isTouchingPlayer(enemy, playerBounds)) {
                continue;
            }

            if (isTooCloseToOtherEnemies(enemy, map, spawnedSoFar)) {
                if (crowdedSpot == null) {
                    crowdedSpot = tileLocation;
                }
                continue;
            }

            if (getDistanceToPlayer(enemy, playerBounds) >= MIN_DISTANCE_FROM_PLAYER) {
                return; 
            }

            if (closeToPlayerSpot == null) {
                closeToPlayerSpot = tileLocation;
            }
        }

        Point fallback = closeToPlayerSpot != null ? closeToPlayerSpot : crowdedSpot;
        if (fallback != null) {
            enemy.setX(fallback.x);
            enemy.setY(fallback.y);
        }
    }

    private static boolean isTouchingPlayer(NPC enemy, Rectangle playerBounds) {
        Rectangle bounds = enemy.getBounds();
        return bounds.getX1() - MIN_GAP_FROM_PLAYER < playerBounds.getX2()
                && bounds.getX2() + MIN_GAP_FROM_PLAYER > playerBounds.getX1()
                && bounds.getY1() - MIN_GAP_FROM_PLAYER < playerBounds.getY2()
                && bounds.getY2() + MIN_GAP_FROM_PLAYER > playerBounds.getY1();
    }


    private static Rectangle getPlayerBounds(Map map) {
        if (map.getPlayer() != null) {
            return map.getPlayer().getBounds();
        }
        Point start = map.getMapTile(PLAYER_START_TILE_X, PLAYER_START_TILE_Y).getLocation();
        return new Rectangle(start.x, start.y, PLAYER_START_BOX_SIZE, PLAYER_START_BOX_SIZE);
    }
       
    private static boolean isTooCloseToOtherEnemies(NPC enemy, Map map, ArrayList<NPC> spawnedSoFar) {
        ArrayList<NPC> others = new ArrayList<>(spawnedSoFar);
        if (map.getNPCs() != null) {   // null while the level itself is still loading
            for (NPC npc : map.getNPCs()) {
                if (npc.getMapEntityStatus() != MapEntityStatus.REMOVED) {
                    others.add(npc);
                }
            }
        }

        Rectangle bounds = enemy.getBounds();
        for (NPC other : others) {
            if (other == enemy) {
                continue;
            }
            Rectangle otherBounds = other.getBounds();
            boolean overlaps = bounds.getX1() - MIN_GAP_BETWEEN_ENEMIES < otherBounds.getX2()
                    && bounds.getX2() + MIN_GAP_BETWEEN_ENEMIES > otherBounds.getX1()
                    && bounds.getY1() - MIN_GAP_BETWEEN_ENEMIES < otherBounds.getY2()
                    && bounds.getY2() + MIN_GAP_BETWEEN_ENEMIES > otherBounds.getY1();
            if (overlaps) {
                return true;
            }
        }
        return false;
    }
    private static boolean isOnOpenFloor(NPC enemy, Map map) {
        Rectangle bounds = enemy.getBounds();
        Point topLeft = map.getTileIndexByPosition(bounds.getX1(), bounds.getY1());
        Point bottomRight = map.getTileIndexByPosition(bounds.getX2(), bounds.getY2());

        for (int y = (int) topLeft.y; y <= (int) bottomRight.y; y++) {
            for (int x = (int) topLeft.x; x <= (int) bottomRight.x; x++) {
                MapTile tile = map.getTileByPosition(
                        x * map.getTileset().getScaledSpriteWidth(),
                        y * map.getTileset().getScaledSpriteHeight());
                if (tile == null || tile.getTileType() != TileType.PASSABLE) {
                    return false;
                }
            }
        }
        return true;
    }

    private static double getDistanceToPlayer(NPC enemy, Rectangle playerBounds) {
        Rectangle bounds = enemy.getBounds();
        float distanceX = (bounds.getX1() + bounds.getX2()) / 2f - (playerBounds.getX1() + playerBounds.getX2()) / 2f;
        float distanceY = (bounds.getY1() + bounds.getY2()) / 2f - (playerBounds.getY1() + playerBounds.getY2()) / 2f;
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }
}
