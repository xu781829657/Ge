package com.android.ge.ui.base;

import android.content.Context;

import com.android.base.frame.fragment.BaseFragment;

/**
 * Created by xudengwang on 2017/3/14.
 */

public abstract class CommonBaseFragment extends BaseFragment {


    public Context getMContext(){

        return getContext();
    }
}
