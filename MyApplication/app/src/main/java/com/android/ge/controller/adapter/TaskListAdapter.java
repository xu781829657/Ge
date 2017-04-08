package com.android.ge.controller.adapter;

import android.content.Context;

import com.android.ge.R;
import com.android.ge.model.task.TaskBean;
import com.android.ge.widgets.view.RoundProgressBar;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xudengwang on 17/4/8.
 */

public class TaskListAdapter extends BaseCommonAdapter<TaskBean> {
    public TaskListAdapter(Context context, List<TaskBean> datas) {
        super(context, R.layout.item_for_task_list, datas);
    }

    @Override
    public void convert(ViewHolder holder, TaskBean taskBean, int position) {
        ((RoundProgressBar)holder.getView(R.id.rd_progressbar)).setProgress(80);
    }
}
