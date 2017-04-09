package com.android.ge.ui.task;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.controller.adapter.TaskDetailAdapter;
import com.android.ge.controller.adapter.TaskListAdapter;
import com.android.ge.model.task.TaskBean;
import com.android.ge.model.task.TaskCourseBean;
import com.android.ge.ui.base.CommonBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by xudengwang on 17/4/9.
 */

public class TaskDetailActivity extends CommonBaseActivity {
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.rv_task_detail)
    RecyclerView mRvTaskDetail;
    private List<TaskCourseBean> mTaskCourseBeans = new ArrayList<>();
    private TaskDetailAdapter mAdapter;
    private TaskBean mTaskBean;

    @Override
    protected void initData() {
        mContext = this;
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getBundleInfo();

    }

    private void getBundleInfo() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mTaskBean = (TaskBean) bundle.getSerializable(CommonConstant.KEY_TASK_BEAN);
            if (mTaskBean != null) {
                mTvTitle.setText(mTaskBean.getTitle());
                if (mTaskBean.courses != null && mTaskBean.courses.size() > 0) {
                    mTaskCourseBeans.addAll(mTaskBean.courses);
                    refreshTaskAdapter();
                }
            }
        }

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_task_detail;
    }

    private void refreshTaskAdapter() {
        if (mAdapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            mRvTaskDetail.setLayoutManager(manager);
            mRvTaskDetail.setHasFixedSize(true);

            mAdapter = new TaskDetailAdapter(mContext, mTaskCourseBeans);
            mRvTaskDetail.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }


    }
}
