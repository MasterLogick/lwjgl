import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.HashMap;
import java.util.Map;

public class InputThread extends Thread {
    public InputThread() {
        addKeyEvent(Keyboard.KEY_W, () -> InputThread.moveForward());
        addKeyEvent(Keyboard.KEY_S, () -> InputThread.moveBack());
        addKeyEvent(Keyboard.KEY_A, () -> InputThread.moveLeft());
        addKeyEvent(Keyboard.KEY_D, () -> InputThread.moveRight());
        addKeyEvent(Keyboard.KEY_SPACE, () -> InputThread.moveUp());
        addKeyEvent(Keyboard.KEY_LSHIFT, () -> InputThread.moveDown());
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
            for (Map.Entry entry : map.entrySet()) {

            }
        }
    }

    public static void addKeyEvent(int key, KeyEvent e) {
        map.put(key, e);
    }

   /* public void move() {

    }*/

    public static void moveForward() {
        System.out.println("moveF");
        //todo
    }

    public static void moveBack() {
        System.out.println("moveB");
        //todo
    }

    public static void moveLeft() {
        System.out.println("moveL");
        //todo
    }

    public static void moveRight() {
        System.out.println("moveR");
        //todo
    }

    public static void moveUp() {
        System.out.println("moveU");
        //todo
    }

    public static void moveDown() {
        System.out.println("moveD");
        //todo
    }
}
