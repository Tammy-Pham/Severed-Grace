package Maps;

import Level.*;
import NPCs.Boss;
import NPCs.Enemy;
import NPCs.RangedEnemy;
import Screens.PlayLevelScreen;
import Scripts.EnemySpawner;
import Tilesets.FallenTileset;
import Utils.Point;

import java.util.ArrayList;

public abstract class LevelMap extends Map {
    private Boss boss;
    private final ArrayList<Point> exitDoorLocations = new ArrayList<>();
    private final boolean combatLevel;
    private boolean bossDefeated = false;
    private boolean levelComplete = false;
    private int waveNumber = 1;

    public LevelMap(String mapFile) {
        this(mapFile, true);
    }

    protected LevelMap(String mapFile, boolean combatLevel) {
        super(mapFile, new FallenTileset());
        this.combatLevel = combatLevel;
        findExitDoors();
    }

    @Override
    public void update(Player player) {
        super.update(player);
        if (!combatLevel || levelComplete) return;
        
        //System.out.println("Level: " + PlayLevelScreen.getLevel());
        
        if (waveNumber == 1 && allEnemiesDefeated()) {
            waveNumber++;
            int meleeCount = 0;
            switch (PlayLevelScreen.getLevel()) {
                case 1:
                    addEnemyWave(1, 1);
                    break;
                case 2:
                    meleeCount = (int)(Math.random() * 2 + 2);
                    addEnemyWave(meleeCount, 5 - meleeCount);
                    break;
                case 3:
                    addEnemyWave(3, 3);
                    break;
                case 4:
                    meleeCount = (int)(Math.random() * 3 + 3);
                    addEnemyWave(meleeCount, 7 - meleeCount);
                    break;
                case 5:
                    meleeCount = (int)(Math.random() * 7 + 1);
                    addEnemyWave(meleeCount, 8 - meleeCount);
                    break;
                case 6:
                    addEnemyWave(0, 9);
                    break;
                case 7:
                    addEnemyWave(10, 5);
                    break;
                default:
                    addEnemyWave(1, 1);
                    break;
            }
        }
        

        if (!bossDefeated) {
            if (boss == null && allEnemiesDefeated() && waveNumber == 2) {
                boss = new Boss(5, getMapTile(0, 0).getLocation());
                int x = getEndBoundX()/2;
                int y = getEndBoundY()/2; 
                boss.setX(x - boss.getWidth() / 2);
                boss.setY(y - boss.getHeight() / 2);
                addNPC(boss);
                player.setX(boss.getX() + (boss.getWidth() - player.getWidth()) / 2);
                player.setY(boss.getY() + boss.getHeight() + 16);
            } else if (boss != null && (boss.getHealth() <= 0 || !getNPCs().contains(boss))) {
                bossDefeated = true;
                openExitDoors();
            }
        }

        if (bossDefeated && playerAtExit(player)) {
            levelComplete = true;
            for (GameListener listener : listeners) {
                listener.onWin();
            }
        }
    }

    private void findExitDoors() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                MapTile tile = getMapTile(x, y);
                if (tile != null && tile.getTileIndex() == FallenTileset.CLOSED_EXIT_DOOR_TILE_INDEX) {
                    exitDoorLocations.add(new Point(x, y));
                }
            }
        }
    }

    private void addEnemyWave(int meleeCount, int rangedCount) {
        for (NPC enemy : EnemySpawner.spawnEnemies(meleeCount, rangedCount)) {
            addNPC(enemy);
        }
    }

    private void openExitDoors() {
        for (Point location : exitDoorLocations) {
            MapTile closedDoor = getMapTile((int) location.x, (int) location.y);
            MapTile openDoor = tileset.getTile(FallenTileset.OPEN_EXIT_DOOR_TILE_INDEX)
                    .build(closedDoor.getX(), closedDoor.getY());
            setMapTile((int) location.x, (int) location.y, openDoor);
        }
    }

    private boolean playerAtExit(Player player) {
        for (Point location : exitDoorLocations) {
            MapTile exitDoor = getMapTile((int) location.x, (int) location.y);
            if (exitDoor != null && exitDoor.intersects(player.getBounds())) {
                return true;
            }
        }
        return false;
    }

    private boolean allEnemiesDefeated() {
        for (NPC npc : getNPCs()) {
            if (npc instanceof Enemy || npc instanceof RangedEnemy) {
                return false;
            }
        }
        return true;
    }
}