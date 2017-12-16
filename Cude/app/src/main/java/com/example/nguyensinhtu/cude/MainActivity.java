package com.example.nguyensinhtu.cude;

import android.content.Intent;
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
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

//    private GLSurfaceView glSurfaceView;
//    private Renderer3D renderer3D;
//
//    private float mPreviousX, mPreviousY;
//    float TOUCH_SCALE_FACTOR = 180.0f / 320;
//    private boolean rendererSet = false;
//
//    public static int getScreenWidth() {
//        return Resources.getSystem().getDisplayMetrics().widthPixels;
//    }
//
//    public static int getScreenHeight() {
//        return Resources.getSystem().getDisplayMetrics().heightPixels;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button bt1 = (Button)findViewById(R.id.button);
        Button bt2 = (Button)findViewById(R.id.button2);
        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, display2.class);
                startActivity(i);
            }
        }
        );
        bt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent ii = new Intent(MainActivity.this, display.class);
                startActivity(ii);
            }
        });
    }


    @Override
    protected void onPause(){
        super.onPause();

//        if (rendererSet){
//            glSurfaceView.onPause();
//        }
    }

    @Override
    protected void onResume(){
        super.onResume();
//        if (rendererSet){
//            glSurfaceView.onResume();
//        }
    }
}
