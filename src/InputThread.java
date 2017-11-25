import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.HashMap;

public class InputThread extends Thread {
    public InputThread() {
        addKeyEvent(Keyboard.KEY_W, () -> InputThread.moveForward());
        addKeyEvent(Keyboard.KEY_S, () -> InputThread.moveBack());
        addKeyEvent(Keyboard.KEY_A, () -> InputThread.moveLeft());
        addKeyEvent(Keyboard.KEY_D, () -> InputThread.moveRight());
        addKeyEvent(Keyboard.KEY_SPACE, () ->InputThread.moveUp());
        addKeyEvent(Keyboard.KEY_LSHIFT,()->InputThread.moveDown());
    }


    static private HashMap<Integer, KeyEvent> map = new HashMap<>();

    @Override
    public void run() {
        try {
            this.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!Display.isCloseRequested() && !Main.isCloseRequested) {
            Keyboard.poll();
            if (Keyboard.next() && Keyboard.getEventKeyState()) {
                KeyEvent e = map.get(Keyboard.getEventKey());
                if (e != null) {
                    e.keyEvent();
                }
            }
        }
    }

    public static void addKeyEvent(int key, KeyEvent e) {
        map.put(key, e);
    }

   /* public void move() {

    }*/

    public static void moveForward() {

    }

    public static void moveBack() {

    }
    public static void moveLeft(){

    }
    public static void moveRight(){

    }
    public static void moveUp(){

    }
    public static void moveDown(){

    }
}
