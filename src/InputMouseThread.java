//import java.awt.*;

public class InputMouseThread extends Thread {
    public static float xAxisRotate;
    public static float yAxisRotate;
    public static float xPoint = 0, yPoint = 0, zPoint = 0;
    public static boolean isMouse;
    private int dx = 0, dy = 0;

    @Override
    public void run() {
        while (Main.isCloseRequested) {
        }
        while (!Main.isCloseRequested) {
            isMouse = false;
            System.out.println(Mouse.getX() + " " + Mouse.getY() + " " + Orientation.getOrientationS() + " " + Mouse.getDX() + Mouse.getDY());
            if ((GameStats.getScreenHeight() - 4) < Mouse.getY()) {
                Mouse.setCursorPosition(Mouse.getX(), 5);
            } else if (4 > Mouse.getY()) {
                Mouse.setCursorPosition(Mouse.getX(), GameStats.getScreenHeight());
            }
            if ((GameStats.getScreenWidth() - 4) < Mouse.getX()) {
                Mouse.setCursorPosition(5, Mouse.getY());
            } else if (4 > Mouse.getX()) {
                Mouse.setCursorPosition(GameStats.getScreenWidth(), Mouse.getY());
            }
            dx += /*0.1 * */Mouse.getDX();
            dy += /*0.1 * */Mouse.getDY();
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
                    zPoint = 0 - (float) (Math.cos(Math.toRadians(yAxisRotate)) * Math.sin(Math.toRadians(xAxisRotate)));
                    break;
                case 2:
                    zPoint = 0 - (float) (Math.cos(Math.toRadians(yAxisRotate)) * Math.cos(Math.toRadians(xAxisRotate)));
                    xPoint = 0 - (float) (Math.cos(Math.toRadians(yAxisRotate)) * Math.sin(Math.toRadians(xAxisRotate)));
                    break;
                case 3:
                    xPoint = 0 - (float) (Math.cos(Math.toRadians(yAxisRotate)) * Math.cos(Math.toRadians(xAxisRotate)));
                    zPoint = (float) (Math.cos(Math.toRadians(yAxisRotate)) * Math.sin(Math.toRadians(xAxisRotate)));
                    break;
            }
            yPoint = (float) (Math.sin(Math.toRadians(yAxisRotate)));
            isMouse = true;
            //System.out.println(yAxisRotate);
            //System.out.println(dx + " " + Orientation.getOrientationS());
            /*
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }
}
