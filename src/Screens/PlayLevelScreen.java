package Screens;

import Engine.GraphicsHandler;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.*;
import Maps.*;
import Players.FallenAngel;
import Utils.Direction;

// This class is for when the RPG game is actually being played
public class PlayLevelScreen extends Screen implements GameListener {
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected PlayLevelScreenState playLevelScreenState;
    protected WinScreen winScreen;
    protected TransitionScreen transitionScreen;
    protected FlagManager flagManager;
    protected HealthBar healthBar;
    protected int currentLevel = 0;

    private static final int TOTAL_LEVELS = 7;

    public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    public void initialize() {
        flagManager = new FlagManager();
        flagManager.addFlag("hasLostBall", false);
        flagManager.addFlag("hasTalkedToWalrus", false);
        flagManager.addFlag("hasTalkedToDinosaur", false);
        flagManager.addFlag("hasFoundBall", false);

        player = null;
        loadLevel(createLevelMap(currentLevel), false);
        healthBar = new HealthBar(10, 0, 2);
        winScreen = new WinScreen(this);
    }

    private Map createLevelMap(int level) {
        switch (level) {
            case 0: return new LevelOneMap();
            case 1: return new LevelTwoMap();
            case 2: return new LevelThreeMap();
            case 3: return new LevelFourMap();
            case 4: return new LevelFiveMap();
            case 5: return new LevelSixMap();
            case 6: return new LevelSevenMap();
            default: throw new IllegalArgumentException("Invalid level: " + level);
        }
    }

    private void loadLevel(Map newMap, boolean showTransition) {
        map = newMap;
        map.setFlagManager(flagManager);

        if (player == null) {
            player = new FallenAngel(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
            player.setFacingDirection(Direction.LEFT);
        } else {
            player.setLocation(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        }
        player.setMap(map);
        map.setPlayer(player);

        map.getTextbox().setInteractKey(player.getInteractKey());
        map.addListener(this);
        map.preloadScripts();
        if (showTransition) {
            transitionScreen = new TransitionScreen(currentLevel + 1);
            playLevelScreenState = PlayLevelScreenState.TRANSITION;
        } else {
            playLevelScreenState = PlayLevelScreenState.RUNNING;
        }
    }

    public void update() {
        switch (playLevelScreenState) {
            case RUNNING:
                if (LevelSwitch.isRequested()) {
                    loadLevel(LevelSwitch.consume(), true);
                    return;
                }
                player.update();
                map.update(player);
                player.updateFacingAndAnimation();
                break;
            case TRANSITION:
                transitionScreen.update();
                if (transitionScreen.isComplete()) {
                    playLevelScreenState = PlayLevelScreenState.RUNNING;
                }
                break;
            case LEVEL_COMPLETED:
                winScreen.update();
                break;
        }
    }

    @Override
    public void onWin() {
        if (currentLevel < TOTAL_LEVELS - 1) {
            currentLevel++;
            loadLevel(createLevelMap(currentLevel), true);
        } else {
            playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        switch (playLevelScreenState) {
            case RUNNING:
                map.draw(player, graphicsHandler);
                healthBar.draw(graphicsHandler, player.getHealth());
                break;
            case TRANSITION:
                transitionScreen.draw(graphicsHandler);
                break;
            case LEVEL_COMPLETED:
                winScreen.draw(graphicsHandler);
                break;
        }
    }

    public PlayLevelScreenState getPlayLevelScreenState() {
        return playLevelScreenState;
    }

    public void resetLevel() {
        currentLevel = 0;
        initialize();
    }

    public void goBackToMenu() {
        screenCoordinator.setGameState(GameState.MENU);
    }

    private enum PlayLevelScreenState {
        RUNNING, TRANSITION, LEVEL_COMPLETED
    }
}
