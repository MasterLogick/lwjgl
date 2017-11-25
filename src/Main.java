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
import static org.lwjgl.util.glu.GLU.gluLookAt;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class Main {
    static boolean isCloseRequested = false;

    public static void main(String[] argv) {
        try {
            Display.setDisplayMode(new DisplayMode(GameStats.getWindowWidth(), GameStats.getWindowHeight()));
            Display.setTitle("Three Dee Demo");
            Display.create();
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(30f, GameStats.getWindowWidth() / GameStats.getWindowHeight(), 0.001f, 700);
        glMatrixMode(GL_MODELVIEW);
        InputThread input = new InputThread();
        MouseThread mouse = new MouseThread();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_TEXTURE_2D);
        glTranslatef(0, -55, -250);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        Mouse.setCursorPosition(GameStats.getWindowCenterX(), GameStats.getWindowCenterY());
        input.start();
        mouse.start();
        Mouse.setClipMouseCoordinatesToWindow(true);
        //Thread input = new InputThread();
        while (!Display.isCloseRequested() && !isCloseRequested) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//            System.out.println(InputThread.x+" "+InputThread.y+" "+InputThread.z);
            glLoadIdentity();
            gluLookAt(InputThread.x,InputThread.y,InputThread.z,InputThread.x+MouseThread.centerX,InputThread.y+MouseThread.centerY,InputThread.z+MouseThread.centerZ,0,InputThread.y+100,0);
            mouse.calc();
            input.move();
            //input.moveBack();
           /* glBegin(GL_QUADS);
            //glColor3f(255,0,0);
            glTexCoord3f(0, 0,0);
            glVertex3i(0, 200,0); // Upper-left
            glTexCoord3f(1, 0,0);
            glVertex3i(200, 200,0); // Upper-right
            glTexCoord3f(1, 1,0);
            glVertex3i(200, 0,0); // Bottom-right
            glTexCoord3f(0, 1,0);
            glVertex3i(0,0,0); // Bottom-left
            glEnd();*/
            m.draw();
            arrow.draw();
            /*glBegin(GL_QUADS);
            glColor3f(50, 90, 0);
            glVertex3i(-500, -70, 500);
            glVertex3i(500, -70, 500);
            glVertex3i(500, -70, -500);
            glVertex3i(-500, -70, -500);
            glEnd();
            glClear(GL_COLOR);*/

            Display.update();
            Display.sync(60);
        }
        m.release();
        Display.destroy();
        System.exit(0);
    }

    public static void exit() {
        isCloseRequested = true;
    }

}