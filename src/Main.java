import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class Main {
    static boolean isCloseRequested = false;

    public static void main(String[] argv) {
        // Initialization code Display
        try {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.setTitle("Three Dee Demo");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }
        Model m = null;
        try {
            m = new Model(new FileInputStream(new File("C:\\3d max\\obj\\testrgl.obj")), TextureLoader.getTexture("PNG", new FileInputStream(new File("C:\\3d max\\obj\\water-wall-clipart-20.jpg"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Initialization code OpenGL
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        // Create a new perspective with 30 degree angle (field of view), 640 / 480 aspect ratio, 0.001f zNear, 100 zFar
        // Note: 	+x is to the right
        //     		+y is to the top
        //			+z is to the camera
        gluPerspective(30f, 800 / 600, 0.001f, 700);
        glMatrixMode(GL_MODELVIEW);
        InputThread input = new InputThread();
        MouseThread mouse = new MouseThread();


        // To make sure the points closest to the camera are shown in front of the points that are farther away.
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_TEXTURE_2D);
        glTranslatef(0, -55, -250);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        Mouse.setCursorPosition(640 / 2, 480 / 2);
        input.start();
        mouse.start();
        Mouse.setClipMouseCoordinatesToWindow(true);
        //Thread input = new InputThread();
        while (!Display.isCloseRequested() && !isCloseRequested) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            //mouse.arotate();
            mouse.rotate();
            input.move();
            /*glBegin(GL_QUADS);
            glTexCoord3f(0,0,0);
            glVertex3f(0, 0, 0);//ul
            glTexCoord3f(1,0,0);
            glVertex3f(20, 0, 0);//ur
            glTexCoord3f(1,1,0);
            glVertex3f(20, 20, 0);//bl
            glTexCoord3f(0,1,0);
            glVertex3f(0, 20, 0);//br
            glVertex3f(-100,0,-100);
            glVertex3f(100,0,-100);
            glVertex3f(100,0,100);
            glVertex3f(-100,0,100);
            glEnd();*/
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
            glBegin(GL_QUADS);
            glColor3f(50, 90, 0);
            glVertex3i(-500, -70, 500);
            glVertex3i(500, -70, 500);
            glVertex3i(500, -70, -500);
            glVertex3i(-500, -70, -500);
            glEnd();
            glClear(GL_COLOR);

            Display.update();
            Display.sync(60);
        }
        m.release();
        Display.destroy();
        // Exit the JVM (for some reason this lingers on Macintosh)
        System.exit(0);
    }

    public static void exit() {
        isCloseRequested = true;
    }

}