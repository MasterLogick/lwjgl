package input;//import java.awt.*;

import display.DisplayInfo;
import engene.Main;
import engene.Player;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

public class InputReader {
    private static float xAxisRotate;
    private static float yAxisRotate;
    private static float xPoint = 0, yPoint = 0, zPoint = 0;
    private static float dx = 0, dy = 0;
    private static float xPos = 2, yPos = 2, zPos = 0;
    private static boolean is = true;
    private static HashMap<Integer, KeyHandler> map = new HashMap<>();

    public static float getXPoint() {
        return xPoint;
    }

    public static float getYPoint() {
        return yPoint;
    }

    public static float getZPoint() {
        return zPoint;
    }

    public static float getXPos() {
        return xPos;
    }

    public static float getYPos() {
        return yPos;
    }

    public static float getZPos() {
        return zPos;
    }

    public static void defaultInit() {
        addKeyEvent(Keyboard.KEY_W, InputReader::moveForward);
        addKeyEvent(Keyboard.KEY_S, InputReader::moveBack);
        addKeyEvent(Keyboard.KEY_A, InputReader::moveLeft);
        addKeyEvent(Keyboard.KEY_D, InputReader::moveRight);
        addKeyEvent(Keyboard.KEY_SPACE, InputReader::moveUp);
        addKeyEvent(Keyboard.KEY_LSHIFT, InputReader::moveDown);
        addKeyEvent(Keyboard.KEY_ESCAPE, () -> Main.exit());
        addKeyEvent(Keyboard.KEY_R, () -> {
            xPos = 0;
            yPos = 0;
            zPos = 0;
        });
        addKeyEvent(Keyboard.KEY_F, () -> {
            Player.isFlyEnable = !Player.isFlyEnable;
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        addKeyEvent(Keyboard.KEY_1, () -> {
            is = !is;
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static void addKeyEvent(int key, KeyHandler e) {
        map.put(key, e);
    }

    private static void moveForward() {

        switch (Orientation.getOrientation()) {
            case 0:
                xPos += Math.sin(Math.toRadians(InputReader.xAxisRotate));
                zPos += Math.cos(Math.toRadians(InputReader.xAxisRotate));
                break;
            case 1:
                zPos -= Math.sin(Math.toRadians(InputReader.xAxisRotate));
                xPos += Math.cos(Math.toRadians(InputReader.xAxisRotate));
                break;
            case 2:
                xPos -= Math.sin(Math.toRadians(InputReader.xAxisRotate));
                zPos -= Math.cos(Math.toRadians(InputReader.xAxisRotate));
                break;
            case 3:
                zPos += Math.sin(Math.toRadians(InputReader.xAxisRotate));
                xPos -= Math.cos(Math.toRadians(InputReader.xAxisRotate));
                break;
        }
        if (Player.isFlyEnable) {
            yPos += Math.sin(Math.toRadians(InputReader.yAxisRotate));
        }
    }

    private static void moveBack() {
        switch (Orientation.getOrientation()) {
            case 0:
                xPos -= Math.sin(Math.toRadians(InputReader.xAxisRotate));
                zPos -= Math.cos(Math.toRadians(InputReader.xAxisRotate));
                break;
            case 1:
                zPos += Math.sin(Math.toRadians(InputReader.xAxisRotate));
                xPos -= Math.cos(Math.toRadians(InputReader.xAxisRotate));
                break;
            case 2:
                xPos += Math.sin(Math.toRadians(InputReader.xAxisRotate));
                zPos += Math.cos(Math.toRadians(InputReader.xAxisRotate));
                break;
            case 3:
                zPos -= Math.sin(Math.toRadians(InputReader.xAxisRotate));
                xPos += Math.cos(Math.toRadians(InputReader.xAxisRotate));
                break;
        }
        if (Player.isFlyEnable) {
            yPos -= Math.sin(Math.toRadians(InputReader.yAxisRotate));
        }
    }

    private static void moveLeft() {
        switch (Orientation.getOrientation()) {
            case 0:
                zPos -= Math.sin(Math.toRadians(InputReader.xAxisRotate));
                xPos += Math.cos(Math.toRadians(InputReader.xAxisRotate));
                break;
            case 1:
                xPos -= Math.sin(Math.toRadians(InputReader.xAxisRotate));
                zPos -= Math.cos(Math.toRadians(InputReader.xAxisRotate));
                break;
            case 2:
                zPos += Math.sin(Math.toRadians(InputReader.xAxisRotate));
                xPos -= Math.cos(Math.toRadians(InputReader.xAxisRotate));
                break;
            case 3:
                xPos += Math.sin(Math.toRadians(InputReader.xAxisRotate));
                zPos += Math.cos(Math.toRadians(InputReader.xAxisRotate));
                break;
        }
    }

    private static void moveRight() {
        switch (Orientation.getOrientation()) {
            case 0:
                zPos += Math.sin(Math.toRadians(InputReader.xAxisRotate));
                xPos -= Math.cos(Math.toRadians(InputReader.xAxisRotate));
                break;
            case 1:
                xPos += Math.sin(Math.toRadians(InputReader.xAxisRotate));
                zPos += Math.cos(Math.toRadians(InputReader.xAxisRotate));
                break;
            case 2:
                zPos -= Math.sin(Math.toRadians(InputReader.xAxisRotate));
                xPos += Math.cos(Math.toRadians(InputReader.xAxisRotate));
                break;
            case 3:
                xPos -= Math.sin(Math.toRadians(InputReader.xAxisRotate));
                zPos -= Math.cos(Math.toRadians(InputReader.xAxisRotate));
                break;
        }
    }

    private static void moveUp() {
        yPos += 1;
    }

    private static void moveDown() {
        yPos -= 1;
    }

    public static void readData() {

        /*try {
            Thread.sleep(62);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        if (is)
            for (Map.Entry<Integer, KeyHandler> entry : map.entrySet()) {
                System.out.println(getXPos() + " " + getYPos() + " " + getZPos());
                if (Keyboard.isKeyDown(entry.getKey())) {
                    (entry.getValue()).keyHandler();
                }
            }
        else {
            if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
                is = true;
            }
        }
        if ((DisplayInfo.getScreenHeight() - 4) < Mouse.getY()) {
            Mouse.setCursorPosition(Mouse.getX(), 5);
        } else if (4 > Mouse.getY()) {
            Mouse.setCursorPosition(Mouse.getX(), DisplayInfo.getScreenHeight());
        }
        if ((DisplayInfo.getScreenWidth() - 4) < Mouse.getX()) {
            Mouse.setCursorPosition(5, Mouse.getY());
        } else if (4 > Mouse.getX()) {
            Mouse.setCursorPosition(DisplayInfo.getScreenWidth(), Mouse.getY());
        }
        dx += 0.6 * Mouse.getDX();
        dy += 0.6 * Mouse.getDY();
        while (Math.abs(dx) > 45) {
            Orientation.setOrientation((int) (Orientation.getOrientation() + Math.signum(dx)));
            dx = (int) (dx - Math.signum(dx) * 90);
        }
        if (Math.abs(dy) > 90) {
            dy = (int) (Math.signum(dy) * 89);

        }
        Mouse.poll();
        xAxisRotate = dx;
        yAxisRotate = dy;

        switch (Orientation.getOrientation()) {
            case 0:
                zPoint = (float) (Math.cos(Math.toRadians(yAxisRotate)) * Math.cos(Math.toRadians(xAxisRotate)));
                xPoint = (float) (Math.cos(Math.toRadians(yAxisRotate)) * Math.sin(Math.toRadians(xAxisRotate)));
                break;
            case 1:
                xPoint = (float) (Math.cos(Math.toRadians(yAxisRotate)) * Math.cos(Math.toRadians(xAxisRotate)));
                zPoint = -(float) (Math.cos(Math.toRadians(yAxisRotate)) * Math.sin(Math.toRadians(xAxisRotate)));
                break;
            case 2:
                zPoint = -(float) (Math.cos(Math.toRadians(yAxisRotate)) * Math.cos(Math.toRadians(xAxisRotate)));
                xPoint = -(float) (Math.cos(Math.toRadians(yAxisRotate)) * Math.sin(Math.toRadians(xAxisRotate)));
                break;
            case 3:
                xPoint = -(float) (Math.cos(Math.toRadians(yAxisRotate)) * Math.cos(Math.toRadians(xAxisRotate)));
                zPoint = (float) (Math.cos(Math.toRadians(yAxisRotate)) * Math.sin(Math.toRadians(xAxisRotate)));
                break;
        }

        yPoint = (float) (Math.sin(Math.toRadians(yAxisRotate)));
    }
}
