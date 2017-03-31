package com.android.ge.ui.tabmain;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.base.util.ScreenUtils;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.controller.Store;
import com.android.ge.controller.adapter.NewsAdapter;
import com.android.ge.controller.adapter.RecommandAdapter;
import com.android.ge.controller.adapter.RequiredAdapter;
import com.android.ge.controller.diliver.RecycleViewDivider;
import com.android.ge.model.CourseBean;
import com.android.ge.model.HomePageResultInfo;
import com.android.ge.model.NewsBean;
import com.android.ge.model.RecommandInfo;
import com.android.ge.model.RequiredInfo;
import com.android.ge.model.login.LoginResultInfo;
import com.android.ge.network.Network;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.ui.base.CommonBaseFragment;
import com.android.ge.utils.PreferencesUtils;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xudengwang on 17/3/18.
 */

public class HomePageFragment extends CommonBaseFragment {
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

    @Override
    public int getContentViewId() {
        return R.layout.fm_homepage;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        refreshNewsAdapter();
        refreshRequiredAdapter(null);
        refreshRecommandAdapter(null);
        super.onResume();
    }

    private void refreshMainData(HomePageResultInfo info){
        

    }

    //刷新最新资讯
    private void refreshNewsAdapter() {
        if(mNews.size() == 0) {
            for (int i= 0;i<3;i++){
                NewsBean bean = new NewsBean();
                bean.setTitle("测试资讯"+i);
                mNews.add(bean);
            }
        }
        if (mNewsAdapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(getMContext());
            mRvNews.setLayoutManager(manager);
            mRvNews.setHasFixedSize(true);
            RecyclerViewHeader header = RecyclerViewHeader.fromXml(getMContext(), R.layout.view_rv_header);
            TextView tv_title = (TextView) header.findViewById(R.id.tv_type_title);
            tv_title.setText(Base.string(R.string.title_news));
            header.attachTo(mRvNews);
            mNewsAdapter = new NewsAdapter(getMContext(), mNews);
            mRvNews.setAdapter(mNewsAdapter);
        } else {
            mNewsAdapter.notifyDataSetChanged();
        }

    }

    //刷新必读课
    private void refreshRequiredAdapter(RequiredInfo info) {
        if (info == null) {
            info = new RequiredInfo();
            info.setTitle("光华学校必读课");
            info.setTotal_count(4);
            ArrayList<CourseBean> beans = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                CourseBean bean = new CourseBean();
                bean.setTitle("必读课" + i);
                bean.setDesc("课程描述" + i);
                beans.add(bean);
            }
            info.setCourses(beans);
        }
        if (mRequiredAdapter == null) {
            GridLayoutManager manager = new GridLayoutManager(getMContext(), 2);
            manager.setOrientation(GridLayoutManager.VERTICAL);
            mRvRequires.setLayoutManager(manager);
            mRvRequires.setHasFixedSize(true);
//            mRvRequires.addItemDecoration(new RecycleViewDivider(
//                    getMContext(), GridLayoutManager.HORIZONTAL, (int) (ScreenUtils.getScreenDensity(getMContext()) * 10), getResources().getColor(R.color.white)));
            RecyclerViewHeader header = RecyclerViewHeader.fromXml(getMContext(), R.layout.view_rv_header);
            TextView tv_title = (TextView) header.findViewById(R.id.tv_type_title);
            tv_title.setText(info.getTitle());
            header.attachTo(mRvRequires);
            mRequiredAdapter = new RequiredAdapter(getMContext(), info.getCourses());
            mRvRequires.setAdapter(mRequiredAdapter);
        } else {
            mRequiredAdapter.notifyDataSetChanged();
        }

    }

    //刷新精品推荐
    private void refreshRecommandAdapter(RecommandInfo info) {
        if (info == null) {
            info = new RecommandInfo();
            info.setTitle("精品推荐");
            info.setTotal_count(4);
            ArrayList<CourseBean> beans = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                CourseBean bean = new CourseBean();
                bean.setTitle("必读课" + i);
                bean.setDesc("课程描述" + i);
                beans.add(bean);
            }
            info.setCourses(beans);
        }
        if (mRecommandAdapter == null) {
            GridLayoutManager manager = new GridLayoutManager(getMContext(), 2);
            manager.setOrientation(GridLayoutManager.VERTICAL);
            mRvRecommand.setLayoutManager(manager);
            mRvRecommand.setHasFixedSize(true);
//            mRvRecommand.addItemDecoration(new RecycleViewDivider(
//                    getMContext(), GridLayoutManager.HORIZONTAL, (int) (ScreenUtils.getScreenDensity(getMContext()) * 10), getResources().getColor(R.color.white)));
            RecyclerViewHeader header = RecyclerViewHeader.fromXml(getMContext(), R.layout.view_rv_header);
            TextView tv_title = (TextView) header.findViewById(R.id.tv_type_title);
            tv_title.setText(info.getTitle());
            header.attachTo(mRvRecommand);
            mRecommandAdapter = new RecommandAdapter(getMContext(), info.getCourses());
            mRvRecommand.setAdapter(mRecommandAdapter);
        } else {
            mRecommandAdapter.notifyDataSetChanged();
        }

    }




    //课程数观察器
    Observer<HomePageResultInfo> mTokenObserver = new Observer<HomePageResultInfo>() {
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
        public void onNext(HomePageResultInfo resultInfo) {
            if (resultInfo != null) {
                //保存token
                PreferencesUtils.saveUserDataItem(Base.getContext(), PreferencesUtils.KEY_TOKEN, resultInfo.getData().getToken());
                getNetDataOrgans();
            }

        }
    };

    //登录
    private void getNetDataHomePageConfig() {
        if (!NetworkUtil.isAvailable(getMContext())) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put(CommonConstant.PARAM_ORG_ID, Store.getOrganId());

        Network.getCourseApi("tab_首页").getHomePageConfig(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mTokenObserver);
    }
}
