package com.android.ge.ui.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.controller.adapter.LearningPathAdapter;
import com.android.ge.controller.adapter.TaskDetailAdapter;
import com.android.ge.model.path.PathBean;
import com.android.ge.model.task.TaskBean;
import com.android.ge.model.task.TaskCourseBean;
import com.android.ge.ui.base.CommonBaseActivity;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by xudengwang on 17/4/9.
 */

public class PathListActivity extends CommonBaseActivity {
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.rv_task_detail)
    RecyclerView mRvTaskDetail;
    private List<PathBean> mPaths = new ArrayList<>();
    private LearningPathAdapter mAdapter;
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
        mTvTitle.setText(Base.string(R.string.title_learning_path));
        getExtraInfo();
    }

    private void getExtraInfo() {

        List<PathBean> paths = (List<PathBean>) getIntent().getSerializableExtra(CommonConstant.KEY_PATH_BEAN_LIST);
        if (paths != null && paths.size() > 0) {
            mPaths.addAll(paths);
            refreshPathAdapter();
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_task_detail;
    }

    private void refreshPathAdapter() {
        if (mAdapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            mRvTaskDetail.setLayoutManager(manager);
            mRvTaskDetail.setHasFixedSize(true);
            mAdapter = new LearningPathAdapter(mContext, mPaths);
            mRvTaskDetail.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }


    }
}
