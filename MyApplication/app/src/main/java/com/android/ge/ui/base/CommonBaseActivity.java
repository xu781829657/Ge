package com.android.ge.ui.base;

import android.app.Dialog;

import com.android.base.frame.activity.BaseActivity;
import com.android.ge.utils.ui.ViewDialog;

/**
 * Created by xudengwang on 2017/3/14.
 */

public abstract  class CommonBaseActivity extends BaseActivity{

    protected Dialog mLodingDialog;
    protected void showLoadingDialog(String str) {
        if (mLodingDialog != null) {
            mLodingDialog.dismiss();
        }
        mLodingDialog = ViewDialog.createLoadingDialog(mContext, str);
        mLodingDialog.show();
    }

    protected void dismissLoadingDialog() {
        if (mLodingDialog != null && mLodingDialog.isShowing()) {
            mLodingDialog.dismiss();
        }
    }
}
