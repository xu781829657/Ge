package com.android.ge.ui.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.controller.Store;
import com.android.ge.controller.adapter.LearningPathAdapter;
import com.android.ge.controller.adapter.TaskListAdapter;
import com.android.ge.controller.entry.PathEntry;
import com.android.ge.model.path.PathBean;
import com.android.ge.model.path.PathResultInfo;
import com.android.ge.model.task.TaskBean;
import com.android.ge.model.task.TaskListResultInfo;
import com.android.ge.network.Network;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.ui.base.CommonBaseFragment;
import com.android.ge.ui.tabmain.TaskFragment;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    private LearningPathAdapter mPathAdapter;
    private List<PathBean> mPaths = new ArrayList<>();

    private String mArgState;

    @Override
    public int getContentViewId() {
        return R.layout.fm_task_list;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        TAB_FLAG = getArguments().getInt(CommonConstant.KEY_TAB_FALG);
        if (TAB_FLAG == TaskFragment.TAB_UNSTART) {
            mArgState = "unstart";

        } else if (TAB_FLAG == TaskFragment.TAB_ONGOING) {
            mArgState = "going";

        } else if (TAB_FLAG == TaskFragment.TAB_FINISHED) {
            mArgState = "finish";

        }
        mTvContent.setText(TAB_FLAG + "");
        //initFalseData();

    }

    @Override
    public void onResume() {
        super.onResume();
        getNetDataTaskList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

//    private void initFalseData() {
//        for (int i = 0; i < 5; i++) {
//            TaskBean bean = new TaskBean();
//            mTasks.add(bean);
//        }
//        refreshTaskAdapter();
//    }

    private void refreshTaskData(TaskListResultInfo info) {
        mTasks.clear();
        if (info.data.size() > 0) {
            mTasks.addAll(info.data);
        }

        refreshTaskAdapter();
    }

    private void refreshTaskAdapter() {
        if (mTaskListAdapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(getMContext());
            mRvTask.setLayoutManager(manager);
            mRvTask.setHasFixedSize(true);
            mRvTask.setNestedScrollingEnabled(false);
            mTaskListAdapter = new TaskListAdapter(getMContext(), mTasks);
            mRvTask.setAdapter(mTaskListAdapter);
        } else {
            mTaskListAdapter.notifyDataSetChanged();
        }


    }

    private void refreshPathData(PathResultInfo info) {
        LogUtils.d(getClass(), "refreshPathData info.data.size():" + info.data.size());
        if (info.data.size() > 0) {
            mPaths.clear();
            mPaths.addAll(info.data);
        }
        refreshPathAdapter();
    }

    private void refreshPathAdapter() {
        if (mTaskListAdapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(getMContext());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            mRvPath.setLayoutManager(manager);
            mRvPath.setHasFixedSize(true);
            mRvPath.setNestedScrollingEnabled(false);

            RecyclerViewHeader header = RecyclerViewHeader.fromXml(getMContext(), R.layout.view_rv_header);
            TextView tv_title = (TextView) header.findViewById(R.id.tv_type_title);
            tv_title.setText(Base.string(R.string.title_learning_path));
            RelativeLayout relClassify = (RelativeLayout) header.findViewById(R.id.rel_classify);
            relClassify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getMContext(), PathListActivity.class);

                    intent.putExtra(CommonConstant.KEY_PATH_BEAN_LIST, (Serializable) mPaths);

                    startActivity(intent);

                }
            });
            header.attachTo(mRvPath);
            mPathAdapter = new LearningPathAdapter(getMContext(), mPaths);
            mRvPath.setAdapter(mPathAdapter);
        } else {
            mTaskListAdapter.notifyDataSetChanged();
        }

        if (mPaths.size() > 0) {
            mRvPath.setVisibility(View.VISIBLE);
        } else {
            mRvPath.setVisibility(View.GONE);
        }

    }

    //任务列表结果
    Observer<TaskListResultInfo> mTaskObserver = new Observer<TaskListResultInfo>() {
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
        public void onNext(TaskListResultInfo resultInfo) {
            if (resultInfo == null || resultInfo.data == null) {
                Base.showToast(R.string.errmsg_data_error);
            }
            refreshTaskData(resultInfo);


        }
    };

    //获取任务列表
    private void getNetDataTaskList() {
        if (!NetworkUtil.isAvailable(getMContext())) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put(CommonConstant.PARAM_ORG_ID, Store.getOrganId());
        map.put(CommonConstant.PARAM_COURSE_STATE, mArgState);

        Network.getCourseApi("任务列表" + mArgState).getTaskList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mTaskObserver);
    }


    // 展示app更新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshPathData(PathEntry eventEntry) {
        LogUtils.d(getClass(), "refreshPathData:");
        if (eventEntry.pathResultInfo != null) {
            refreshPathData(eventEntry.pathResultInfo);
        }

    }


}
