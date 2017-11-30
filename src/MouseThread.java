import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class MouseThread extends Thread {
    public static int xAxisRotate;
    public static int yAxisRotate;
    private static int dx, dy;
    public Orientation orientation = Orientation.NORTH;

    @Override
    public void run() {
        while (Main.isCloseRequested) {
        }
        while (!Main.isCloseRequested) {
            dx = Mouse.getDX();
            dy = Mouse.getDY();
            if(Math.abs(dx)>45){
                orientation = Orientation.values()[(int)(orientation.ordinal()+Math.signum(dx)) % 4];
            }
            orientation = Orientation.values()[orientation.ordinal() % 4];
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
