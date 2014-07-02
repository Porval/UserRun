package com.jp.userrun;

import android.app.Application;

public class App extends Application {
    private static App sInstance = new App();

    public static App get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }
}
