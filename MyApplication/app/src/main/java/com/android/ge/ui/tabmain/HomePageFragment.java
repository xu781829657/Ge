package com.android.ge.ui.tabmain;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.controller.Store;
import com.android.ge.controller.adapter.NewsAdapter;
import com.android.ge.controller.adapter.RecommandAdapter;
import com.android.ge.controller.adapter.RequiredAdapter;
import com.android.ge.controller.entry.BannerEntry;
import com.android.ge.model.GalleryBean;
import com.android.ge.model.HomePageInfo;
import com.android.ge.model.NewsBean;
import com.android.ge.model.RecommandInfo;
import com.android.ge.model.RequiredInfo;
import com.android.ge.network.Network;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.network.response.ServerResponseFunc;
import com.android.ge.ui.base.CommonBaseFragment;
import com.android.ge.ui.course.ClassifyCourseListActivity;
import com.android.ge.ui.news.NewsListActivity;
import com.android.ge.utils.image.GlideImageLoader;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xudengwang on 17/3/18.
 */

public class HomePageFragment extends CommonBaseFragment {
    @Bind(R.id.banner)
    Banner mBanner;

    @Bind(R.id.rv_news)
    RecyclerView mRvNews;

    @Bind(R.id.rv_required_courses)
    RecyclerView mRvRequires;
    @Bind(R.id.rv_recommand_courses)
    RecyclerView mRvRecommand;

