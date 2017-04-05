package com.android.base.frame.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.base.frame.AppManager;
import com.android.base.util.SystemBarTintManager;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/13.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public ProgressDialog waitdialog;
    public Context mContext;
    private SystemBarTintManager mSystemBarTintManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSystemBarTintManager = new SystemBarTintManager(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //if (isUserDefinedColorForStatusBar) {//自定义状态栏的颜色
                mSystemBarTintManager.setStatusBarTintEnabled(true);
                mSystemBarTintManager.setTintColor(getResources().getColor(android.R.color.transparent));
//            } else {
//                mSystemBarTintManager.setStatusBarTintEnabled(false);
//            }
        } else {
            mSystemBarTintManager.setStatusBarTintEnabled(false);
        }
        mContext = this;
        AppManager.create().addActivity(this);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        initData();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.create().finishActivity(this);
    }

    public void gotoActivity(Class<? extends Activity> clazz, boolean finish) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }
    public void gotoActivity(Class<? extends Activity> clazz, int flag) {
        Intent intent = new Intent(this, clazz);
        intent.setFlags(flag);
        startActivity(intent);
    }

    public void gotoActivity(Class<? extends Activity> clazz, Bundle bundle, boolean finish) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) intent.putExtras(bundle);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    public void gotoActivity(Class<? extends Activity> clazz, Bundle bundle, int flags, boolean finish) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) intent.putExtras(bundle);
        intent.addFlags(flags);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    protected abstract int getContentViewId();

    protected abstract void initData();

    public ProgressDialog showWaitDialog(String text) {
        if (waitdialog != null) {
            if (!waitdialog.isShowing()) {
                waitdialog.setMessage(text);
                waitdialog.setCancelable(false);
                waitdialog.show();
                return waitdialog;
            }
            return null;
        } else {
            waitdialog = new ProgressDialog(mContext);
            waitdialog.setMessage(text);
            waitdialog.setCancelable(false);
            waitdialog.show();
            return waitdialog;
        }
    }

    public void hideWaitDialog() {
        if (waitdialog != null && waitdialog.isShowing()) {
            try {
                waitdialog.dismiss();
                waitdialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
