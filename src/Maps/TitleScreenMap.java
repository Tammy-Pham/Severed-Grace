package Maps;

import Engine.GraphicsHandler;
import Level.Map;
import Tilesets.FallenTileset;

// Represents the map that is used as a background for the main menu and credits menu screen
public class TitleScreenMap extends Map {


    public TitleScreenMap() {
        super("title_screen_map.txt", new FallenTileset());
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
