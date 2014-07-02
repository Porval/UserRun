package com.jp.userrun;

import android.app.Application;

import com.jp.userrun.utils.Pref;
import com.jp.userrun.utils.Utils;

public class App extends Application {
    private static App sInstance = new App();

    public static App get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        int isFirstLaunch = Pref.ofApp().getInt(Constants.PREF_KEY_FIRST_LAUNCH, Constants.FALSE);
        if (Utils.isTrue(isFirstLaunch)) {
            //TODO load toy data
        }
    }
}
