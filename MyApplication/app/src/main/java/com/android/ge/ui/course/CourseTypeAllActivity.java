package com.android.ge.ui.course;

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
import com.android.ge.controller.adapter.CourseTypeAllAdapter;
import com.android.ge.model.CourseTypeDetailInfo;
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
 * Created by xudengwang on 17/6/25.
 * tab学习页:课程分类
 */

public class CourseTypeAllActivity extends CommonBaseActivity {
    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.rv_course_type_all)
    RecyclerView mRvCourseTypeAll;

    private CourseTypeAllAdapter mAdapter;
    private List<CourseTypeDetailInfo> mCourseTypes = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_course_type_all;
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
        getNetDataCourseTypeAll();
        refreshAdapter();
    }


//    private void refreshData(CourseClassifyInfo resultInfo) {
//        if (resultInfo.courses != null && resultInfo.courses.size() > 0) {
//            mCourseTypes.addAll(resultInfo.courses);
//        }
//        refreshAdapter();
//
//    }

    private void refreshAdapter() {
        if (mAdapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            mRvCourseTypeAll.setLayoutManager(manager);
            mRvCourseTypeAll.setHasFixedSize(true);
            mRvCourseTypeAll.setNestedScrollingEnabled(false);
            mAdapter = new CourseTypeAllAdapter(mContext, mCourseTypes);
            mRvCourseTypeAll.setAdapter(mAdapter);

        } else {
            mAdapter.notifyDataSetChanged();
        }


    }


    //主页配置
    Observer<ArrayList<CourseTypeDetailInfo>> mClassifyObserver = new Observer<ArrayList<CourseTypeDetailInfo>>() {
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
        public void onNext(ArrayList<CourseTypeDetailInfo> resultInfo) {
            if (resultInfo == null || resultInfo.size() == 0) {
                Base.showToast(R.string.errmsg_data_error);
                return;
            }
            mCourseTypes.clear();
            mCourseTypes.addAll(resultInfo);
            refreshAdapter();

            //refreshData(resultInfo);

        }
    };

    //获取主页配置
    private void getNetDataCourseTypeAll() {
        if (!NetworkUtil.isAvailable(mContext)) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }
        showLoadingDialog(null);
        Map<String, String> map = new HashMap<>();
        map.put(CommonConstant.PARAM_ORG_ID, Store.getOrganId());


//        map.put(CommonConstant.PARAM_COURSE_TYPE_ID, mArgCourseTypeId);

        Network.getCourseApi("获取全部课程分类").getCourseTypeAll(map)
                .subscribeOn(Schedulers.io())
                //拦截服务器返回的错误
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ServerResponseFunc<ArrayList<CourseTypeDetailInfo>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mClassifyObserver);

    }
}
