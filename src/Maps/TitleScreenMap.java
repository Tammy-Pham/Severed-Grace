package Maps;

import Engine.GraphicsHandler;
//import Engine.ImageLoader;
//import GameObject.ImageEffect;
//import GameObject.Sprite;
import Level.Map;
//<<<<<<< HEAD
//import Tilesets.CommonTileset;
//import Utils.Colors;
//import Utils.Point;
//=======
import Tilesets.FallenTileset;
import Utils.Colors;
import Utils.Point;
//>>>>>>> 50875cad49815e870d8065cccd7030e0344da08e

// Represents the map that is used as a background for the main menu and credits menu screen
public class TitleScreenMap extends Map {

    //private Sprite cat;

    public TitleScreenMap() {
//<<<<<<< HEAD
        //super("title_screen_map.txt", new CommonTileset());
        super("title_screen_map.txt", new FallenTileset());
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        //cat.draw(graphicsHandler);
    }
}
