package com.android.ge.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.ge.controller.adapter.Itemviewdelegate.CourseItemViewDelegate;
import com.android.ge.controller.adapter.Itemviewdelegate.CourseTypeItemViewDelegate;
import com.android.ge.model.BaseCourseTypeInfo;
import com.android.ge.model.learning.BaseLearningItem;
import com.android.ge.model.learning.TitleItemInfo;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xudengwang on 17/3/21.
 */

public class LearningCourseTypeAdapter extends MultiItemTypeAdapter<BaseLearningItem> implements MultiItemTypeAdapter.OnItemClickListener{

    private List<BaseLearningItem> mDatas = new ArrayList<>();

    public LearningCourseTypeAdapter(Context context, List<BaseLearningItem> datas) {
        super(context, datas);
        addItemViewDelegate(new CourseItemViewDelegate());
        addItemViewDelegate(new CourseTypeItemViewDelegate());
        setOnItemClickListener(this);
    }

    @Override
    public void convert(ViewHolder holder, BaseLearningItem item) {


        super.convert(holder, item);
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;

    }
}
