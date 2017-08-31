package com.android.ge.controller.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.base.frame.Base;
import com.android.base.util.ScreenUtils;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.model.CourseBean;
import com.android.ge.model.path.PathBean;
import com.android.ge.model.task.TaskBean;
import com.android.ge.ui.customview.PathContentInfoView;
import com.android.ge.ui.webview.CourseWebActivity;
import com.android.ge.ui.webview.PathWebActivity;
import com.android.ge.widgets.view.RoundProgressBar;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xudengwang on 17/4/8.
 */

public class LearningPathAdapter extends BaseCommonAdapter<PathBean> implements MultiItemTypeAdapter.OnItemClickListener {
    public LearningPathAdapter(Context context, List<PathBean> datas) {
        super(context, R.layout.item_for_learning_path, datas);
        setOnItemClickListener(this);
    }

    @Override
    public void convert(ViewHolder holder, PathBean pathBean, int position) {

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.getView(R.id.rel_cover).getLayoutParams();
        params.width = (int) (ScreenUtils.getScreenWidth(mContext) - 34 * ScreenUtils.getScreenDensity(mContext));
        params.height = (int) params.width * 140 / 340;
        holder.getView(R.id.rel_cover).setLayoutParams(params);

        holder.setText(R.id.tv_path_title, pathBean.getTitle());
        holder.setText(R.id.tv_path_progress, String.format(Base.string(R.string.format_progress), pathBean.getProgress()) + "%");

        holder.setText(R.id.tv_course_count, String.format(Base.string(R.string.format_course_course_count), pathBean.getCourses_total()));
        holder.setText(R.id.tv_test_count, String.format(Base.string(R.string.format_course_quiz_count), pathBean.getExamination_total()));
        holder.setText(R.id.tv_survey_count, String.format(Base.string(R.string.format_course_survey_count), pathBean.getQuestionnaire_total()));
        setImageFromInternet((ImageView) holder.getView(R.id.iv_path_cover), pathBean.getCover(), R.drawable.demo_course_loading_icon);
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        PathBean pathBean = mDatas.get(position);
        Bundle bundle = new Bundle();
        bundle.putString(CommonConstant.PARAM_PATH_ID, pathBean.getId());
        bundle.putString(CommonConstant.PARAM_ENTRY_TYPE, CommonConstant.TYPE_LEARNINGPATH);
        bundle.putString(CommonConstant.PARAM_ENTRY_ID, pathBean.getId());
        bundle.putString(CommonConstant.PARAM_CX,pathBean.getCx());
        gotoActivity(CourseWebActivity.class, bundle);
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

}
