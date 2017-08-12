package com.android.ge.controller.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.base.frame.Base;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.model.task.TaskBean;
import com.android.ge.ui.customview.TaskContentInfoView;
import com.android.ge.ui.tabmain.TaskFragment;
import com.android.ge.ui.task.TaskDetailActivity;
import com.android.ge.utils.DateUtils;
import com.android.ge.widgets.view.RoundProgressBar;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xudengwang on 17/4/8.
 */

public class TaskListAdapter extends BaseCommonAdapter<TaskBean> implements MultiItemTypeAdapter.OnItemClickListener {
    private int mTabFlag = 0;//0待参加,1进行中的,2已完成

    public TaskListAdapter(Context context, List<TaskBean> datas, int tab_flag) {
        super(context, R.layout.item_for_task_list, datas);
        mTabFlag = tab_flag;
        setOnItemClickListener(this);
    }

    @Override
    public void convert(ViewHolder holder, TaskBean taskBean, int position) {
        if (TaskFragment.TAB_UNSTART == mTabFlag) {
            holder.setVisible(R.id.rd_progressbar, false);
        } else {
            holder.setVisible(R.id.rd_progressbar, true);
        }
        ((RoundProgressBar) holder.getView(R.id.rd_progressbar)).setProgress(taskBean.getProgress());
        if (taskBean.getProgress() == 100) {
            holder.getView(R.id.tv_complete).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.tv_complete).setVisibility(View.GONE);
        }
        holder.setText(R.id.tv_task_name, taskBean.getTitle());
        // holder.setText(R.id.tv_task_deadline_time, String.format(Base.string(R.string.format_deadline_time), DateUtils.getDateToStringCommon(taskBean.getDeadline_timestamp()*1000)));
        holder.setText(R.id.tv_task_deadline_time, String.format(Base.string(R.string.format_deadline_time), taskBean.getEnd_at()));
        ((TaskContentInfoView) holder.getView(R.id.view_task_content_info)).setTaskBean(taskBean);
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommonConstant.KEY_TASK_BEAN, mDatas.get(position));
        gotoActivity(TaskDetailActivity.class, bundle);
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
