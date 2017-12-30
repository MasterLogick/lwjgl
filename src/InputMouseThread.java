import org.lwjgl.input.Mouse;

public class InputMouseThread extends Thread {
    public static int xAxisRotate;
    public static int yAxisRotate;
    private int dx = 0, dy = 0;

    @Override
    public void run() {
        while (Main.isCloseRequested) {
        }
        while (!Main.isCloseRequested) {
            if (!Mouse.isInsideWindow()) {
                Mouse.setCursorPosition(GameStats.getWindowCenterX(), GameStats.getWindowCenterY());
            }
            dx += Mouse.getDX();
            dy += Mouse.getDY();
            while (Math.abs(dx) > 45) {
                Orientation.setOrientation((int) (Orientation.getOrientation() + Math.signum(dx)));
                dx = (int) (dx - Math.signum(dx) * 90);
            }
            if (Math.abs(dy) > 180) {
                dy = (int) (Math.signum(dy) * 180);
            }
            xAxisRotate = dx;
            yAxisRotate = dy;
            //System.out.println(dx + " " + Orientation.getOrientationS());

            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
