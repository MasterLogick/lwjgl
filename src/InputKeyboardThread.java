import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class InputKeyboardThread extends Thread {
    static float xPos = 0, yPos = 0, zPos = 0;
    public static boolean isKeyboard;


    public InputKeyboardThread() {
        addKeyEvent(Keyboard.KEY_W, () -> moveForward());
        addKeyEvent(Keyboard.KEY_S, () -> moveBack());
        addKeyEvent(Keyboard.KEY_A, () -> moveLeft());
        addKeyEvent(Keyboard.KEY_D, () -> moveRight());
        addKeyEvent(Keyboard.KEY_SPACE, () -> moveUp());
        addKeyEvent(Keyboard.KEY_LSHIFT, () -> moveDown());
        addKeyEvent(Keyboard.KEY_ESCAPE, () -> Main.exit());
        addKeyEvent(Keyboard.KEY_R, () -> {
            xPos = 0;
            yPos = 0;
            zPos = 0;
        });
    }

    static private HashMap<Integer, KeyEvent> map = new HashMap<>();

    @Override
    public void run() {
        try {
            this.sleep(100);
        } catch (InterruptedException e) {
            Main.ERROR(e);
        }
        while (!Main.isCloseRequested) {
            isKeyboard=false;
//            Keyboard.poll();
            for (Map.Entry entry : map.entrySet()) {
                if (Keyboard.isKeyDown((Integer) entry.getKey())) {
                    ((KeyEvent) entry.getValue()).keyEvent();
                }
            }
            isKeyboard=true;
            try {
                this.sleep(50);
            } catch (InterruptedException e) {
                Main.ERROR(e);
            }
        }
    }

    public static void addKeyEvent(int key, KeyEvent e) {
        map.put(key, e);
    }

    /* public void move() {
 
     }*/
    static boolean isF = true;

    public static void moveForward() {
        if (isF) {
            System.out.println("moveF");
            switch (Orientation.getOrientation()) {
                case 0:
                    xPos -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                    zPos -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                    break;
                case 1:
                    zPos -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                    xPos += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                    break;
                case 2:
                    xPos += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                    zPos += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                    break;
                case 3:
                    zPos += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                    xPos -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                    break;

            }
            isF = false;
            Timer t = new Timer();

            class foo extends TimerTask {

                @Override
                public void run() {
                    isF = true;
                }
            }
            t.schedule(new foo(), 7);
        }
    }

    static boolean isB = true;

    public static void moveBack() {
        if (isB) {
            System.out.println("moveB");
            switch (Orientation.getOrientation()) {
                case 0:
                    xPos += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                    zPos += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                    break;
                case 1:
                    zPos += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                    xPos -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                    break;
                case 2:
                    xPos -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                    zPos -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                    break;
                case 3:
                    zPos -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                    xPos += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                    break;
            }
            isB = false;
            Timer t = new Timer();

            class foo extends TimerTask {

                @Override
                public void run() {
                    isB = true;
                }
            }
            t.schedule(new foo(), 7);
        }
    }

    static boolean isL = true;

    public static void moveLeft() {
        if (isL) {
            System.out.println("moveL");
            switch (Orientation.getOrientation()) {
                case 0:
                    zPos += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                    xPos -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                    break;
                case 1:
                    xPos += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                    zPos += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                    break;
                case 2:
                    zPos -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                    xPos += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                    break;
                case 3:
                    xPos -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                    zPos -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                    break;
            }
            isL = false;
            Timer t = new Timer();

            class foo extends TimerTask {

                @Override
                public void run() {
                    isL = true;
                }
            }
            t.schedule(new foo(), 7);
        }
    }

    static boolean isR = true;

    public static void moveRight() {
        if (isR) {
            System.out.println("moveR");
            switch (Orientation.getOrientation()) {
                case 0:
                    zPos -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                    xPos += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                    break;
                case 1:
                    xPos -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                    zPos -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                    break;
                case 2:
                    zPos += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                    xPos -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                    break;
                case 3:
                    xPos += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                    zPos += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                    break;
            }
            isR = false;
            Timer t = new Timer();

            class foo extends TimerTask {

                @Override
                public void run() {
                    isR = true;
                }
            }
            t.schedule(new foo(), 7);
        }
    }

    static boolean isU = true;

    public static void moveUp() {
        if (isU) {
            System.out.println("moveU");
            yPos += 1;
            isU = false;
            Timer t = new Timer();

            class foo extends TimerTask {

                @Override
                public void run() {
                    isU = true;
                }
            }
            t.schedule(new foo(), 7);
        }
    }

    static boolean isD = true;

    public static void moveDown() {
        if (isD) {
            System.out.println("moveD");
            yPos -= 1;
            isD = false;
            Timer t = new Timer();

            class foo extends TimerTask {

                @Override
                public void run() {
                    isD = true;
                }
            }
            t.schedule(new foo(), 7);
        }
    }
}
