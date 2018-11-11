package render;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

import java.io.*;
import java.nio.FloatBuffer;

public abstract class ShaderProgram {

    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;
    private static FloatBuffer matrix = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(String vertexShader, String fragmentShader) {
        load(vertexShader, fragmentShader);
    }

    public ShaderProgram(InputStream vertexStream, InputStream fragmentStream) {
        StringBuilder vertexShader = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(vertexStream));
            String line;
            while ((line = reader.readLine()) != null) {
                vertexShader.append(line).append("//\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        StringBuilder fragmentShader = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fragmentStream));
            String line;
            while ((line = reader.readLine()) != null) {
                fragmentShader.append(line).append("//\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        load(vertexShader.toString(), fragmentShader.toString());
    }

    public ShaderProgram(File vertexFile, File fragmentFile) {
        StringBuilder vertexShader = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(vertexFile));
            String line;
            while ((line = reader.readLine()) != null) {
                vertexShader.append(line).append("//\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        StringBuilder fragmentShader = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fragmentFile));
            String line;
            while ((line = reader.readLine()) != null) {
                fragmentShader.append(line).append("//\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        load(vertexShader.toString(), fragmentShader.toString());
    }

    protected void loadMatrix(int location, Matrix4f matrix4f) {
        matrix4f.store(matrix);
        matrix.flip();
        GL20.glUniformMatrix4(location, false, matrix);
    }

    protected void loadMatrix(int location, FloatBuffer matrix4f) {
        GL20.glUniformMatrix4(location, false, matrix4f);
    }

    private void load(String vertexShader, String fragmentShader) {
        vertexShaderID = loadShader(vertexShader, GL20.GL_VERTEX_SHADER);
        System.out.println("vertex shader loaded");
        fragmentShaderID = loadShader(fragmentShader, GL20.GL_FRAGMENT_SHADER);
        System.out.println("fragment shader loaded");
        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
        getAllUniformLocarion();
    }

    protected abstract void getAllUniformLocarion();

    protected int getUniformLocation(String location) {
        return GL20.glGetUniformLocation(programID, location);
    }

    public void start() {
        GL20.glUseProgram(programID);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    public void cleanUp() {
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }

    private static int loadShader(String shader, int type) {
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shader);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }

}
