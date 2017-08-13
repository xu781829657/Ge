package com.android.ge.controller.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.android.ge.R;
import com.android.ge.model.user.MedalBean;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xudengwang on 17/8/13.
 */

public class TestMedalAdapter extends BaseCommonAdapter<MedalBean> {
    public TestMedalAdapter(Context context, List<MedalBean> datas) {
        super(context, R.layout.item_for_test_medal, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MedalBean medalBean, int position) {

    }
}
