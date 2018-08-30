import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

public class InputKeyboardThread extends Thread {
    static float xPos = 0, yPos = 0, zPos = 0;
    public static boolean isKeyboardDataRead;


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
        addKeyEvent(Keyboard.KEY_F, () -> Player.isFlyEnable = !Player.isFlyEnable);
        addKeyEvent(Keyboard.KEY_1, () -> is = !is);
    }

    static boolean is = true;
    static private HashMap<Integer, KeyHandler> map = new HashMap<>();

    @Override
    public void run() {
        try {
            this.sleep(100);
        } catch (InterruptedException e) {
            Main.ERROR(e);
        }
        while (!Main.isCloseRequested) {
            isKeyboardDataRead = false;
            if (is)
                for (Map.Entry<Integer, KeyHandler> entry : map.entrySet()) {
                    if (Keyboard.isKeyDown(entry.getKey())) {
                        (entry.getValue()).keyHandler();
                    }
                }
            else {
                if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
                    is = !is;
                }
            }
            isKeyboardDataRead = true;
            try {
                this.sleep(50);
            } catch (InterruptedException e) {
                Main.ERROR(e);
            }
        }
    }

    public static void addKeyEvent(int key, KeyHandler e) {
        map.put(key, e);
    }

    public static void moveForward() {
        switch (Orientation.getOrientation()) {
            case 0:
                xPos += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                zPos += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
            case 1:
                zPos -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                xPos += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
            case 2:
                xPos -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                zPos -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
            case 3:
                zPos += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                xPos -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
        }
        if (Player.isFlyEnable) {
            yPos += Math.sin(Math.toRadians(InputMouseThread.yAxisRotate));
        }
    }

    public static void moveBack() {
        switch (Orientation.getOrientation()) {
            case 0:
                xPos -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                zPos -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
            case 1:
                zPos += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                xPos -= Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
            case 2:
                xPos += Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                zPos += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
            case 3:
                zPos -= Math.sin(Math.toRadians(InputMouseThread.xAxisRotate));
                xPos += Math.cos(Math.toRadians(InputMouseThread.xAxisRotate));
                break;
        }
        if (Player.isFlyEnable) {
            yPos -= Math.sin(Math.toRadians(InputMouseThread.yAxisRotate));
        }
    }

    public static void moveLeft() {
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
    }

    public static void moveRight() {
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
    }

    public static void moveUp() {
        yPos += 1;
    }

    public static void moveDown() {
        yPos -= 1;
    }
}
