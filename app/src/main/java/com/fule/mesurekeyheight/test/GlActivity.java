package com.fule.mesurekeyheight.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.fule.mesurekeyheight.R;


/**
 * Created by Administrator on 2017/9/15.
 */

public class GlActivity extends FragmentActivity {
    private CameraGLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_gl);
        glSurfaceView = (CameraGLSurfaceView) findViewById(R.id.camera_textureview);


    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.bringToFront();
    }
}
