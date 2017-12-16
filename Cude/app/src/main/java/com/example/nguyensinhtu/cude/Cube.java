package com.example.nguyensinhtu.cude;

/**
 * Created by NGUYENSINHTU on 12/12/2017.
 */
import android.content.Context;
import android.opengl.Matrix;
import android.view.ViewGroup;

import static android.opengl.GLES20.*;
import static android.opengl.Matrix.multiplyMM;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Cube {
    private Context context;
    private  float[] m = new float[16];
    private  float tx, ty, tz;
    private float [] faceData = {
            // front face
            0f, 0f, -0.6f,
            -0.6f, -0.6f, -0.6f,
            0.6f, -0.6f, -0.6f,
            0.6f, 0.6f, -0.6f,
            -0.6f, 0.6f, -0.6f,
            -0.6f, -0.6f, -0.6f,

            //bottom face
            0f, -0.6f, 0f,
            -0.6f, -0.6f, -0.6f,
            0.6f, -0.6f, -0.6f,
            0.6f, -0.6f, 0.6f,
            -0.6f, -0.6f, 0.6f,
            -0.6f, -0.6f, -0.6f,

            // right face
            0.6f, 0f, 0f,
            0.6f, -0.6f, -0.6f,
            0.6f, 0.6f, -0.6f,
            0.6f, 0.6f, 0.6f,
            0.6f, -0.6f, 0.6f,
            0.6f, -0.6f, -0.6f,

            //top face
            0f, 0.6f, 0f,
            -0.6f, 0.6f, -0.6f,
            0.6f, 0.6f, -0.6f,
            0.6f, 0.6f, 0.6f,
            -0.6f, 0.6f, 0.6f,
            -0.6f, 0.6f, -0.6f,

            //left face
            -0.6f, 0f, 0f,
            -0.6f, -0.6f, -0.6f,
            -0.6f, 0.6f, -0.6f,
            -0.6f, 0.6f, 0.6f,
            -0.6f, -0.6f, 0.6f,
            -0.6f, -0.6f, -0.6f,

            //Rear face
            0f, 0f, 0.6f,
            -0.6f, -0.6f, 0.6f,
            0.6f, -0.6f, 0.6f,
            0.6f, 0.6f, 0.6f,
            -0.6f, 0.6f, 0.6f,
            -0.6f, -0.6f, 0.6f,

            // line in front  face
            -0.6f, -0.2f, -0.6f,
            0.6f, -0.2f, -0.6f,
            -0.2f, -0.6f, -0.6f,
            -0.2f, 0.6f, -0.6f,

            // line in top face
            -0.6f, 0.6f, -0.2f,
            0.6f, 0.6f, -0.2f,
            -0.2f, 0.6f, -0.6f,
            -0.2f, 0.6f, 0.6f,

            // line in left face
            -0.6f, -0.2f, -0.6f,
            -0.6f, -0.2f, 0.6f,
            -0.6f, -0.6f, -0.2f,
            -0.6f, 0.6f, -0.2f,
    };

    private FloatBuffer faceBuffer;

    private int program;
    private int aPositionLocation;
    private int aColorLocation;
    private int uMVPMatrixLocation;
    private int uColorLocation;

    public Cube(Context context){
        this.context = context;


        faceBuffer = ByteBuffer.allocateDirect(faceData.length*4).order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        faceBuffer.put(faceData);
        faceBuffer.position(0);

        String vertexShaderSource = Util.readTextFileFromResource(context, R.raw.cube_vertex_shader);
        String fragmentShaderSource = Util.readTextFileFromResource(context, R.raw.cube_fragment_shader);

        int vertexShader = Util.compileVertexShader(vertexShaderSource);
        int fragmentShader = Util.compileFragmentShader(fragmentShaderSource);

        program = Util.linkProgram(vertexShader, fragmentShader);

        aPositionLocation = glGetAttribLocation(program, "aPosition");
        //aColorLocation = glGetAttribLocation(program, "aColor");
        uColorLocation = glGetUniformLocation(program, "uColor");
        uMVPMatrixLocation = glGetUniformLocation(program, "uMVPMatrix");
    }

    public void draw(float[] mvpMatrix){
        glUseProgram(program);
        //truyền attribute position
        glEnableVertexAttribArray(aPositionLocation);
        glVertexAttribPointer(
                aPositionLocation, 3, GL_FLOAT, false, 0, faceBuffer);


        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, mvpMatrix, 0);
        //glDrawElements(GL_TRIANGLES, indexData.length, GL_UNSIGNED_BYTE, indexBuffer);
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
        glUniform4f(uColorLocation, 1.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLE_FAN, 6, 6);
        glUniform4f(uColorLocation, 0.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLE_FAN, 12, 6);
        glUniform4f(uColorLocation, 1.0f, 0.5f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLE_FAN, 18, 6);
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLE_FAN, 24, 6);
        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLE_FAN, 30, 6);

        glLineWidth(10f);
        glUniform4f(uColorLocation, 0.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_LINES, 36, 2);

        // draw front face and rear face
        float [] m1 = new float[16];
            glUniformMatrix4fv(uMVPMatrixLocation, 1, false, mvpMatrix, 0);
            glDrawArrays(GL_LINES, 36, 2);
            Matrix.setIdentityM(m, 0);
            Matrix.translateM(m, 0, 0f, 0.4f, 0f);
            multiplyMM(m, 0, mvpMatrix, 0, m, 0);
            glUniformMatrix4fv(uMVPMatrixLocation, 1, false, m, 0);
            glDrawArrays(GL_LINES, 36, 2);
            Matrix.setIdentityM(m1, 0);
            Matrix.translateM(m1, 0, 0f, 0.0f, 1.2f);
            multiplyMM(m1, 0, m, 0, m1, 0);
            glUniformMatrix4fv(uMVPMatrixLocation, 1, false, m1, 0);
            glDrawArrays(GL_LINES, 36, 2);
            Matrix.translateM(m1, 0, 0f, -0.4f, 0.0f);
            glUniformMatrix4fv(uMVPMatrixLocation, 1, false, m1, 0);
            glDrawArrays(GL_LINES, 36, 2);

        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, mvpMatrix, 0);
        glDrawArrays(GL_LINES, 38, 2);
        Matrix.setIdentityM(m, 0);
        Matrix.translateM(m, 0, 0.4f, 0.0f, 0f);
        multiplyMM(m, 0, mvpMatrix, 0, m, 0);
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, m, 0);
        glDrawArrays(GL_LINES, 38, 2);
        Matrix.setIdentityM(m1, 0);
        Matrix.translateM(m1, 0, 0f, 0.0f, 1.2f);
        multiplyMM(m1, 0, m, 0, m1, 0);
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, m1, 0);
        glDrawArrays(GL_LINES, 38, 2);
        Matrix.translateM(m1, 0, -0.4f, 0.0f, 0.0f);
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, m1, 0);
        glDrawArrays(GL_LINES, 38, 2);

        // draw top and bottom face
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, mvpMatrix, 0);
        glDrawArrays(GL_LINES, 40, 2);
        Matrix.setIdentityM(m, 0);
        Matrix.translateM(m, 0, 0.0f, 0.0f, 0.4f);
        multiplyMM(m, 0, mvpMatrix, 0, m, 0);
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, m, 0);
        glDrawArrays(GL_LINES, 40, 2);
        Matrix.setIdentityM(m1, 0);
        Matrix.translateM(m1, 0, 0.0f, -1.2f, 0.0f);
        multiplyMM(m1, 0, m, 0, m1, 0);
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, m1, 0);
        glDrawArrays(GL_LINES, 40, 2);
        Matrix.translateM(m1, 0, 0.0f, 0.0f, -0.4f);
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, m1, 0);
        glDrawArrays(GL_LINES, 40, 2);

        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, mvpMatrix, 0);
        glDrawArrays(GL_LINES, 42, 2);
        Matrix.setIdentityM(m, 0);
        Matrix.translateM(m, 0, 0.4f, 0.0f, 0.0f);
        multiplyMM(m, 0, mvpMatrix, 0, m, 0);
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, m, 0);
        glDrawArrays(GL_LINES, 42, 2);
        Matrix.setIdentityM(m1, 0);
        Matrix.translateM(m1, 0, 0.0f, -1.2f, 0.0f);
        multiplyMM(m1, 0, m, 0, m1, 0);
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, m1, 0);
        glDrawArrays(GL_LINES, 42, 2);
        Matrix.translateM(m1, 0, -0.4f, 0.0f, 0.0f);
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, m1, 0);
        glDrawArrays(GL_LINES, 42, 2);

        // left and right  face
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, mvpMatrix, 0);
        glDrawArrays(GL_LINES, 44, 2);
        Matrix.setIdentityM(m, 0);
        Matrix.translateM(m, 0, 0.0f, 0.4f, 0.0f);
        multiplyMM(m, 0, mvpMatrix, 0, m, 0);
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, m, 0);
        glDrawArrays(GL_LINES, 44, 2);
        Matrix.setIdentityM(m1, 0);
        Matrix.translateM(m1, 0, 1.2f, 0.0f, 0.0f);
        multiplyMM(m1, 0, m, 0, m1, 0);
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, m1, 0);
        glDrawArrays(GL_LINES, 44, 2);
        Matrix.translateM(m1, 0, 0.0f, -0.4f, 0.0f);
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, m1, 0);
        glDrawArrays(GL_LINES, 44, 2);

        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, mvpMatrix, 0);
        glDrawArrays(GL_LINES, 46, 2);
        Matrix.setIdentityM(m, 0);
        Matrix.translateM(m, 0, 0.0f, 0.0f, 0.4f);
        multiplyMM(m, 0, mvpMatrix, 0, m, 0);
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, m, 0);
        glDrawArrays(GL_LINES, 46, 2);
        Matrix.setIdentityM(m1, 0);
        Matrix.translateM(m1, 0, 1.2f, 0.0f, 0.0f);
        multiplyMM(m1, 0, m, 0, m1, 0);
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, m1, 0);
        glDrawArrays(GL_LINES, 46, 2);
        Matrix.translateM(m1, 0, 0.0f, 0.0f, -0.4f);
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, m1, 0);
        glDrawArrays(GL_LINES, 46, 2);

        glDisableVertexAttribArray(aPositionLocation);
    }

}

