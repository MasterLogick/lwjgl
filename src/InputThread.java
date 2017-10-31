import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTranslatef;

public class InputThread extends Thread {
    int ctrl=0;
    static float x=0,y=0,z=0;
    private HashMap<String, Float> m = new HashMap<>();
    static int c = 0;
    static float centerx=0,centery=0,centerz=10;
    static float upx=0,upy=10,upz=0;

    @Override
    public void run() {
        try {
            this.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!Display.isCloseRequested() && !Main.isCloseRequested) {
            if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
                ctrl=(ctrl+1)%3;
                System.out.println(ctrl);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                switch (ctrl){
                    case 0:
                        z+=1;
                        break;
                    case 1:
                        centerz+=1;
                        break;
                    case 2:
                        upz+=1;
                        break;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //m.put("KZ", 1f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                switch (ctrl){
                    case 0:
                        z-=1;
                        break;
                    case 1:
                        centerz-=1;
                        break;
                    case 2:
                        upz-=1;
                        break;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //m.put("KZ", -1f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                switch (ctrl){
                    case 0:
                        y-=1;
                        break;
                    case 1:
                        centery-=1;
                        break;
                    case 2:
                        upy-=1;
                        break;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //m.put("KY", 1f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                switch (ctrl){
                    case 0:
                        y+=1;
                        break;
                    case 1:
                        centery+=1;
                        break;
                    case 2:
                        upy+=1;
                        break;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //m.put("KY", -1f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                switch (ctrl){
                    case 0:
                        x-=1;
                        break;
                    case 1:
                        centerx-=1;
                        break;
                    case 2:
                        upx-=1;
                        break;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //m.put("KX", 1f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                switch (ctrl){
                    case 0:
                        x+=1;
                        break;
                    case 1:
                        centerx+=1;
                        break;
                    case 2:
                        upx+=1;
                        break;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //m.put("KX", -1f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_F7)) {
                Mouse.setCursorPosition(640 / 2, 480 / 2);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
                //m.put("RELOAD", 0f);
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
                            //glTranslatef(Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY)), 0, Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY)));
                            x+=Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY));
                            z+=Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY));
                            break;
                        case 1:
                            //glTranslatef(-Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY)), 0, Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY)));
                            x-=Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY));
                            z+=Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY));
                            break;
                        case 2:
                            //glTranslatef(-Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY)), 0, -Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY)));
                            x-=Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY));
                            z-=Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY));
                            break;
                        case 3:
                            //glTranslatef(Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY)), 0, -Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY)));
                            x+=Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY));
                            z-=Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY));
                            break;
                    }
                    break;
                case "KY":
                    //glTranslatef(0, value, 0);
                    y+=value;
                    break;
                case "KZ":
                    switch (MouseThread.vector) {
                        case 0:
                            //glTranslatef(-Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY)),0, Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY)));
                            x-=Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY));
                            z+=Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY));
                            break;
                        case 1:
                            //glTranslatef(-Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY)), 0, -Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY)));
                            x-=Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY));
                            z-=Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY));
                            break;
                        case 2:
                            //glTranslatef(Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY)), 0, -Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY)));
                            x+=Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY));
                            z-=Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY));
                            break;
                        case 3:
                            //glTranslatef(Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY)), 0, Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY)));
                            x+=Math.signum(value)*(float) Math.sin(Math.toRadians(MouseThread.angleY));
                            z+=Math.signum(value)*(float) Math.cos(Math.toRadians(MouseThread.angleY));
                            break;
                    }

                    break;
                case "RELOAD":
                    MouseThread.vector = 0;
                    MouseThread.angleX = 0;
                    MouseThread.angleY = 0;
                    glLoadIdentity();
                    Mouse.setCursorPosition(GameStats.getWindowCenterX(),GameStats.getWindowCenterY());
                    break;
            }
        }
        m.clear();
    }
    public void moveBack(){
        glTranslatef(x,y,z);
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
