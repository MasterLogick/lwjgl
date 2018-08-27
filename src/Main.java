import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

//import org.lwjgl.input.Mouse;

public class Main {
    static boolean isCloseRequested = true;

    public static void main(String[] argv) {
        Mouse.poll();
        try {
            for (DisplayMode m : Display.getAvailableDisplayModes()) {
                System.out.println(m.toString() + " " + m.isFullscreenCapable());
            }
        } catch (LWJGLException e) {
            Main.ERROR(e);
        }
        try {
            Display.setDisplayMode(new DisplayMode(GameStats.getWindowWidth(), GameStats.getWindowHeight()));
            Display.setTitle("Three Dee Demo");
            Display.create();
        } catch (LWJGLException e) {
            Main.ERROR(e);
        }
        Model arrow = null;
        Model m = null;
        try {
            ;
            arrow = new Model(Main.class.getResourceAsStream("arrow.obj"), null);
            m = new Model(Main.class.getResourceAsStream("testrgl.obj"), TextureLoader.getTexture("PNG", Main.class.getResourceAsStream("water-wall-clipart-20.jpg")));
        } catch (FileNotFoundException e) {         //---------------------------------MODEL INIT
            Main.ERROR(e);
        } catch (IOException e) {
            Main.ERROR(e);
        }
        InputKeyboardThread input = new InputKeyboardThread();
        InputMouseThread mouse = new InputMouseThread();
        input.setDaemon(true);
        mouse.setDaemon(true);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(30f, GameStats.getWindowWidth() / GameStats.getWindowHeight(), 0.001f, 700);
        glMatrixMode(GL_MODELVIEW);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_TEXTURE_2D);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        Mouse.setCursorPosition(GameStats.getWindowCenterX(), GameStats.getWindowCenterY());
        input.start();
        mouse.start();
        //------------------------------INIT CODE
        isCloseRequested = false;
        while (!isCloseRequested) {
            //System.out.println(Display.getX());
            while (!(InputMouseThread.isMouseRead && InputKeyboardThread.isKeyboardRead)) ;
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glLoadIdentity();
            GLU.gluLookAt(InputKeyboardThread.xPos, InputKeyboardThread.yPos, InputKeyboardThread.zPos, InputKeyboardThread.xPos + InputMouseThread.xPoint, InputKeyboardThread.yPos + InputMouseThread.yPoint, InputKeyboardThread.zPos + InputMouseThread.zPoint, 0, 1, 0);
            m.draw();
            arrow.draw();
            Display.update();
            Display.sync(60);
        }
        exit();
        m.release();
        Display.destroy();
        System.exit(0);
    }

    public static void exit() {
        isCloseRequested = true;
    }

    public static void ERROR(Exception e) {
        e.printStackTrace();
        Display.destroy();
        System.exit(1);
    }
}