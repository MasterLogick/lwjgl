public class GameStats {
    private static int WindowWidth = 800;
    private static int WindowHeight = 600;
    private static int WindowCenterX = WindowWidth / 2;
    private static int WindowCenterY = WindowHeight / 2;

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
/*

    public static void setWindowHeight(int windowHeight) {
        WindowHeight = windowHeight;
        WindowCenterY=WindowHeight/2;
    }

    public static void setWindowWidth(int windowWidth) {
        WindowWidth = windowWidth;
        WindowCenterX=WindowWidth/2;
    }
*/
}
