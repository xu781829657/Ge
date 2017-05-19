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
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.controller.Store;
import com.android.ge.controller.adapter.LearningPathAdapter;
import com.android.ge.controller.adapter.TaskDetailAdapter;
import com.android.ge.controller.entry.PathEntry;
import com.android.ge.model.path.PathBean;
import com.android.ge.model.path.PathListInfo;
import com.android.ge.model.path.PathResultInfo;
import com.android.ge.model.task.TaskBean;
import com.android.ge.model.task.TaskCourseBean;
import com.android.ge.network.Network;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.network.response.ServerResponseFunc;
import com.android.ge.ui.base.CommonBaseActivity;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

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
        } else {
            getNetDataLearningPath();
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


    //学习路径结果
    Observer<PathListInfo> mPathObserver = new Observer<PathListInfo>() {
        @Override
        public void onCompleted() {
            dismissLoadingDialog();
        }

        @Override
        public void onError(Throwable e) {
            LogUtils.d(getClass(), "observer course e.message:" + e.getMessage());
            e.printStackTrace();
            Base.showToast(ExceptionEngine.handleException(e).message);
            dismissLoadingDialog();
        }

        @Override
        public void onNext(PathListInfo resultInfo) {
            if (resultInfo == null || resultInfo.learningpath == null) {
                Base.showToast(R.string.errmsg_data_error);
            }
            //EventBus.getDefault().post(new PathEntry(resultInfo));

            mPaths.addAll(resultInfo.learningpath);
            refreshPathAdapter();

        }
    };

    //获取学习路径
    private void getNetDataLearningPath() {
        if (!NetworkUtil.isAvailable(mContext)) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }
        showLoadingDialog(null);

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
