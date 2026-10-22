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
    protected static int currentLevel = 0;
    protected EndingScreen endingScreen;

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
        loadCutsceneMap(currentLevel);
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

    private Map createSceneMap(int level) {
        switch (level) {
            case 0: return new LevelOneSceneMap();
            case 1: return new LevelTwoSceneMap();
            case 2: return new LevelThreeSceneMap();
            case 3: return new LevelFourSceneMap();
            case 4: return new LevelFiveSceneMap();
            case 5: return new LevelSixSceneMap();
            case 6: return new LevelSevenSceneMap();
            default: throw new IllegalArgumentException("Invalid level: " + level);
        }
    }

    private void loadCutsceneMap(int level) {
        loadLevel(createSceneMap(level), false);
        if (map.getTriggers().isEmpty() || map.getTriggers().get(0).getTriggerScript() == null) {
            throw new IllegalStateException("Cutscene map for level " + (level + 1) + " has no cutscene trigger");
        }
        map.setActiveScript(map.getTriggers().get(0).getTriggerScript());
        playLevelScreenState = PlayLevelScreenState.CUTSCENE;
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
            case CUTSCENE:
                player.update();
                map.update(player);
                player.updateFacingAndAnimation();
                if (map.getActiveScript() == null) {
                    loadLevel(createLevelMap(currentLevel), true);
                }
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
            case ENDING:
                endingScreen.update();
                break;
        }
    }

    @Override
    public void onWin() {
        if (currentLevel < TOTAL_LEVELS - 1) {
            currentLevel++;
            loadCutsceneMap(currentLevel);
        } else {
            endingScreen = new EndingScreen(player, this);
            endingScreen.initialize();
            playLevelScreenState = PlayLevelScreenState.ENDING;
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        switch (playLevelScreenState) {
            case RUNNING:
                map.draw(player, graphicsHandler);
                healthBar.draw(graphicsHandler, player.getHealth());
                break;
            case CUTSCENE:
                map.draw(player, graphicsHandler);
                break;
            case TRANSITION:
                transitionScreen.draw(graphicsHandler);
                break;
            case LEVEL_COMPLETED:
                winScreen.draw(graphicsHandler);
                break;
            case ENDING:
                endingScreen.draw(graphicsHandler);
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
        RUNNING, CUTSCENE, TRANSITION, LEVEL_COMPLETED, ENDING
    }

    public static int getLevel(){
        return currentLevel + 1;
    }
}
