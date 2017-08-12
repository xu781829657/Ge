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
import com.android.ge.model.HomePageInfo;
import com.android.ge.model.path.PathBean;
import com.android.ge.model.path.PathListInfo;
import com.android.ge.model.path.PathResultInfo;
import com.android.ge.model.task.TaskBean;
import com.android.ge.model.task.TaskListInfo;
import com.android.ge.model.task.TaskListResultInfo;
import com.android.ge.network.Network;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.network.response.ServerResponseFunc;
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

    @Bind(R.id.rel_classify)
    RelativeLayout mRlPathClassify;
    @Bind(R.id.tv_more)
    TextView mTvMore;

    @Bind(R.id.tv_type_title)
    TextView mTvTypeTitle;

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
       // EventBus.getDefault().register(this);

        TAB_FLAG = getArguments().getInt(CommonConstant.KEY_TAB_FALG);
        if (TAB_FLAG == TaskFragment.TAB_UNSTART) {
            mArgState = "ready";

        } else if (TAB_FLAG == TaskFragment.TAB_ONGOING) {
            mArgState = "progress";

        } else if (TAB_FLAG == TaskFragment.TAB_FINISHED) {
            mArgState = "finish";

        }
        mTvContent.setText(TAB_FLAG + "");
        mTvTypeTitle.setText(Base.string(R.string.title_learning_path));
        mTvMore.setVisibility(View.GONE);

    }

    @Override
    public void onResume() {
        super.onResume();
        getNetDataTaskList();
        getNetDataLearningPath();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
 //       EventBus.getDefault().unregister(this);
    }

//    private void initFalseData() {
//        for (int i = 0; i < 5; i++) {
//            TaskBean bean = new TaskBean();
//            mTasks.add(bean);
//        }
//        refreshTaskAdapter();
//    }

    private void refreshTaskData(TaskListInfo info) {
        mTasks.clear();
        if (info.missions.size() > 0) {
            mTasks.addAll(info.missions);
        }
        refreshTaskAdapter();
    }

    private void refreshTaskAdapter() {
        if (mTaskListAdapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(getMContext());
            mRvTask.setLayoutManager(manager);
            mRvTask.setHasFixedSize(true);
            mRvTask.setNestedScrollingEnabled(false);
            mTaskListAdapter = new TaskListAdapter(getMContext(), mTasks, TAB_FLAG);
            mRvTask.setAdapter(mTaskListAdapter);
        } else {
            mTaskListAdapter.notifyDataSetChanged();
        }
    }

    private void refreshPathData(PathListInfo info) {
        LogUtils.d(getClass(), "refreshPathData info.data.size():" + info.learningpath.size());
        if (info == null) {
            return;
        }
        if (info.learningpath.size() > 0) {
            mPaths.clear();
            mPaths.addAll(info.learningpath);
        }
        refreshPathAdapter();
    }

    private void refreshPathAdapter() {

        if (mPaths.size() > 0) {
            mRlPathClassify.setVisibility(View.VISIBLE);
            mRvPath.setVisibility(View.VISIBLE);
        } else {
            mRlPathClassify.setVisibility(View.GONE);
            //mRvPath.setVisibility(View.GONE);
            return;
        }

        if (mPathAdapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(getMContext());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            mRvPath.setLayoutManager(manager);
            mRvPath.setHasFixedSize(true);
            mRvPath.setNestedScrollingEnabled(false);
            mPathAdapter = new LearningPathAdapter(getMContext(), mPaths);
            mRvPath.setAdapter(mPathAdapter);
        } else {
            mPathAdapter.notifyDataSetChanged();
        }

    }

    //任务列表结果
    Observer<TaskListInfo> mTaskObserver = new Observer<TaskListInfo>() {
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
        public void onNext(TaskListInfo resultInfo) {
            if (resultInfo == null || resultInfo.missions == null) {
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
                //拦截服务器返回的错误
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ServerResponseFunc<TaskListInfo>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mTaskObserver);
    }

//    // 展示app更新
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void refreshPathData(PathEntry eventEntry) {
//        LogUtils.d(getClass(), "refreshPathData:");
//        if (eventEntry.pathListInfo != null) {
//            refreshPathData(eventEntry.pathListInfo);
//        }
//    }

    //学习路径结果
    Observer<PathListInfo> mPathObserver = new Observer<PathListInfo>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            LogUtils.d(getClass(), "observer course e.message:" + e.getMessage());
            e.printStackTrace();
            Base.showToast(ExceptionEngine.handleException(e, getActivity()).message);
        }

        @Override
        public void onNext(PathListInfo resultInfo) {
            if (resultInfo == null || resultInfo.learningpath == null) {
                Base.showToast(R.string.errmsg_data_error);
            }
            //EventBus.getDefault().post(new PathEntry(resultInfo));

            refreshPathData(resultInfo);

        }
    };

    //获取学习路径
    private void getNetDataLearningPath() {
        if (!NetworkUtil.isAvailable(getMContext())) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put(CommonConstant.PARAM_ORG_ID, Store.getOrganId());

        Network.getCourseApi("学习路径").getLearningPath(map)
                .subscribeOn(Schedulers.io())
                //拦截服务器返回的错误
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ServerResponseFunc<PathListInfo>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mPathObserver);
    }

}
