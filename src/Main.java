import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class Main {
    static boolean isCloseRequested = true;

    public static void main(String[] argv) {
        try {
            for (DisplayMode m : Display.getAvailableDisplayModes()) {
                System.out.println(m.toString() + " " + m.isFullscreenCapable());
            }
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        try {
            GameStats.setWindowHeight(Display.getAvailableDisplayModes()[41].getHeight());
            GameStats.setWindowWidth(Display.getAvailableDisplayModes()[41].getWidth());
            Display.setDisplayMode(/*new DisplayMode(GameStats.getWindowWidth(), GameStats.getWindowHeight())*/Display.getAvailableDisplayModes()[41]);
            //Display.setFullscreen(true);
            Display.setTitle("Three Dee Demo");
            Display.create();
            //WINDOW CREATING
        } catch (LWJGLException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }
        Model arrow = null;
        Model m = null;
        try {
            arrow = new Model(new FileInputStream(new File("C:\\lwjgl\\obj\\arrow.obj")), null);
            m = new Model(new FileInputStream(new File("C:\\lwjgl\\obj\\testrgl.obj")), TextureLoader.getTexture("PNG", new FileInputStream(new File("C:\\lwjgl\\obj\\water-wall-clipart-20.jpg"))));
        } catch (FileNotFoundException e) {                                 //MODEL INIT
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(30f, GameStats.getWindowWidth() / GameStats.getWindowHeight(), 0.001f, 700);
        glMatrixMode(GL_MODELVIEW);
        InputKeyboardThread input = new InputKeyboardThread();
        InputMouseThread mouse = new InputMouseThread();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_TEXTURE_2D);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        Mouse.setCursorPosition(GameStats.getWindowCenterX(), GameStats.getWindowCenterY());

        input.setDaemon(true);
        mouse.setDaemon(true);
        input.start();
        mouse.start();
        Mouse.setClipMouseCoordinatesToWindow(true);                                        //INIT CODE
        isCloseRequested = false;
        while (!isCloseRequested) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glLoadIdentity();
            InputKeyboardThread.gluLookAt();
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

}