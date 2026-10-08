package Engine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

public class Mouse {
    private static HashMap<Integer, Boolean> buttonDown = new HashMap<Integer, Boolean>();

    private static HashMap<Integer, Boolean> buttonUp = new HashMap<Integer, Boolean>();

    private static int mouseX = 0;
    private static int mouseY = 0;

    private static MouseListener mouselistener = new MouseListener() {
       
        public void mouseClicked(MouseEvent e) {
        }

        
        public void mousePressed(MouseEvent e) {
            int button = e.getButton();
            buttonDown.put(e.getButton(), true);
            buttonUp.put(e.getButton(), false);
        }

        
        public void mouseReleased(MouseEvent e) {
            int button = e.getButton();
            buttonUp.put(button, true);
            buttonDown.put(button, false);
        }

        
        public void mouseEntered(MouseEvent e) {
        }

        
        public void mouseExited(MouseEvent e) {
        }
    }; 

    private static MouseMotionListener mouseMotionlistener = new MouseMotionListener() {
        public void mouseDragged(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }

        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
    };
    private Mouse() {
    }
    public static MouseListener getMouseListener() {
        return mouselistener;
    }
    public static MouseMotionListener getMouseMotionListener() {
        return mouseMotionlistener;
    }   
    public static boolean isButtonDown(int button) {
            if (buttonDown.containsKey(button)) {
                return buttonDown.get(button);
            }
        return false;
    }

    public static boolean isButtonUp(int button) {
        if (buttonUp.containsKey(button)) {
            return buttonUp.get(button);
        }
        return false;   

    }
    public static int getMouseX() {
        return mouseX;
    }

    public static int getMouseY() {
        return mouseY;
    }
}
