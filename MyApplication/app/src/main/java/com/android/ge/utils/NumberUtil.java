package com.android.ge.utils;

import android.text.TextUtils;

import com.android.base.util.ScreenUtils;

/**
 * Created by xudengwang on 17/4/10.
 */

public class NumberUtil {

    public static int getProgress(String progress_current, String progress_total) {
        if (TextUtils.isEmpty(progress_current) || TextUtils.isEmpty(progress_total)) {
            return 0;
        }
        float current = Float.valueOf(progress_current);
        float total = Float.valueOf(progress_total);
        if(current >total){
            return 100;
        }

        int progress = (int) (current * 100 / total);
        return progress;

    }

}
