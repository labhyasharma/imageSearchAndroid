package com.joshtalks.joshtalksassignment;

import android.app.Application;

public class AppRoot extends Application {
    private static AppRoot mAppRootInstance;

    public static AppRoot getInstance() {
        return mAppRootInstance;
    }

    public static void setInstance(AppRoot app) {
        mAppRootInstance = app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
    }
}
