package com.example.nguyensinhtu.cude;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Build;

import android.opengl.*;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class display2 extends Activity {

    private GLSurfaceView glSurfaceView;
    private Renderer3DWithTexture renderer3DWithTexture;

    private float mPreviousX, mPreviousY;
    float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private boolean rendererSet = false;

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        final ConfigurationInfo configurationInfo =
                activityManager.getDeviceConfigurationInfo();

        final boolean supportsEs2 =
                configurationInfo.reqGlEsVersion >= 0x20000
                        || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                        && (Build.FINGERPRINT.startsWith("generic")
                        || Build.FINGERPRINT.startsWith("unknown")
                        || Build.MODEL.contains("google_sdk")
                        || Build.MODEL.contains("Emulator")
                        || Build.MODEL.contains("Android SDK built for x86")));


        if(supportsEs2 == true){
            rendererSet = true;
            glSurfaceView = new GLSurfaceView(this);
            glSurfaceView.setEGLContextClientVersion(2);
            renderer3DWithTexture = new Renderer3DWithTexture(this);
            glSurfaceView.setRenderer(renderer3DWithTexture);
        }

        glSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event != null){
                    float x = event.getX();
                    float y = event.getY();
                    float height = getScreenHeight();
                    float width = getScreenWidth();
                    if(event.getAction() == MotionEvent.ACTION_MOVE){
                        float dx = x - mPreviousX;
                        float dy = y - mPreviousY;
                        // scalar of two vector
                        float tmp = dx*width;
                        float len = (float)Math.sqrt((dx*dx) + (dy*dy));
                        float resAngle = (float)Math.cos((tmp)/(len*width));

                        if (resAngle > Math.toRadians(45.0)){
                            renderer3DWithTexture.setRotationAxis(1.0f, 0.0f, 0.0f);
                        }else{
                            renderer3DWithTexture.setRotationAxis(0.0f, 1.0f, 0.0f);
                        }

                        // reverse direction of rotation above the mid-line
                        if (y > v.getHeight() / 2) {
                            dx = dx * -1 ;
                        }

                        // reverse direction of rotation to left of the mid-line
                        if (x < v.getWidth() / 2) {
                            dy = dy * -1 ;
                        }

                        float angle = renderer3DWithTexture.getAngle() + ((dx + dy)*TOUCH_SCALE_FACTOR);
                        renderer3DWithTexture.setAngle(angle);
                    }

                    mPreviousX = x;
                    mPreviousY = y;


                    return true;
                }//else if (event.getAction() == MotionEvent.
                else {
                    return false;
                }
            }
        });

        setContentView(glSurfaceView);
    }

    @Override
    protected void onPause(){
        super.onPause();

        if (rendererSet){
            glSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (rendererSet){
            glSurfaceView.onResume();
        }
    }
}
