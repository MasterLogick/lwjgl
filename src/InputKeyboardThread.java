import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

public class InputKeyboardThread extends Thread {
    static int[] pos = new int[]{0, 0, 0};

    public InputKeyboardThread() {
        addKeyEvent(Keyboard.KEY_W, () -> moveForward());
        addKeyEvent(Keyboard.KEY_S, () -> moveBack());
        addKeyEvent(Keyboard.KEY_A, () -> moveLeft());
        addKeyEvent(Keyboard.KEY_D, () -> moveRight());
        addKeyEvent(Keyboard.KEY_SPACE, () -> moveUp());
        addKeyEvent(Keyboard.KEY_LSHIFT, () -> moveDown());
        addKeyEvent(Keyboard.KEY_ESCAPE, () -> Main.exit());
    }

    static private HashMap<Integer, KeyEvent> map = new HashMap<>();

    @Override
    public void run() {
        try {
            this.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!Main.isCloseRequested) {
//            Keyboard.poll();
            for (Map.Entry entry : map.entrySet()) {
                if (Keyboard.isKeyDown((Integer) entry.getKey())) {
                    ((KeyEvent) entry.getValue()).keyEvent();
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
        System.out.println("moveF");
        switch (Orientation.getOrientation()) {
            case 0:
                pos[0] += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                pos[2] += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
            case 1:
                pos[2] += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                pos[0] -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
            case 2:
                pos[0] -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                pos[2] -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
            case 3:
                pos[2] -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                pos[0] += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
        }
    }

    public static void moveBack() {
        System.out.println("moveB");
        switch (Orientation.getOrientation()) {
            case 0:
                pos[0] -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                pos[2] -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
            case 1:
                pos[2] -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                pos[0] += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
            case 2:
                pos[0] += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                pos[2] += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
            case 3:
                pos[2] += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                pos[0] -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
        }
    }

    public static void moveLeft() {
        System.out.println("moveL");
        switch (Orientation.getOrientation()) {
            case 0:
                pos[2] += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                pos[0] -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
            case 1:
                pos[0] += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                pos[2] += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
            case 2:
                pos[2] -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                pos[0] += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
            case 3:
                pos[0] -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                pos[2] -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
        }
    }

    public static void moveRight() {
        System.out.println("moveR");
        switch (Orientation.getOrientation()) {
            case 0:
                pos[2] -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                pos[0] += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
            case 1:
                pos[0] -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                pos[2] -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
            case 2:
                pos[2] += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                pos[0] -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
            case 3:
                pos[0] += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                pos[2] += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
        }
    }

    public static void moveUp() {
        System.out.println("moveU");
        pos[1] += 1;
    }

    public static void moveDown() {
        System.out.println("moveD");
        pos[1] -= 1;
    }

    public static void gluLookAt() {

    }
}
