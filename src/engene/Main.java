package engene;

import display.DisplayInfo;
import input.InputReader;
import input.Mouse;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.TextureLoader;
import render.MatrixUtil;
import render.Model;
import res.ResourseManager;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.lwjgl.util.glu.GLU.gluPerspective;

public class Main {
    static boolean isCloseRequested = true;
    public static float LightX = 0, LightY = 0, LightZ = 0;
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
            Display.setDisplayMode(new DisplayMode(DisplayInfo.getWindowWidth(), DisplayInfo.getWindowHeight()));
            Display.setTitle("Three Dee Demo");
            Display.create();
        } catch (LWJGLException e) {
            Main.ERROR(e);
        }
        Model arrow = null;
        Model m = null;
        Model bunny = null;
        try {
            bunny = new Model(ResourseManager.getResourse("bunny.obj"), null);
            arrow = new Model(ResourseManager.getResourse("arrow.obj"), null);
            m = new Model(ResourseManager.getResourse("testrgl.obj"), TextureLoader.getTexture("JPG", ResourseManager.getResourse("wood-textures-seamless-hq-resolution.jpg")));
        } catch (FileNotFoundException e) {         //---------------------------------MODEL INIT
            Main.ERROR(e);
        } catch (IOException e) {
            Main.ERROR(e);
        }
        InputReader.defaultInit();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        gluPerspective(30f, DisplayInfo.getWindowWidth() / DisplayInfo.getWindowHeight(), 0.001f, 1000);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_TEXTURE_2D);


        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT0);
        GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, MatrixUtil.asFlippedFloatBuffer(0.05f, 0.05f, 0.05f, 1f));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, MatrixUtil.asFlippedFloatBuffer(0, 0, 0, 1));
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE);


        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        Mouse.setCursorPosition(DisplayInfo.getWindowCenterX(), DisplayInfo.getWindowCenterY());
        isCloseRequested = false;
        while (!isCloseRequested) {
            GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, MatrixUtil.asFlippedFloatBuffer(LightX, LightY, LightZ, 1));
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glLoadIdentity();
            InputReader.readData();
            GLU.gluLookAt(InputReader.getXPos(), InputReader.getYPos(), InputReader.getZPos(), InputReader.getXPos() + InputReader.getXPoint(), InputReader.getYPos() + InputReader.getYPoint(), InputReader.getZPos() + InputReader.getZPoint(), 0, 1, 0);
            m.draw();
            /*GL11.glPushMatrix();
            GL11.glTranslatef(10,0,0);
            m.draw();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(20,0,0);
            m.draw();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(30,0,0);
            m.draw();
            GL11.glPopMatrix();*/
            arrow.draw();
//            bunny.draw();
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