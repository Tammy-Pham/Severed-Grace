package Game;

import Engine.DefaultScreen;
import Engine.GraphicsHandler;
import Engine.Screen;
import Screens.CreditsScreen;
import Screens.Level1Scene;
import Screens.Level2Scene;
import Screens.Level3Scene;
import Screens.Level4Scene;
import Screens.Level5Scene;
import Screens.Level6Scene;
import Screens.Level7Scene;
import Screens.MenuScreen;
import Screens.PlayLevelScreen;

/*
 * Based on the current game state, this class determines which Screen should be shown
 * There can only be one "currentScreen", although screens can have "nested" screens
 */
public class ScreenCoordinator extends Screen {
	// currently shown Screen
	protected Screen currentScreen = new DefaultScreen();

	// keep track of gameState so ScreenCoordinator knows which Screen to show
	protected GameState gameState;
	protected GameState previousGameState;

	public GameState getGameState() {
		return gameState;
	}

	// Other Screens can set the gameState of this class to force it to change the currentScreen
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public void initialize() {
		// start game off with Menu Screen
		gameState = GameState.MENU;
	}

	@Override
	public void update() {
		do {
			// if previousGameState does not equal gameState, it means there was a change in gameState
			// this triggers ScreenCoordinator to bring up a new Screen based on what the gameState is
			if (previousGameState != gameState) {
				switch(gameState) {
					case MENU:
						currentScreen = new MenuScreen(this);
						break;
					case LEVEL:
						currentScreen = new PlayLevelScreen(this);
						break;
					case CREDITS:
						currentScreen = new CreditsScreen(this);
						break;
					case LEVEL1SCENE:
						currentScreen = new Level1Scene(this);
						break;
					case LEVEL2SCENE:
						currentScreen = new Level2Scene(this);
						break;
					case LEVEL3SCENE:
						currentScreen = new Level3Scene(this);
						break;
					case LEVEL4SCENE:
						currentScreen = new Level4Scene(this);
						break;
					case LEVEL5SCENE:
						currentScreen = new Level5Scene(this);
						break;
					case LEVEL6SCENE:
						currentScreen = new Level6Scene(this);
						break;
					case LEVEL7SCENE:
						currentScreen = new Level7Scene(this);
						break;
				}
				currentScreen.initialize();
			}
			previousGameState = gameState;

			// call the update method for the currentScreen
			currentScreen.update();
		} while (previousGameState != gameState);
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		// call the draw method for the currentScreen
		currentScreen.draw(graphicsHandler);
	}
}
