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
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

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
    @Bind(R.id.view_refresh)
    TwinklingRefreshLayout mViewRefresh;


    private NewsListAdapter mAdapter;
    private ArrayList<NewsBean> mNews = new ArrayList<>();
    private int mParamPage = 0;

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
        setRefreshListener();
        showLoadingDialog(null);
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

    private void setRefreshListener() {

        SinaRefreshView headerView = new SinaRefreshView(this);
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        mViewRefresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(this);
        mViewRefresh.setBottomView(loadingView);
        mViewRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                mParamPage = 1;
                getNetDataNewsList();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                mParamPage++;
                getNetDataNewsList();
            }
        });
    }

    private void finishRefreshState() {
        mViewRefresh.finishRefreshing();
        mViewRefresh.finishLoadmore();
    }

    //主页配置
    Observer<NewsResultInfo> mNewsObserver = new Observer<NewsResultInfo>() {
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
        public void onNext(NewsResultInfo resultInfo) {
            if (resultInfo == null) {
                Base.showToast(R.string.errmsg_data_error);
                return;
            }

            if (resultInfo.news.size() == 0) {
                mViewRefresh.setEnableLoadmore(false);
                return;
            }
            mViewRefresh.setEnableLoadmore(true);
            if (mParamPage == 1) {
                mNews.clear();
            }
            mNews.addAll(resultInfo.news);
            refreshAdapter();

        }
    };

    //获取主页配置
    private void getNetDataNewsList() {
        if (!NetworkUtil.isAvailable(mContext)) {
            Base.showToast(R.string.errmsg_network_unavailable);
            dismissLoadingDialog();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put(CommonConstant.PARAM_ORG_ID, Store.getOrganId());
        map.put(CommonConstant.PARAM_PAGE, mParamPage + "");

        Network.getCourseApi("获取资讯列表").getNewslist(map)
                .subscribeOn(Schedulers.io())
                //拦截服务器返回的错误
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ServerResponseFunc<NewsResultInfo>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mNewsObserver);

    }
}
