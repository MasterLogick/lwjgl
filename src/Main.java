import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.TextureLoader;

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
            Display.setDisplayMode(new DisplayMode(GameStats.getWindowWidth(), GameStats.getWindowHeight()));
            Display.setTitle("Three Dee Demo");
            Display.create();
        } catch (LWJGLException e) {
            Main.ERROR(e);
        }
//        new TestModel(Main.class.getResourceAsStream("cubes.obj"),
//                Main.class.getResourceAsStream("wood-textures-seamless-hq-resolution.jpg"), "JPG", GL15.GL_STATIC_DRAW);
        Model arrow = null;
        Model m = null;
        try {
            ;
            arrow = new Model(Main.class.getResourceAsStream("arrow.obj"), null);
            m = new Model(Main.class.getResourceAsStream("testrgl.obj"), TextureLoader.getTexture("JPG", Main.class.getResourceAsStream("k3unwj.jpg")));
        } catch (FileNotFoundException e) {         //---------------------------------MODEL INIT
            Main.ERROR(e);
        } catch (IOException e) {
            Main.ERROR(e);
        }
        //StaticShader staticShader = new StaticShader();
        InputKeyboardThread input = new InputKeyboardThread();
        InputMouseThread mouse = new InputMouseThread();
        input.setDaemon(true);
        mouse.setDaemon(true);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        gluPerspective(30f, GameStats.getWindowWidth() / GameStats.getWindowHeight(), 0.001f, 700);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        Mouse.setCursorPosition(GameStats.getWindowCenterX(), GameStats.getWindowCenterY());
        input.start();
        mouse.start();
        //------------------------------INIT CODE
        isCloseRequested = false;
        while (!isCloseRequested) {
            while (!(InputMouseThread.isMouseDataRead && InputKeyboardThread.isKeyboardDataRead)) {
                System.out.print("");
            }
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glLoadIdentity();
            //staticShader.start();
            GLU.gluLookAt(InputKeyboardThread.xPos, InputKeyboardThread.yPos, InputKeyboardThread.zPos, InputKeyboardThread.xPos + InputMouseThread.xPoint, InputKeyboardThread.yPos + InputMouseThread.yPoint, InputKeyboardThread.zPos + InputMouseThread.zPoint, 0, 1, 0);
            m.draw();
            arrow.draw();
            //staticShader.stop();
            Display.update();
            Display.sync(60);
        }
        exit();
        //staticShader.cleanUp();
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