package com.android.ge.ui.task;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.controller.adapter.TaskListAdapter;
import com.android.ge.model.task.TaskBean;
import com.android.ge.ui.base.CommonBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by xudengwang on 17/4/7.
 */

public class TaskListFragment extends CommonBaseFragment {
    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.rv_task)
    RecyclerView mRvTask;
    @Bind(R.id.rv_path)
    RecyclerView mRvPath;
    private int TAB_FLAG;

    private TaskListAdapter mTaskListAdapter;
    private List<TaskBean> mTasks = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.fm_task_list;
    }

    @Override
    protected void initData() {
        TAB_FLAG = getArguments().getInt(CommonConstant.KEY_TAB_FALG);
        mTvContent.setText(TAB_FLAG + "");
        initFalseData();

    }

    private void initFalseData() {
        for (int i = 0; i < 5; i++) {
            TaskBean bean = new TaskBean();
            mTasks.add(bean);
        }
        refreshTaskAdapter();
    }

    private void refreshTaskAdapter() {
        if (mTaskListAdapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(getMContext());
            mRvTask.setLayoutManager(manager);
            mRvTask.setHasFixedSize(true);
            mTaskListAdapter = new TaskListAdapter(getMContext(), mTasks);
            mRvTask.setAdapter(mTaskListAdapter);
        } else {
            mTaskListAdapter.notifyDataSetChanged();
        }

    }
}
