package com.android.base.util;

import android.util.Log;

/**
 * Created by xudengwang on 2016/08/30.
 */
public class LogUtils {

    public static boolean print = false;
    private static final String LOG_FILTER = "123";

    private static final String TAG = "GE";
    public static void d(String log) {
        if (print) {
            Log.d(TAG, LOG_FILTER + log);
        }
    }

    public static void d(Class<?> class_value, String log) {
        if (print) {
            String tag = class_value.getName();
            int index = tag.lastIndexOf('.');
            if (index != -1)
                tag = tag.substring(index + 1);
            Log.d(tag, LOG_FILTER + log);
        }
    }

    public static void i(Class<?> class_value, String log) {
        if (print) {
            String tag = class_value.getName();
            int index = tag.lastIndexOf('.');
            if (index != -1)
                tag = tag.substring(index + 1);
            Log.i(tag, LOG_FILTER + log);
        }
    }

    public static void e(Class<?> class_value, String log) {
        if (print) {
            String tag = class_value.getName();
            int index = tag.lastIndexOf('.');
            if (index != -1)
                tag = tag.substring(index + 1);
            Log.e(tag, LOG_FILTER + log);
        }
    }
}
