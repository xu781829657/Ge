package com.android.base.util;

import android.content.Context;
import android.widget.Toast;

import com.android.base.frame.Base;

/**
 * Created by xudengwang on 2016/6/1.
 */
public class ToastUtils {
    private static Context mContext;
    private static ToastUtils mInstance;
    private Toast mToast;

    public static ToastUtils getInstance() {
        if (mInstance == null) {
            mInstance = new ToastUtils(Base.getContext());
        }
        return mInstance;
    }

    public static void init(Context ctx) {
        mInstance = new ToastUtils(ctx);
    }

    private ToastUtils(Context ctx) {
        mContext = ctx;
    }

    public void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }

    public void showToast(int textId) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, Base.string(textId), Toast.LENGTH_SHORT);
        } else {
            mToast.setText(Base.string(textId));
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }


    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
