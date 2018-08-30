import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TestModel {
    private int vaoID = 0;
    private List<Integer> vbos = new ArrayList<>();
    private int indicesNumber = 0;
    private int textureID = 0;
    private int renderType = -1;

    public TestModel(InputStream objFile, InputStream textureFile, String textureFileFormat, int renderType) {
        this.renderType = renderType;
        try {
            textureID = TextureLoader.getTexture(textureFileFormat, textureFile).getTextureID();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader bf = new BufferedReader(new InputStreamReader(objFile));
        String file = null;
        String s = null;
        try {
            while ((s = bf.readLine()) != null) {
                file += s + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Vector<Float> textureCoords = getFloatData("vt", file.split("\n"));
        Vector<Float> vertices = getFloatData("v", file.split("\n"));
        Vector<Float> normals = getFloatData("vn", file.split("\n"));
        Vector<Integer> indices = getIntegerData("f", file.split("\n"));
        float[] vertArr = new float[indices.size()];
        float[] normArr = new float[indices.size()];
        float[] textureArr = new float[indices.size() / 3 * 2];
        int[] indicesArr = new int[indices.size() / 3];
        for (int i = 0; i < indices.size(); i++) {
            switch (i % 3) {
                case 0:
                    vertArr[3 * i] = vertices.get(indices.get(i) * 3);
                    vertArr[3 * i + 1] = vertices.get(indices.get(i) * 3 + 1);
                    vertArr[3 * i + 2] = vertices.get(indices.get(i) * 3 + 2);
                    indicesArr[i] = indices.get(i);
                    break;
                case 1:
                    textureArr[i / 3 * 2] = textureCoords.get(indices.get(i) / 3 * 2);
                    textureArr[i / 3 * 2 + 1] = textureCoords.get(indices.get(i) / 3 * 2 + 1);
                    break;
                case 2:
                    normArr[3 * i] = normals.get(indices.get(i) * 3);
                    normArr[3 * i + 1] = normals.get(indices.get(i) * 3 + 1);
                    normArr[3 * i + 2] = normals.get(indices.get(i) * 3 + 2);
                    break;
            }
        }
        System.out.println();
    }

    private Vector<Integer> getIntegerData(String type, String[] file) {
        Vector<Integer> retVal = new Vector<>();
        for (String s : file) {
            if (s.length() < 28) {
                continue;
            }
            if (!s.substring(0, type.length() + 1).equals(type + " ")) {
                continue;
            }
            for (String str : s.split("[ /]")) {
                if (!str.equals(type) && !str.isEmpty()) retVal.add(Integer.parseInt(str) - 1);
            }
        }
        return retVal;
    }

    private Vector<Float> getFloatData(String type, String[] file) {
        Vector<Float> retVal = new Vector<>();
        for (String s : file) {
            if (s.length() < 28) {
                continue;
            }
            if (!s.substring(0, type.length() + 1).equals(type + " ")) {
                continue;
            }
            for (String str : s.split("[ /]")) {
                if (!str.equals(type) && !str.isEmpty()) retVal.add(Float.parseFloat(str));
            }
        }
        return retVal;
    }

    private void loadToVAO(float[] vertices, float[] textureCords, int[] indices) {
        indicesNumber = indices.length;
        vaoID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);
        loadVertices(vertices);
        loadTextureCoords(textureCords);
        loadIndices(indices);
        GL30.glBindVertexArray(0);
    }

    private void loadTextureCoords(float[] textureCords) {
        int verticesVBOid = GL15.glGenBuffers();
        vbos.add(verticesVBOid);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesVBOid);
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(textureCords.length);
        verticesBuffer.put(textureCords);
        verticesBuffer.flip();
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, renderType);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void loadIndices(int[] indices) {
        int facesVBOid = GL15.glGenBuffers();
        vbos.add(facesVBOid);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, facesVBOid);
        IntBuffer facesBuffer = BufferUtils.createIntBuffer(indices.length);
        facesBuffer.put(indices);
        facesBuffer.flip();
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, facesBuffer, renderType);

    }

    private void loadVertices(float[] vertices) {
        int verticesVBOid = GL15.glGenBuffers();
        vbos.add(verticesVBOid);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesVBOid);
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices);
        verticesBuffer.flip();
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, renderType);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void cleanUp() {
        GL30.glDeleteVertexArrays(vaoID);
        GL11.glDeleteTextures(textureID);
        for (int vbo : vbos) {
            GL15.glDeleteBuffers(vbo);
        }
    }

    public void render() {
        GL30.glBindVertexArray(vaoID);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
        GL11.glDrawElements(GL11.GL_TRIANGLES, indicesNumber, GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }
}