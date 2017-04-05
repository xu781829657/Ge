package com.android.ge.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.base.frame.fragment.BaseFragment;

/**
 * Created by xudengwang on 2017/3/14.
 */

public abstract class CommonBaseFragment extends BaseFragment {


    public Context getMContext(){

        return getContext();
    }

    public void gotoActivity(Class<? extends Activity> clazz, boolean finish) {
        Intent intent = new Intent(getMContext(), clazz);
        startActivity(intent);
        if (finish) {
            ((CommonBaseActivity)getMContext()).finish();
        }
    }
    public void gotoActivity(Class<? extends Activity> clazz, int flag) {
        Intent intent = new Intent(getMContext(), clazz);
        intent.setFlags(flag);
        startActivity(intent);
    }

    public void gotoActivity(Class<? extends Activity> clazz, Bundle bundle, boolean finish) {
        Intent intent = new Intent(getMContext(), clazz);
        if (bundle != null) intent.putExtras(bundle);
        startActivity(intent);
        if (finish) {
            ((CommonBaseActivity)getMContext()).finish();
        }
    }

    public void gotoActivity(Class<? extends Activity> clazz, Bundle bundle, int flags, boolean finish) {
        Intent intent = new Intent(getMContext(), clazz);
        if (bundle != null) intent.putExtras(bundle);
        intent.addFlags(flags);
        startActivity(intent);
        if (finish) {
            ((CommonBaseActivity)getMContext()).finish();
        }
    }
}
