package com.android.ge.app;

import android.app.Application;

import com.android.base.frame.Base;

/**
 * Created by xudengwang on 2017/3/14.
 */

public class AppApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Base.initialize(this);
    }
}
