package render;

import org.lwjgl.util.vector.Matrix4f;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;

public class StaticShader extends ShaderProgram {
    private int transformMatrixLocation;
    private int projectionMatrixLocation;

    public StaticShader() throws FileNotFoundException {
        super(new FileInputStream("C:\\lwjgl\\shaders\\StaticVertexShader.txt"), new FileInputStream("C:\\lwjgl\\shaders\\StaticFragmentShader.txt"));
    }

    @Override
    protected void getAllUniformLocarion() {
        projectionMatrixLocation = super.getUniformLocation("projection");
        transformMatrixLocation = super.getUniformLocation("transform");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(0, "textureCoords");
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(transformMatrixLocation, matrix);
    }

    public void loadTransformationMatrix(FloatBuffer matrix) {
        super.loadMatrix(transformMatrixLocation, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(projectionMatrixLocation, matrix);
    }

    public void loadProjectionMatrix(FloatBuffer matrix) {
        super.loadMatrix(projectionMatrixLocation, matrix);
    }
}
