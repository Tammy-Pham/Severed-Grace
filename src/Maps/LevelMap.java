package Maps;

import Engine.ScreenManager;
import Level.*;
import NPCs.Boss;
import NPCs.Enemy;
import NPCs.RangedEnemy;
import Tilesets.FallenTileset;
import Utils.Point;

import java.util.ArrayList;

public abstract class LevelMap extends Map {
    private Boss boss;
    private final ArrayList<Point> exitDoorLocations = new ArrayList<>();
    private boolean bossDefeated = false;
    private boolean levelComplete = false;

    public LevelMap(String mapFile) {
        super(mapFile, new FallenTileset());
        findExitDoors();
    }

    @Override
    public void update(Player player) {
        super.update(player);
        if (levelComplete) return;

        if (!bossDefeated) {
            if (boss == null && allEnemiesDefeated()) {
                boss = new Boss(5, getMapTile(0, 0).getLocation());
                int cx = Math.round(getCamera().getX()) + ScreenManager.getScreenWidth() / 2;
                int cy = Math.round(getCamera().getY()) + ScreenManager.getScreenHeight() / 2;
                boss.setX(cx - boss.getWidth() / 2);
                boss.setY(cy - boss.getHeight() / 2);
                addNPC(boss);
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