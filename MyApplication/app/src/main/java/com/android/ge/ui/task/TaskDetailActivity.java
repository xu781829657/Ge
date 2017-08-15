package com.android.ge.ui.task;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.controller.Store;
import com.android.ge.controller.adapter.TaskDetailAdapter;
import com.android.ge.controller.adapter.TaskListAdapter;
import com.android.ge.controller.entry.PathEntry;
import com.android.ge.model.path.PathListInfo;
import com.android.ge.model.task.TaskBean;
import com.android.ge.model.task.TaskCourseBean;
import com.android.ge.model.task.TaskDetailBean;
import com.android.ge.model.task.TaskDetailInfo;
import com.android.ge.network.Network;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.network.response.ServerResponseFunc;
import com.android.ge.ui.base.CommonBaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    private List<TaskDetailBean> mTaskDetails = new ArrayList<>();
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
//                if (mTaskBean.courses != null && mTaskBean.courses.size() > 0) {
//                    mTaskDetails.addAll(mTaskBean.courses);
//                    refreshTaskAdapter();
//                }

                getNetDataTaskDetail();
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
            mAdapter = new TaskDetailAdapter(mContext, mTaskDetails,mTaskBean);
            mRvTaskDetail.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }


    }


    //学习路径结果
    Observer<TaskDetailInfo> mTaskDetailObserver = new Observer<TaskDetailInfo>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            LogUtils.d(getClass(), "observer course e.message:" + e.getMessage());
            e.printStackTrace();
            Base.showToast(ExceptionEngine.handleException(e).message);
        }

        @Override
        public void onNext(TaskDetailInfo resultInfo) {
            if (resultInfo == null || resultInfo.missions_details == null) {
                Base.showToast(R.string.errmsg_data_error);
            }
            mTaskDetails.clear();
            mTaskDetails.addAll(resultInfo.missions_details);
            refreshTaskAdapter();
            //EventBus.getDefault().post(new PathEntry(resultInfo));
        }
    };

    //获取学习路径
    private void getNetDataTaskDetail() {
        if (!NetworkUtil.isAvailable(mContext)) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put(CommonConstant.PARAM_ID, mTaskBean.getId());

        Network.getCourseApi("任务详情").getTaskDetailInfo(map)
                .subscribeOn(Schedulers.io())
                //拦截服务器返回的错误
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ServerResponseFunc<TaskDetailInfo>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mTaskDetailObserver);
    }
}
