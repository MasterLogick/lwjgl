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
                m.put("KZ", 0.5f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                m.put("KZ", -0.5f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                m.put("KY", 0.5f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                m.put("KY", -0.5f);
            }
            // If we're pressing the "down" key decrease our speed

            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                m.put("KX",0.5f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                m.put("KX", -0.5f);
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

            //glTranslatef(x, y, z);
            /*z = 0;
            x = 0;
            y = 0;*/
        }
    }

    public void move() {
        for (Map.Entry e : m.entrySet()) {
            /*switch (MouseThread.vector){
                case 0:

                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }*/

            switch ((String) e.getKey()) {
                case "KX":
                    switch (MouseThread.vector) {
                        case 0:
                            glTranslatef((float) (e.getValue())*(1-((float)Math.abs(MouseThread.angleY)/90)), 0, (float) e.getValue()*(((float) MouseThread.angleY)/90));
                            break;
                        case 1:
                            glTranslatef(0, 0, (float) e.getValue());
                            break;
                        case 2:
                            glTranslatef(-(float) e.getValue(), 0, 0);
                            break;
                        case 3:
                            glTranslatef(-(float) e.getValue()*(MouseThread.angleY/90), 0, -(float) e.getValue()*(1-Math.abs(MouseThread.angleY)/90));
                            break;
                    }
                    break;
                case "KY":
                    glTranslatef(0, (float) e.getValue(), 0);
                    break;
                case "KZ":
                    switch (MouseThread.vector) {
                        case 0:
                            glTranslatef(0, 0, (float) e.getValue());
                            break;
                        case 1:
                            glTranslatef(-(float) e.getValue(), 0, 0);
                            break;
                        case 2:
                            glTranslatef(0, 0, -(float) e.getValue());
                            break;
                        case 3:
                            glTranslatef((float) e.getValue(), 0, 0);
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
