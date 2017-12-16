package com.example.nguyensinhtu.cude;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

/**
 * Created by NGUYENSINHTU on 12/14/2017.
 */

public class CubeData {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT
            + TEXTURE_COORDINATES_COMPONENT_COUNT) * 4;

    private static final float[] VERTEX_DATA = {
            // front face
            0f, 0f, -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 1.0f, 1.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 0.0f,
            -0.5f, 0.5f, -0.5f, 0.0f, 0.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,

            //Rear face
            0f, 0f, 0.5f,0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
            -0.5f, 0.5f, 0.5f, 0.0f, 1.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,

            //bottom face
            0f, -0.5f, 0f, 0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f, 1.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            -0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
            -0.5f, -0.5f, -0.5f, 1.0f, 1.0f,

            //top face
            0f, 0.5f, 0f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, 0.5f, -0.5f,  1.0f, 1.0f,
            0.5f, 0.5f, 0.5f,   1.0f, 0.0f,
            -0.5f, 0.5f, 0.5f, 0.0f, 0.0f,
            -0.5f, 0.5f, -0.5f,0.0f, 1.0f,


            // left face
            0.5f, 0f, 0f,0.5f, 0.5f,
            0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, 0.5f, -0.5f,1.0f, 1.0f,
            0.5f, 0.5f, 0.5f,1.0f, 0.0f,
            0.5f, -0.5f, 0.5f,0.0f, 0.0f,
            0.5f, -0.5f, -0.5f,0.0f, 1.0f,

            //left face
            -0.5f, 0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f,    0.0f, 1.0f,
            -0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            -0.5f, 0.5f, 0.5f,1.0f, 0.0f,
            -0.5f, -0.5f, 0.5f,0.0f, 0.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,


    };

    private final VertexArray vertexArray;

    public CubeData() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(TextureShaderProgram textureProgram) {
        vertexArray.setVertexAttribPointer(
                0,
                textureProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);

        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                textureProgram.getTextureCoordinatesAttributeLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE);
    }

    public void draw(int pos) {
        glDrawArrays(GL_TRIANGLE_FAN, pos, 6);
    }
}
