package com.android.ge.controller.adapter;

import android.content.Context;

import com.android.ge.R;
import com.android.ge.model.task.TaskBean;
import com.android.ge.model.task.TaskCourseBean;
import com.android.ge.ui.customview.TaskContentInfoView;
import com.android.ge.widgets.view.RoundProgressBar;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xudengwang on 17/4/8.
 */

public class TaskDetailAdapter extends BaseCommonAdapter<TaskCourseBean> {
    public TaskDetailAdapter(Context context, List<TaskCourseBean> datas) {
        super(context, R.layout.item_for_task_detail, datas);
    }

    @Override
    public void convert(ViewHolder holder, TaskCourseBean taskBean, int position) {
//        ((RoundProgressBar) holder.getView(R.id.rd_progressbar)).setProgress(80);
//        holder.setText(R.id.tv_task_name, taskBean.getTitle());
       // ((TaskContentInfoView) holder.getView(R.id.view_task_content_info)).setTaskBean(taskBean);
    }
}
