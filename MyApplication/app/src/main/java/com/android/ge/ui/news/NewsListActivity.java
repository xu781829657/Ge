package com.android.ge.ui.news;

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
import com.android.ge.controller.adapter.ClassifyCourseListAdapter;
import com.android.ge.controller.adapter.NewsListAdapter;
import com.android.ge.model.CourseClassifyInfo;
import com.android.ge.model.NewsBean;
import com.android.ge.network.Network;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.network.response.ServerResponseFunc;
import com.android.ge.ui.base.CommonBaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xudengwang on 17/8/1.
 * 资讯列表
 */

public class NewsListActivity extends CommonBaseActivity {
    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.rv_news)
    RecyclerView mRvNews;

    private NewsListAdapter mAdapter;
    private ArrayList<NewsBean> mNews;

    @Override
    protected int getContentViewId() {
        return R.layout.ac_news_list;
    }

    @Override
    protected void initData() {
        mContext = this;
        mTvTitle.setText(Base.string(R.string.title_news_list));
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void refreshAdapter() {
        if (mAdapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            mRvNews.setLayoutManager(manager);
            mRvNews.setHasFixedSize(true);

            mAdapter = new NewsListAdapter(mContext, mNews);
            mRvNews.setAdapter(mAdapter);

        } else {
            mAdapter.notifyDataSetChanged();
        }


    }

    //主页配置
    Observer<ArrayList<NewsBean>> mClassifyObserver = new Observer<ArrayList<NewsBean>>() {
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
        public void onNext(ArrayList<NewsBean> resultInfo) {
            if (resultInfo == null) {
                Base.showToast(R.string.errmsg_data_error);
                return;
            }


            refreshData(resultInfo);

        }
    };

    //获取主页配置
    private void getNetDataCourseClassifyInfo() {
        if (!NetworkUtil.isAvailable(mContext)) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }
        showLoadingDialog(null);
        Map<String, String> map = new HashMap<>();
        map.put(CommonConstant.PARAM_ORG_ID, Store.getOrganId());
        if (COURSE_TAG == mCourseFlag) {
            map.put(CommonConstant.PARAM_TYPE, "tag");
            map.put(CommonConstant.PARAM_TAG_ID, mArgCourseTypeId);
        } else if (COURSE_CLASSIFY == mCourseFlag) {
            map.put(CommonConstant.PARAM_TYPE, "cat");
            map.put(CommonConstant.PARAM_CAT_ID, mArgCourseTypeId);

        }


//        map.put(CommonConstant.PARAM_COURSE_TYPE_ID, mArgCourseTypeId);

        Network.getCourseApi("获取单个课程分类").getNewslist(map)
                .subscribeOn(Schedulers.io())
                //拦截服务器返回的错误
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ServerResponseFunc<ArrayList<NewsBean>>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mClassifyObserver);

    }
}
