package com.fule.mesurekeyheight.test.camerafilter;

import android.app.Application;

import com.fule.mesurekeyheight.test.camerafilter.video.TextureMovieEncoder;


public class App extends Application {

    @Override public void onCreate() {
        super.onCreate();
        TextureMovieEncoder.initialize(getApplicationContext());
    }
}
