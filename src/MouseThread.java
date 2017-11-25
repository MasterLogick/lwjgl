import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class MouseThread extends Thread {
    static int vector = 0;
    static float /*rotateX = 0,*/ verticalAngle = 0, horizontalAngle = 0/*, prevAngleX = 0, prevAngleY = 0*/, centerX = 0, centerY = 0, centerZ = 0;
    static boolean vertical = false;
    static double calculatingAngleY = Math.atan(10);
    //private HashMap<Integer, Integer> m = new HashMap<>();

    @Override
    public void run() {
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int dx, dy, x = Mouse.getY(), y = Mouse.getX();
        while (!Display.isCloseRequested() && !Main.isCloseRequested) {
//            m.put(Mouse.getDX(), Mouse.getDY());
            //System.out.println(Mouse.getX() + " " + Mouse.getY());
            Mouse.poll();
            dx = Mouse.getY() - x;
            dy = Mouse.getX() - y;
            x = Mouse.getY();
            y = Mouse.getX();
//            rotateX += dx*0.5;
            verticalAngle += dx * 0.5;
            horizontalAngle += dy * 0.5;

            while (horizontalAngle > 45) {
                vector =(vector+1)%4;
                horizontalAngle -= 90;
            }
            while (horizontalAngle < -45) {
                vector =(vector-1)%4;
                horizontalAngle += 90;
            }
            if (verticalAngle > 180) {
                verticalAngle -= 360;
            }
            if (verticalAngle < -180) {
                verticalAngle += 360;
            }

        }
    }

    public void calc() {
        centerY = (float)Math.sin(Math.toRadians(verticalAngle));
        switch (vector) {
            case 0:
                centerZ = (float) Math.cos(Math.toRadians(horizontalAngle));
                centerX = (float) Math.sin(Math.toRadians(horizontalAngle));
                break;
            case 1:
                centerZ = (float) -Math.sin(Math.toRadians(horizontalAngle));
                centerX = (float) Math.cos(Math.toRadians(horizontalAngle));
                break;
            case 2:
                centerZ = (float) -Math.cos(Math.toRadians(horizontalAngle));
                centerX = (float) -Math.sin(Math.toRadians(horizontalAngle));
                break;
            case 3:
                centerZ = (float) Math.sin(Math.toRadians(horizontalAngle));
                centerX = (float) -Math.cos(Math.toRadians(horizontalAngle));
                break;
        }
    }
}
