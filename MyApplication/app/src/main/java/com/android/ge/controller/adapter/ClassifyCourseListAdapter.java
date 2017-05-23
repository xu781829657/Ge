package com.android.ge.controller.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.model.CourseBean;
import com.android.ge.ui.customview.CourseContentInfoView;
import com.android.ge.ui.webview.CourseWebActivity;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xudengwang on 17/4/8.
 */

public class ClassifyCourseListAdapter extends BaseCommonAdapter<CourseBean> implements MultiItemTypeAdapter.OnItemClickListener{
    public ClassifyCourseListAdapter(Context context, List<CourseBean> datas) {
        super(context, R.layout.item_for_classify_course_list, datas);
        setOnItemClickListener(this);
    }

    @Override
    protected void convert(ViewHolder holder, CourseBean courseBean, int position) {
        holder.setText(R.id.tv_course_title, courseBean.getTitle());
        ((CourseContentInfoView) holder.getView(R.id.view_course_content_info)).setCourseBean(courseBean);
        if(!TextUtils.isEmpty(courseBean.getCover())){
            setImageFromInternet((ImageView) holder.getView(R.id.iv_course_cover), courseBean.getCover(), R.drawable.questionnaire_loading_icon);
        } else {
            holder.setImageResource(R.id.iv_course_cover,R.drawable.questionnaire_loading_icon);
        }


    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        CourseBean courseBean = mDatas.get(position);
        Bundle bundle = new Bundle();
        bundle.putString(CommonConstant.PARAM_COURSE_ID,courseBean.getId());
        gotoActivity(CourseWebActivity.class, bundle);
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