    private NewsAdapter mNewsAdapter;
    private RequiredAdapter mRequiredAdapter;
    private RecommandAdapter mRecommandAdapter;
    private ArrayList<NewsBean> mNews = new ArrayList<>();
    private List<String> mBannerImageUrls = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.fm_homepage;
    }

    @Override
    protected void initData() {


    }

    //轮播图更新及初始化
    private void refreshBanner(List<GalleryBean> galleryBeanList) {
        if (galleryBeanList == null || galleryBeanList.size() == 0) {
            mBanner.setVisibility(View.GONE);
            return;
        } else {
            mBannerImageUrls.clear();
            for (int i = 0; i < galleryBeanList.size(); i++) {
                mBannerImageUrls.add(galleryBeanList.get(i).getImage_url());
            }
        }
        //EventBus.getDefault().post(new BannerEntry(mBannerImageUrls));
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(mBannerImageUrls);
        mBanner.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
//        //设置banner样式
//        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
//        //设置图片加载器
//        mBanner.setImageLoader(new GlideImageLoader());
//        //设置图片集合
//        mBanner.setImages(mBannerImageUrls);
//        //设置banner动画效果
//        mBanner.setBannerAnimation(Transformer.DepthPage);
//        //设置标题集合（当banner样式有显示title时）
//        //mBanner.setBannerTitles(titles);
//        //设置自动轮播，默认为true
//        mBanner.isAutoPlay(true);
//        //设置轮播时间
//        mBanner.setDelayTime(1500);
//        //设置指示器位置（当banner模式中有指示器时）
//        mBanner.setIndicatorGravity(BannerConfig.CENTER);
//        //banner设置方法全部调用完毕时最后调用
//        mBanner.start();


    }

    @Override
    public void onResume() {
        super.onResume();
        getNetDataHomePageConfig();
    }

    private void refreshMainData(HomePageInfo homePageInfo) {
        refreshBanner(homePageInfo.advertises);
        refreshNewsAdapter(homePageInfo.news);
        refreshRecommandAdapter(homePageInfo.recommand);
        refreshRequiredAdapter(homePageInfo.required);
    }


    //刷新最新资讯
    private void refreshNewsAdapter(List<NewsBean> news) {
        if (news != null && news.size() > 0) {
            mNews.clear();
            mNews.addAll(news);
            mRvNews.setVisibility(View.VISIBLE);
        } else {
            mRvNews.setVisibility(View.GONE);
            return;
        }

        if (mNewsAdapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(getMContext());
            mRvNews.setLayoutManager(manager);
            mRvNews.setHasFixedSize(true);
            mRvNews.setNestedScrollingEnabled(false);
            RecyclerViewHeader header = RecyclerViewHeader.fromXml(getMContext(), R.layout.view_rv_header);
            TextView tv_title = (TextView) header.findViewById(R.id.tv_type_title);
            tv_title.setText(Base.string(R.string.title_news));
            RelativeLayout relClassify = (RelativeLayout) header.findViewById(R.id.rel_classify);
            relClassify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoActivity(NewsListActivity.class, false);
                }
            });

            header.attachTo(mRvNews);
            mNewsAdapter = new NewsAdapter(getMContext(), mNews);
            mRvNews.setAdapter(mNewsAdapter);
        } else {
            mNewsAdapter.notifyDataSetChanged();
        }

    }

    //刷新必读课
    private void refreshRequiredAdapter(final RequiredInfo info) {

        if (info == null || info.getCourses() == null || info.getCourses().size() == 0) {
            mRvRequires.setVisibility(View.GONE);
            return;
        } else {
            mRvRequires.setVisibility(View.VISIBLE);
        }
        if (mRequiredAdapter == null) {
            GridLayoutManager manager = new GridLayoutManager(getMContext(), 2);
            manager.setOrientation(GridLayoutManager.VERTICAL);
            mRvRequires.setLayoutManager(manager);
            mRvRequires.setHasFixedSize(true);
            mRvRequires.setNestedScrollingEnabled(false);
//            mRvRequires.addItemDecoration(new RecycleViewDivider(
//                    getMContext(), GridLayoutManager.HORIZONTAL, (int) (ScreenUtils.getScreenDensity(getMContext()) * 10), getResources().getColor(R.color.white)));
            RecyclerViewHeader header = RecyclerViewHeader.fromXml(getMContext(), R.layout.view_rv_header);
            TextView tv_title = (TextView) header.findViewById(R.id.tv_type_title);
            tv_title.setText(info.getTitle());
            RelativeLayout relClassify = (RelativeLayout) header.findViewById(R.id.rel_classify);
            relClassify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(CommonConstant.KEY_COURSE_TYPE, ClassifyCourseListActivity.COURSE_TAG);
                    bundle.putString(CommonConstant.KEY_COURSE_TYPE_ID, info.getId());
                    bundle.putString(CommonConstant.KEY_TITLE, info.getTitle());
                    gotoActivity(ClassifyCourseListActivity.class, bundle, false);
                }
            });
            header.attachTo(mRvRequires);
            mRequiredAdapter = new RequiredAdapter(getMContext(), info.getCourses());
            mRvRequires.setAdapter(mRequiredAdapter);
        } else {
            mRequiredAdapter.notifyDataSetChanged();
        }

    }

    //刷新精品推荐
    private void refreshRecommandAdapter(final RecommandInfo info) {

        if (info == null || info.getCourses() == null || info.getCourses().size() == 0) {
            mRvRecommand.setVisibility(View.GONE);
            return;
        } else {
            mRvRecommand.setVisibility(View.VISIBLE);
        }

        if (mRecommandAdapter == null) {
            GridLayoutManager manager = new GridLayoutManager(getMContext(), 2);
            manager.setOrientation(GridLayoutManager.VERTICAL);
            mRvRecommand.setLayoutManager(manager);
            mRvRecommand.setHasFixedSize(true);
            mRvRecommand.setNestedScrollingEnabled(false);
//            mRvRecommand.addItemDecoration(new RecycleViewDivider(
//                    getMContext(), GridLayoutManager.HORIZONTAL, (int) (ScreenUtils.getScreenDensity(getMContext()) * 10), getResources().getColor(R.color.white)));
            RecyclerViewHeader header = RecyclerViewHeader.fromXml(getMContext(), R.layout.view_rv_header);
            TextView tv_title = (TextView) header.findViewById(R.id.tv_type_title);
            tv_title.setText(info.getTitle());
            RelativeLayout relClassify = (RelativeLayout) header.findViewById(R.id.rel_classify);
            relClassify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(CommonConstant.KEY_COURSE_TYPE_ID, info.getId());
                    bundle.putInt(CommonConstant.KEY_COURSE_TYPE, ClassifyCourseListActivity.COURSE_TAG);
                    bundle.putString(CommonConstant.KEY_TITLE, info.getTitle());
                    gotoActivity(ClassifyCourseListActivity.class, bundle, false);
                }
            });
            header.attachTo(mRvRecommand);
            mRecommandAdapter = new RecommandAdapter(getMContext(), info.getCourses());
            mRvRecommand.setAdapter(mRecommandAdapter);
        } else {
            mRecommandAdapter.notifyDataSetChanged();
        }

    }


    //主页配置
    Observer<HomePageInfo> mHomePageObserver = new Observer<HomePageInfo>() {
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
        public void onNext(HomePageInfo resultInfo) {
            if (resultInfo != null) {
                refreshMainData(resultInfo);
            }

        }
    };

    //获取主页配置
    private void getNetDataHomePageConfig() {
        if (!NetworkUtil.isAvailable(getMContext())) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put(CommonConstant.PARAM_ORG_ID, Store.getOrganId());

        Network.getCourseApi("tab_首页").getHomePageConfig(map)
                .subscribeOn(Schedulers.io())
                //拦截服务器返回的错误
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ServerResponseFunc<HomePageInfo>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mHomePageObserver);
    }
}
