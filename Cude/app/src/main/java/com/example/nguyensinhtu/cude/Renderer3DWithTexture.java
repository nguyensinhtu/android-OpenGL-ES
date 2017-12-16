package com.example.nguyensinhtu.cude;

/**
 * Created by NGUYENSINHTU on 12/12/2017.
 */

import android.content.Context;
import android.opengl.*;
import static android.opengl.Matrix.*;

import javax.microedition.khronos.egl.*;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

public class Renderer3DWithTexture implements GLSurfaceView.Renderer {
    private Context context;
    private float[] modelViewMatrix = new float[16];
    private float[] projectionMatrix = new float[16];
    private float[] mvpMarix = new float[16];
    private  float xr = 1.0f, yr = 0.0f, zr = 0.0f;

    private CubeData cubeData;
    private Cube cube;
    private TextureShaderProgram textureShaderProgram;

    private  int texture;

    private float angle;
    private int texture1, texture2, texture3, texture4, texture5, texture6;

    public Renderer3DWithTexture(Context context){
        this.context = context;
    }

    public void setAngle(float angle){
        this.angle = angle;
    }

    public float getAngle(){
        return this.angle;
    }

    public void setRotationAxis(float x, float y, float z){
        this.xr = x;
        this.yr = y;
        this.zr = z;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClearDepthf(1.0f);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        cubeData = new CubeData();
        textureShaderProgram = new TextureShaderProgram(context);
        texture = TextureHelper.loadTexture(context, R.drawable.blue_1);
        texture1 = TextureHelper.loadTexture(context, R.drawable.green_1);
        texture2 = TextureHelper.loadTexture(context, R.drawable.orange_1);
        texture3 = TextureHelper.loadTexture(context, R.drawable.red_1);
        texture5 = TextureHelper.loadTexture(context, R.drawable.white_1);
        texture4 = TextureHelper.loadTexture(context, R.drawable.yellow_1);
        setLookAtM(modelViewMatrix, 0, 2.5f, 2.5f, 4.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        float ratio = (float)width / height;
        perspectiveM(projectionMatrix, 0, 45, ratio, 1.0f, 100.0f);
        multiplyMM(mvpMarix, 0, projectionMatrix, 0, modelViewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        float[] rotationMatrix = new float[16];
        setIdentityM(rotationMatrix, 0);

        rotateM(rotationMatrix, 0, angle, this.xr, this.yr, this.zr);
        multiplyMM(rotationMatrix, 0, mvpMarix, 0, rotationMatrix, 0);
        textureShaderProgram.useProgram();
        // front face
        textureShaderProgram.setUniforms(rotationMatrix, texture);
        cubeData.bindData(textureShaderProgram);
        cubeData.draw(0);

        // back face
        textureShaderProgram.setUniforms(rotationMatrix, texture1);
        cubeData.bindData(textureShaderProgram);
        cubeData.draw(6);
        //bottom face
        textureShaderProgram.setUniforms(rotationMatrix, texture2);
        cubeData.bindData(textureShaderProgram);
        cubeData.draw(12);
        // bottom face
        textureShaderProgram.setUniforms(rotationMatrix, texture3);
        cubeData.bindData(textureShaderProgram);
        cubeData.draw(18);
        // left face
        textureShaderProgram.setUniforms(rotationMatrix, texture4);
        cubeData.bindData(textureShaderProgram);
        cubeData.draw(24);

        // right face
        textureShaderProgram.setUniforms(rotationMatrix, texture5);
        cubeData.bindData(textureShaderProgram);
        cubeData.draw(30);

    }
}


