package com.android.ge.controller.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xudengwang on 17/3/21.
 */

public class LearningCourseTypeAdapter extends MultiItemTypeAdapter {

    public LearningCourseTypeAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    public void convert(ViewHolder holder, Object o) {
        super.convert(holder, o);
    }


}
