import java.awt.*;

public class Mouse {
    private static double x = 0, y = 0;
    private static double DX = MouseInfo.getPointerInfo().getLocation().getX(), DY = MouseInfo.getPointerInfo().getLocation().getY();
    private static double prevX = 0, prevY = 0;

    public static void poll() {
        DX = x - MouseInfo.getPointerInfo().getLocation().x;
        x = MouseInfo.getPointerInfo().getLocation().x;
        prevX = MouseInfo.getPointerInfo().getLocation().x;
        DY = y - MouseInfo.getPointerInfo().getLocation().y;
        y = MouseInfo.getPointerInfo().getLocation().y;
        prevY = MouseInfo.getPointerInfo().getLocation().y;
    }

    public static double getX() {
        x = MouseInfo.getPointerInfo().getLocation().x;
        return x;
    }

    public static double getY() {
        y = MouseInfo.getPointerInfo().getLocation().y;
        return y;
    }

    public static double getDX() {
        DX = 0;
        DX = prevX - Mouse.getX();
        prevX = Mouse.getX();
        /*DX = x - MouseInfo.getPointerInfo().getLocation().getX();
        x = MouseInfo.getPointerInfo().getLocation().x;*/
        return DX;
    }

    public static double getDY() {
        DY = 0;
        DY = prevY - Mouse.getY();
        prevY = Mouse.getY();
        /*DY = y - MouseInfo.getPointerInfo().getLocation().y;
        y = MouseInfo.getPointerInfo().getLocation().y;*/
        return DY;
    }

    public static void setCursorPosition(double new_x, double new_y) {
        Robot r = null;
        try {
            r = new Robot(MouseInfo.getPointerInfo().getDevice());
        } catch (AWTException e) {
            e.printStackTrace();
        }

        r.mouseMove((int) new_x, (int) new_y);
        poll();
        x = new_x;
        y = new_y;
    }
}
