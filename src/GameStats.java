import java.awt.*;

public class GameStats {
    private static int WindowWidth = 1000;
    private static int WindowHeight = 900;
    private static int WindowCenterX = WindowWidth / 2;
    private static int WindowCenterY = WindowHeight / 2;
    private static final int ScreenWidth= MouseInfo.getPointerInfo().getDevice().getDisplayMode().getWidth();
    private static final int ScreenHeight= MouseInfo.getPointerInfo().getDevice().getDisplayMode().getHeight();

    public static int getScreenWidth() {
        return ScreenWidth;
    }

    public static int getScreenHeight() {
        return ScreenHeight;
    }

    public static void setWindowWidth(int windowWidth) {
        WindowWidth = windowWidth;
        WindowCenterX = WindowWidth / 2;
    }

    public static void setWindowHeight(int windowHeight) {
        WindowHeight = windowHeight;
        WindowCenterY = WindowHeight / 2;
    }

    public static void setWindowCenterX(int windowCenterX) {
        WindowCenterX = windowCenterX;
    }

    public static void setWindowCenterY(int windowCenterY) {
        WindowCenterY = windowCenterY;
    }

    public static int getWindowWidth() {
        return WindowWidth;
    }

    public static int getWindowHeight() {
        return WindowHeight;
    }

    public static int getWindowCenterX() {
        return WindowCenterX;
    }

    public static int getWindowCenterY() {
        return WindowCenterY;
    }
}
