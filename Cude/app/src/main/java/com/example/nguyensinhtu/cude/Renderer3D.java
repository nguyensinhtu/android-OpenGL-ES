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

public class Renderer3D implements GLSurfaceView.Renderer {
    private Context context;
    private float[] modelViewMatrix = new float[16];
    private float[] projectionMatrix = new float[16];
    private float[] mvpMarix = new float[16];
    private  float xr = 1.0f, yr = 0.0f, zr = 0.0f;

    private Cube cube;

    private float angle;

    public Renderer3D (Context context){
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
        cube = new Cube(this.context);

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

        cube.draw(rotationMatrix);

    }
}


