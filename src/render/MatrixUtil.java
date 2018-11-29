package render;


import org.lwjgl.BufferUtils;
import org.lwjgl.util.glu.Util;
import org.lwjgl.util.vector.Matrix4f;

import java.nio.FloatBuffer;

public class MatrixUtil extends Util {
    static Matrix4f projectionMatrix = new Matrix4f();

    public static void createProjectionMatrix(float fovy, float aspect, float zNear, float zFar) {
        float radians = fovy / 2.0F * 3.1415927F / 180.0F;
        float deltaZ = zFar - zNear;
        float sine = (float) Math.sin((double) radians);
        if (deltaZ != 0.0F && sine != 0.0F && aspect != 0.0F) {
            float cotangent = (float) Math.cos((double) radians) / sine;
            projectionMatrix.m00 = cotangent / aspect;
            projectionMatrix.m11 = cotangent;
            projectionMatrix.m22 = -(zFar + zNear / deltaZ);
            projectionMatrix.m23 = -1.0F;
            projectionMatrix.m32 = -2.0F * zNear * zFar / deltaZ;
            projectionMatrix.m33 = 0.0F;
        }
    }

    public static Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public static Matrix4f createLookAtMatrix(float eyex, float eyey, float eyez, float centerx, float centery, float centerz, float upx, float upy, float upz) {
        float[] forward = new float[3];
        float[] side = new float[3];
        float[] up = new float[3];
        forward[0] = centerx - eyex;
        forward[1] = centery - eyey;
        forward[2] = centerz - eyez;
        up[0] = upx;
        up[1] = upy;
        up[2] = upz;
        normalize(forward);
        cross(forward, up, side);
        normalize(side);
        cross(side, forward, up);
        Matrix4f matrix = new Matrix4f();
        matrix.m00 = side[0];
        matrix.m01 = up[0];
        matrix.m02 = -forward[0];
        matrix.m03 = 0;
        matrix.m10 = side[1];
        matrix.m11 = up[1];
        matrix.m12 = -forward[1];
        matrix.m13 = 0;
        matrix.m20 = side[2];
        matrix.m21 = up[2];
        matrix.m22 = -forward[2];
        matrix.m23 = 0;
        matrix.m30 = matrix.m00 * eyex + matrix.m10 * eyey + matrix.m20 * eyez;
        matrix.m31 = matrix.m01 * eyex + matrix.m11 * eyey + matrix.m21 * eyez;
        matrix.m32 = matrix.m02 * eyex + matrix.m12 * eyey + matrix.m22 * eyez;
        matrix.m33 = matrix.m03 * eyex + matrix.m13 * eyey + matrix.m23 * eyez + 1;
        return matrix;
    }

    public static FloatBuffer asFlippedFloatBuffer(float... values) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
        buffer.put(values);
        buffer.flip();
        return buffer;
    }
}
