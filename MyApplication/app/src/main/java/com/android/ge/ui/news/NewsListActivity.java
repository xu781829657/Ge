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
import com.android.ge.controller.adapter.NewsListAdapter;
import com.android.ge.model.NewsBean;
import com.android.ge.model.NewsResultInfo;
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
    private ArrayList<NewsBean> mNews = new ArrayList<>();

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

        getNetDataNewsList();
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
    Observer<NewsResultInfo> mNewsObserver = new Observer<NewsResultInfo>() {
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
        public void onNext(NewsResultInfo resultInfo) {
            if (resultInfo == null) {
                Base.showToast(R.string.errmsg_data_error);
                return;
            }
            if (resultInfo.news != null && resultInfo.news.size() > 0) {
                mNews.clear();
                mNews.addAll(resultInfo.news);
                refreshAdapter();
            }

        }
    };

    //获取主页配置
    private void getNetDataNewsList() {
        if (!NetworkUtil.isAvailable(mContext)) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }
        showLoadingDialog(null);
        Map<String, String> map = new HashMap<>();
        map.put(CommonConstant.PARAM_ORG_ID, Store.getOrganId());

        Network.getCourseApi("获取资讯列表").getNewslist(map)
                .subscribeOn(Schedulers.io())
                //拦截服务器返回的错误
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ServerResponseFunc<NewsResultInfo>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mNewsObserver);

    }
}
