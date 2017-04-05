package com.android.ge.app;

import android.app.Application;

import com.android.base.frame.Base;

/**
 * Created by xudengwang on 2017/3/14.
 */

public class AppApplication extends Application{
    public int ENVIRONMENT;

    private static AppApplication singleton;
    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        Base.initialize(this);
    }

    public static AppApplication getInstance(){
        return singleton;
    }
}
