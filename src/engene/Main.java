package engene;

import display.DisplayInfo;
import input.InputReader;
import input.Mouse;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureLoader;
import render.MatrixUtil;
import render.StaticShader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.lwjgl.util.glu.GLU.gluPerspective;

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
            Display.setDisplayMode(new DisplayMode(DisplayInfo.getWindowWidth(), DisplayInfo.getWindowHeight()));
            Display.setTitle("Three Dee Demo");
            Display.create();
        } catch (LWJGLException e) {
            Main.ERROR(e);
        }
//        new TestModel(Main.class.getResourceAsStream("cubes.obj"),
//                Main.class.getResourceAsStream("wood-textures-seamless-hq-resolution.jpg"), "JPG", GL15.GL_STATIC_DRAW);
        /*Model arrow = null;
        Model m = null;
        try {
            arrow = new Model(Main.class.getResourceAsStream("arrow.obj"), null);
            m = new Model(Main.class.getResourceAsStream("testrgl.obj"), TextureLoader.getTexture("JPG", Main.class.getResourceAsStream("wood-textures-seamless-hq-resolution.jpg")));
        } catch (FileNotFoundException e) {         //---------------------------------MODEL INIT
            Main.ERROR(e);
        } catch (IOException e) {
            Main.ERROR(e);
        }*/
        float[] vertices = {
                -2f, -2f, 0f,//v0
                -2f, 2f, 0f,//v1
                2f, 2f, 0f,//v2
                2f, -2f, 0f,//v3

        };
        float[] texture = new float[]{
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };
        int[] indices = {
                0, 1, 3,//top left triangle (v0, v1, v3)
                3, 1, 2//bottom right triangle (v3, v1, v2)
        };
        int id = 0;
        try {
            id = TextureLoader.getTexture("JPG", new FileInputStream("C:\\lwjgl\\obj\\wood-textures-seamless-hq-resolution.jpg")).getTextureID();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Renderer r = new Renderer();
        RawModel model = new Loader().loadToVAO(vertices, texture, indices);
        StaticShader staticShader = null;
        try {
            staticShader = new StaticShader();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputReader.defaultInit();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        gluPerspective(30f, DisplayInfo.getWindowWidth() / DisplayInfo.getWindowHeight(), 0.00001f, 10000);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        Mouse.setCursorPosition(DisplayInfo.getWindowCenterX(), DisplayInfo.getWindowCenterY());
        isCloseRequested = false;
        while (!isCloseRequested) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
//            GL11.glLoadIdentity();
            InputReader.readData();
            staticShader.start();
            staticShader.loadTransformationMatrix(MatrixUtil.createLookAtMatrix(InputReader.getXPos(), InputReader.getYPos(), InputReader.getZPos(), InputReader.getXPos() + InputReader.getXPoint(), InputReader.getYPos() + InputReader.getYPoint(), InputReader.getZPos() + InputReader.getZPoint(), 0, 1, 0));
            r.render(model, id);
//             m.draw();
//            arrow.draw();
//            GL11.glTranslatef(InputReader.getXPos(), InputReader.getYPos(), InputReader.getZPos());
            staticShader.stop();
            Display.update();
            Display.sync(60);
        }
        exit();
        staticShader.cleanUp();
        //m.release();
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