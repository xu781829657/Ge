package com.android.ge.controller.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.model.task.TaskBean;
import com.android.ge.model.task.TaskCourseBean;
import com.android.ge.ui.customview.TaskContentInfoView;
import com.android.ge.ui.webview.CourseWebActivity;
import com.android.ge.widgets.view.RoundProgressBar;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xudengwang on 17/4/8.
 */

public class TaskDetailAdapter extends BaseCommonAdapter<TaskCourseBean> implements MultiItemTypeAdapter.OnItemClickListener {
    public TaskDetailAdapter(Context context, List<TaskCourseBean> datas) {
        super(context, R.layout.item_for_task_detail, datas);
        setOnItemClickListener(this);
    }

    @Override
    public void convert(ViewHolder holder, TaskCourseBean taskBean, int position) {
//        ((RoundProgressBar) holder.getView(R.id.rd_progressbar)).setProgress(80);
//        holder.setText(R.id.tv_task_name, taskBean.getTitle());
        // ((TaskContentInfoView) holder.getView(R.id.view_task_content_info)).setTaskBean(taskBean);

        if (CommonConstant.TASK_COURSE_TYPE_COURSE.equalsIgnoreCase(taskBean.getType())) {
            holder.setText(R.id.tv_task_course_type, "类型: 课件");
        } else if (CommonConstant.TASK_COURSE_TYPE_QUIZ.equalsIgnoreCase(taskBean.getType())) {
            holder.setText(R.id.tv_task_course_type, "类型: 考试");
        } else if (CommonConstant.TASK_COURSE_TYPE_SURVEY.equalsIgnoreCase(taskBean.getType())) {
            holder.setText(R.id.tv_task_course_type, "类型: 问卷");
        }
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        TaskCourseBean taskCourseBean = mDatas.get(position);
        if (CommonConstant.TASK_COURSE_TYPE_COURSE.equalsIgnoreCase(taskCourseBean.getType())) {
            Bundle bundle = new Bundle();
            bundle.putString(CommonConstant.PARAM_COURSE_ID, taskCourseBean.getId() + "");
            gotoActivity(CourseWebActivity.class, bundle);
        }

    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
