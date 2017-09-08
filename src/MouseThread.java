import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.glRotatef;

public class MouseThread extends Thread {
    static int vector = 0;
    static float /*rotateX = 0,*/ angleX = 0, angleY = 0/*, prevAngleX = 0, prevAngleY = 0*/,centerX=0,centerY=0,centerZ=0;
    static boolean vertical = false;
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
            System.out.println(Mouse.getX() + " " + Mouse.getY());
            Mouse.poll();
            dx = Mouse.getY()-x;
            dy = Mouse.getX()-y;
            x = Mouse.getY();
            y = Mouse.getX();
//            rotateX += dx*0.5;
            angleX += dx*0.5;
            angleY += dy*0.5;
            /*if (rotateX > 180) {
                rotateX -= 360;
            }
            if (rotateX < -180) {
                rotateX += 360;
            }*/
            while (angleY > 45) {
                vector += 1;
                angleY -= 90;
                if (vector == 4) {
                    vector = 0;
                }
            }
            while (angleY < -45) {
                vector -= 1;
                angleY += 90;
                if (vector == -1) {
                    vector = 3;
                }
            }
            if (angleX > 180) {
                angleX -= 360;
            }
            if (angleX < -180) {
                angleX += 360;
            }

        }
    }
    public void rotate() {
//        centerY+=Math.
        switch (vector){
            case 0:
            centerZ+=Math.cos(Math.toRadians(angleY));
            centerX+=Math.sin(Math.toRadians(angleY));
                break;
            case 1:
                centerZ-=Math.sin(Math.toRadians(angleY));
                centerX+=Math.cos(Math.toRadians(angleY));
                break;
            case 2:
                centerZ-=Math.cos(Math.toRadians(angleY));
                centerX-=Math.sin(Math.toRadians(angleY));
                break;
            case 3:
                centerZ+=Math.sin(Math.toRadians(angleY));
                centerX-=Math.cos(Math.toRadians(angleY));
                break;
        }
        /*System.out.println(angleX + " " + angleY + " " + vector);
        glRotatef(MouseThread.angleX, 1, 0, 0);
        switch (vector) {
            case 0:
                glRotatef(-rotateX, 1, 0, 0);
                glRotatef(angleY, 0, 1, 0);
                break;
            case 1:
                glRotatef(-rotateX, 0, 0, 1);
                glRotatef(90 + angleY, 0, 1, 0);
                break;
            case 2:
                glRotatef(rotateX, 1, 0, 0);
                glRotatef(180 - angleY < 180 ? 180 - angleY :  angleY-180, 0, 1, 0);
                break;
            case 3:
                glRotatef(rotateX, 0, 0, 1);
                glRotatef(-90 - angleY, 0, 1, 0);
                break;
        }
        rotateX = 0;*/
        }
    }
}