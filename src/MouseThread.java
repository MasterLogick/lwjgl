import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.glRotatef;

public class MouseThread extends Thread {
    static int vector = 0, rotateX = 0, rotateY = 0, angleX = 0, angleY = 0/*, prevAngleX = 0, prevAngleY = 0*/;
    static boolean vertical = false;
    //private HashMap<Integer, Integer> m = new HashMap<>();

    @Override
    public void run() {
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int dx, dy;
        while (!Display.isCloseRequested() && !Main.isCloseRequested) {
//            m.put(Mouse.getDX(), Mouse.getDY());
            dx = Mouse.getDY();
            dy = Mouse.getDX();
            rotateX += dx;
            rotateY += dy;
            angleX += dx;
            angleY += dy;
            if (rotateX > 180) {
                rotateX -= 360;
            }
            if (rotateX < -180) {
                rotateX += 360;
            }
            if (rotateY > 180) {
                rotateY -= 360;
            }
            if (rotateY < -180) {
                rotateY += 360;
            }
            while (angleY>45){
                vector+=1;
                angleY-=90;
                if(vector==4){
                    vector=0;
                }
            }
            while (angleY<-45){
                vector-=1;
                angleY+=90;
                if(vector==-1){
                    vector=3;
                }
            }
            if (angleX > 180) {
                angleX -= 360;
            }
            if (angleX < -180) {
                angleX += 360;
            }
            /*if (angleY >= -45 && angleY < 45)
                vector = 0;
            if (angleY >= 45 && angleY < 135)
                vector = 1;
            if (angleY >= 135 ^ angleY < -135)
                vector = 2;
            if (angleY >= -135 && angleY < -45)
                vector = 3;*/
        }
    }

   /* public void arotate() {
        glRotatef(-prevAngleX, 1, 0, 0);
        glRotatef(-prevAngleY, 0, 1, 0);
        prevAngleX = 0;
        prevAngleY = 0;
    }*/

    public void rotate() {
        System.out.println(angleX + " " + angleY + " " + vector);
        /*prevAngleX = angleX;
        prevAngleY = angleY;*/
       /* int dx, dy;
        for (Map.Entry entry : m.entrySet()) {
            dx= (int) entry.getKey();
            dy= (int) entry.getValue();*/
        //System.out.println(vector);

        /*switch (vector) {
            case 0:
                glRotatef(-rotateX, 1, 0, 0);
                break;
            case 1:
                glRotatef(-rotateX, 0, 0, 1);
                break;
            case 2:
                glRotatef(rotateX, 1, 0, 0);
                break;
            case 3:
                glRotatef(rotateX, 0, 0, 1);
                break;
        }*/
        switch (vector) {
            case 0:
                glRotatef(-rotateX, 1, 0, 0);
                break;
            case 1:
                glRotatef(-rotateX, 0, 0, 1);
                break;
            case 2:
                glRotatef(rotateX, 1, 0, 0);
                break;
            case 3:
                glRotatef(rotateX, 0, 0, 1);
                break;
        }
        glRotatef(rotateY, 0, 1, 0);
        rotateX = 0;
        rotateY = 0;
        /*}
        m.clear();*/
    }
}