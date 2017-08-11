package com.android.ge.ui.course;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.controller.Store;
import com.android.ge.controller.adapter.ClassifyCourseListAdapter;
import com.android.ge.model.CourseBean;
import com.android.ge.model.CourseClassifyInfo;
import com.android.ge.model.CourseClassifyResultInfo;
import com.android.ge.model.task.TaskListInfo;
import com.android.ge.network.Network;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.network.response.ServerResponseFunc;
import com.android.ge.ui.base.CommonBaseActivity;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xudengwang on 17/4/5.
 */

public class ClassifyCourseListActivity extends CommonBaseActivity {

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.rv_course)
    RecyclerView mRvCourse;

    @Bind(R.id.view_refresh)
    TwinklingRefreshLayout mViewRefresh;

    private List<CourseBean> mCourses = new ArrayList<>();
    private ClassifyCourseListAdapter mAdapter;

    private String mArgCourseTypeId;//课程类型id
    private String mTitle;
    private int mCourseFlag;
    private int mParamPage = 0;

    public static final int COURSE_TAG = 0;
    public static final int COURSE_CLASSIFY = 1;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_course_list;
    }

    @Override
    protected void initData() {
        mContext = this;
        mTvTitle.setText(Base.string(R.string.title_course_classify));
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        initFalseData();
//        refreshAdapter();
        getBundleArgs();
        setRefreshListener();
        showLoadingDialog(null);
        getNetDataCourseClassifyInfo();
    }

    private void getBundleArgs() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mArgCourseTypeId = bundle.getString(CommonConstant.KEY_COURSE_TYPE_ID);
            mTitle = bundle.getString(CommonConstant.KEY_TITLE);
            mCourseFlag = bundle.getInt(CommonConstant.KEY_COURSE_TYPE);
        }

        if (!TextUtils.isEmpty(mTitle)) {
            mTvTitle.setText(mTitle);
        }

    }

    //
    private void setRefreshListener() {
//        ProgressLayout headerView = new ProgressLayout(this);
//        mViewRefresh.setHeaderView(headerView);
        SinaRefreshView headerView = new SinaRefreshView(this);
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
//        TextHeaderView headerView = (TextHeaderView) View.inflate(this,R.layout.header_tv,null);
        mViewRefresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(this);
        mViewRefresh.setBottomView(loadingView);
        mViewRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                mParamPage = 1;
                getNetDataCourseClassifyInfo();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                mParamPage++;
                getNetDataCourseClassifyInfo();
            }
        });
    }

    private void finishRefreshState() {
        mViewRefresh.finishRefreshing();
        mViewRefresh.finishLoadmore();
    }

    private void refreshAdapter() {
        if (mAdapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            mRvCourse.setLayoutManager(manager);
            mRvCourse.setHasFixedSize(true);

            mAdapter = new ClassifyCourseListAdapter(mContext, mCourses);
            mRvCourse.setAdapter(mAdapter);

        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    //主页配置
    Observer<CourseClassifyInfo> mClassifyObserver = new Observer<CourseClassifyInfo>() {
        @Override
        public void onCompleted() {
            dismissLoadingDialog();
            finishRefreshState();
        }

        @Override
        public void onError(Throwable e) {
            LogUtils.d(getClass(), "observer course e.message:" + e.getMessage());
            e.printStackTrace();
            Base.showToast(ExceptionEngine.handleException(e).message);
            dismissLoadingDialog();
            if (mParamPage > 0) {
                mParamPage--;
            }
            finishRefreshState();
        }

        @Override
        public void onNext(CourseClassifyInfo resultInfo) {
            if (resultInfo == null || resultInfo.courses == null) {
                Base.showToast(R.string.errmsg_data_error);
                return;
            }
            if (resultInfo.courses.size() == 0) {
                mViewRefresh.setEnableLoadmore(false);
                return;
            }
            mViewRefresh.setEnableLoadmore(true);
            if (mParamPage == 1) {
                mCourses.clear();
            }
            mCourses.addAll(resultInfo.courses);
            refreshAdapter();
        }
    };

    //获取主页配置
    private void getNetDataCourseClassifyInfo() {
        if (!NetworkUtil.isAvailable(mContext)) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put(CommonConstant.PARAM_ORG_ID, Store.getOrganId());
        if (COURSE_TAG == mCourseFlag) {
            map.put(CommonConstant.PARAM_TYPE, "tag");
            map.put(CommonConstant.PARAM_TAG_ID, mArgCourseTypeId);
        } else if (COURSE_CLASSIFY == mCourseFlag) {
            map.put(CommonConstant.PARAM_TYPE, "cat");
            map.put(CommonConstant.PARAM_CAT_ID, mArgCourseTypeId);
        }
        map.put(CommonConstant.PARAM_PAGE, mParamPage + "");
//        map.put(CommonConstant.PARAM_COURSE_TYPE_ID, mArgCourseTypeId);

        Network.getCourseApi("获取单个课程分类").getCourseClassify(map)
                .subscribeOn(Schedulers.io())
                //拦截服务器返回的错误
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ServerResponseFunc<CourseClassifyInfo>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mClassifyObserver);

    }


}
