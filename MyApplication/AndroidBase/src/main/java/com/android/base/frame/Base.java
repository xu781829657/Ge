package com.android.base.frame;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.base.util.ToastUtils;

/**
 *
 */
public class Base {

    private static Context context;

    public static void initialize(@NonNull Context context) {
        Base.context = context;
    }

    public static Context getContext() {
        synchronized (Base.class) {
            if (Base.context == null)
                throw new NullPointerException("Call Base.initialize(context) within your Application onCreate() method.");

            return Base.context.getApplicationContext();
        }
    }

    public static Resources getResources() {
        return Base.getContext().getResources();
    }

    public static Resources.Theme getTheme() {
        return Base.getContext().getTheme();
    }

    public static AssetManager getAssets() {
        return Base.getContext().getAssets();
    }

    public static Configuration getConfiguration() {
        return Base.getResources().getConfiguration();
    }

    public static DisplayMetrics getDisplayMetrics() {
        return Base.getResources().getDisplayMetrics();
    }

    public static String string(int id) {
        return getResources().getString(id);
    }

    public static String string(int id, Object... args) {
        return getResources().getString(id, args);
    }

    //toast提示
    public static void showToast(String string) {
        ToastUtils.getInstance().showToast(string);
    }

    public static void showToast(int resid) {
        ToastUtils.getInstance().showToast(string(resid));
    }

    /**
     * 强制隐藏输入法键盘
     */
    public static void hideInput(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)  Base.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * 强制隐藏输入法键盘
     */
    public static void showInput(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) Base.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


}
// TODO: Thread safety
// TODO: ripple, bitmap, time, contact list, picture list, video list, connectivity, wake lock, screen lock/off/on, get attributes, cookie, audio
// TODO: keystore
// TODO: http://jo.centis1504.net/?p=1189
// TODO: Test codes