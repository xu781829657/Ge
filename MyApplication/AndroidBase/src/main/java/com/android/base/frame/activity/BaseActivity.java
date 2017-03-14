package com.android.base.frame.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.base.frame.AppManager;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/13.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public ProgressDialog waitdialog;
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
