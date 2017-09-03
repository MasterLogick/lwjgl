import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTranslatef;

public class InputThread extends Thread {
    private HashMap<String, Float> m = new HashMap<>();
    static int c = 0;

    @Override
    public void run() {
        try {
            this.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!Display.isCloseRequested() && !Main.isCloseRequested) {
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                m.put("KZ", 1f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                m.put("KZ", -1f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                m.put("KY", 1f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                m.put("KY", -1f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                m.put("KX", 1f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                m.put("KX", -1f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_F7)) {
                Mouse.setCursorPosition(640 / 2, 480 / 2);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
                m.put("RELOAD", 0f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                Main.exit();
            }
            /*if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
                c+=4;
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
        }
    }

    public void move() {
        float value;
        for (Map.Entry e : m.entrySet()) {
            value=(float)e.getValue();
            switch ((String) e.getKey()) {
                case "KX":
                    switch (MouseThread.vector) {
                        case 0:

                            glTranslatef(Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY)), 0, Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY)));
                            break;
                        case 1:
                            glTranslatef(-Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY)), 0, Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY)));
                            break;
                        case 2:
                            glTranslatef(-Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY)), 0, -Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY)));
                            break;
                        case 3:
                            glTranslatef(Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY)), 0, -Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY)));
                            break;
                    }
                    break;
                case "KY":
                    glTranslatef(0, value, 0);
                    break;
                case "KZ":
                    switch (MouseThread.vector) {
                        case 0:/*
                            System.out.println();
                            System.out.println(((float)MouseThread.angleY/90)+" "+0+" "+value+" "+(1f-(Math.abs((float)MouseThread.angleY)/90f)));
                            System.out.println();*/
                            glTranslatef(-Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY)),0, Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY)));
                            break;
                        case 1:
                            glTranslatef(-Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY)), 0, -Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY)));
                            break;
                        case 2:
                            glTranslatef(Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY)), 0, -Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY)));
                            break;
                        case 3:
                            glTranslatef(Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY)), 0, Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY)));
                            break;
                    }

                    break;
                case "RELOAD":
                    MouseThread.vector = 0;
                    MouseThread.angleX = 0;
                    MouseThread.angleY = 0;
                    glLoadIdentity();
                    break;
            }
        }
        m.clear();
    }
}
/*
switch (MouseThread.vector){
        case 0:
        break;
        case 1:
        break;
        case 2:
        break;
        case 3:
        break;
        }*/
