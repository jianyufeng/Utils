package com.fule.mesurekeyheight;

import android.app.Application;

/**
 * 作者:Created by 简玉锋 on 2017/7/27 17:49
 * 邮箱: jianyufeng@38.hn
 */

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    /**
     * 单例，返回一个实例
     * @return
     */
    public static App getInstance() {
        return instance;
    }
}
