package com.android.ge.ui.tabmain;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.base.util.ArraysUtils;
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.controller.Store;
import com.android.ge.controller.adapter.LearningCourseTypeAdapter;
import com.android.ge.controller.entry.BannerEntry;
import com.android.ge.model.BaseCourseTypeInfo;
import com.android.ge.model.CourseBean;
import com.android.ge.model.GalleryBean;
import com.android.ge.model.LearningInfo;
import com.android.ge.model.learning.BaseLearningItem;
import com.android.ge.model.learning.TitleItemInfo;
import com.android.ge.network.Network;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.network.response.ServerResponseFunc;
import com.android.ge.ui.base.CommonBaseFragment;
import com.android.ge.ui.course.CourseTypeAllActivity;
import com.android.ge.ui.task.PathListActivity;
import com.android.ge.utils.image.GlideImageLoader;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
 * tab学习页
 */

public class LearningFragment extends CommonBaseFragment {
    @Bind(R.id.lin_course_classify)
    LinearLayout mLinCourseTypeAll;
    @Bind(R.id.banner)
    Banner mBanner;
    @Bind(R.id.rv_learning)
    RecyclerView mRvLearning;
    @Bind(R.id.lin_lerning_path)
    LinearLayout mLinLearningPath;
    private List<BaseCourseTypeInfo> mCourseTypes = new ArrayList<>();
    private LearningCourseTypeAdapter mAdapter;
    private ArrayList<BaseLearningItem> mLearningItemList = new ArrayList<>();
    private List<String> mBannerImageUrls = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.fm_learning;
    }

    @Override
    protected void initData() {

        mLinLearningPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(PathListActivity.class, false);
            }
        });

        mLinCourseTypeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(CourseTypeAllActivity.class, false);
            }
        });

    }

    @Override
    public void onResume() {
        getNetDataCourseTypeList();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

//    // 展示app更新
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void refreshBannerData(BannerEntry eventEntry) {
//        LogUtils.d("refreshBannerData!!!!");
//        mBannerImageUrls.clear();
//        mBannerImageUrls.addAll(eventEntry.bannerUrls);
//        refreshBanner();
//    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

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
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(mBannerImageUrls);
        mBanner.setDelayTime(3000);
        mBanner.start();
    }

    private void refreshMainData(LearningInfo resultInfo) {
        refreshBanner(resultInfo.advertises);
        if (resultInfo.courses == null || resultInfo.courses.size() == 0) {
            return;
        }
        mLearningItemList.clear();
        //将courses分类列表转化成瀑布流展示中的数据(title和course)
        for (int i = 0; i < resultInfo.courses.size(); i++) {
            BaseCourseTypeInfo typeInfo = resultInfo.courses.get(i);
            if (typeInfo.getCourses() != null && typeInfo.getCourses().size() > 0) {
                BaseLearningItem titleLearningItem = new BaseLearningItem();
                TitleItemInfo titleItemInfo = new TitleItemInfo();
                titleItemInfo.setId(typeInfo.getId());
                titleItemInfo.setTitle(typeInfo.getTitle());
                titleItemInfo.setTotal_count(typeInfo.getTotal());
                titleLearningItem.setTitleItemInfo(titleItemInfo);
                mLearningItemList.add(titleLearningItem);
                for (int j = 0; j < typeInfo.getCourses().size(); j++) {
                    CourseBean courseBean = typeInfo.getCourses().get(j);
                    BaseLearningItem courseLearningItem = new BaseLearningItem();
                    courseLearningItem.setCourseBean(courseBean);
                    courseLearningItem.setOriginalPosition(j);
                    mLearningItemList.add(courseLearningItem);
                }

            }

        }
        refreshAdapter();
    }

    /**
     * 我的投资list，我的活动grid，会员发现grid假数据
     */
    private void refreshAdapter() {
        if (mAdapter == null) {
            //采用recycleview 瀑布流形式实现


            StaggeredGridLayoutManager treasureLayoutManager = new StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL);
            mRvLearning.setLayoutManager(treasureLayoutManager);
            mRvLearning.setHasFixedSize(true);
            mRvLearning.setNestedScrollingEnabled(false);

            mAdapter = new LearningCourseTypeAdapter(getContext(), mLearningItemList);
            mRvLearning.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private List<BaseLearningItem> transformData(List<BaseCourseTypeInfo> courseTypeInfos) {

        if (courseTypeInfos == null || courseTypeInfos.size() == 0) {
            return mLearningItemList;
        }
        mLearningItemList.clear();
        for (int i = 0; i < courseTypeInfos.size(); i++) {
            BaseCourseTypeInfo courseTypeInfo = courseTypeInfos.get(0);
            TitleItemInfo titleItemInfo = new TitleItemInfo();
            titleItemInfo.setId(courseTypeInfo.getId());
            titleItemInfo.setTitle(courseTypeInfo.getTitle());
            titleItemInfo.setTotal_count(courseTypeInfo.getTotal());
            BaseLearningItem learningItem = new BaseLearningItem();
            learningItem.setTitleItemInfo(titleItemInfo);
            mLearningItemList.add(learningItem);

            if (!ArraysUtils.isEmptyList(courseTypeInfo.getCourses())) {
                for (int j = 0; j < courseTypeInfo.getCourses().size(); j++) {
                    CourseBean courseBean = courseTypeInfo.getCourses().get(j);
                    BaseLearningItem newLearningItem = new BaseLearningItem();
                    newLearningItem.setOriginalPosition(j);
                    newLearningItem.setCourseBean(courseBean);
                    if (j + 1 == courseTypeInfo.getCourses().size()) {
                        newLearningItem.setOriginalLast(true);
                    }
                    mLearningItemList.add(newLearningItem);
                }
            }

        }
        return mLearningItemList;

    }


//    private void initFalseData() {
//        for (int j = 0; j < 3; j++) {
//            BaseCourseTypeInfo info = new BaseCourseTypeInfo();
//            info.setTitle("光华学校必读课");
//            info.setTotal(5);
//            ArrayList<CourseBean> beans = new ArrayList<>();
//            for (int i = 0; i < 3; i++) {
//                CourseBean bean = new CourseBean();
//                bean.setTitle("必读课" + i);
//                bean.setContent("课程描述" + i);
//                beans.add(bean);
//            }
//            info.setCourses(beans);
//            mCourseTypes.add(info);
//        }
//
//    }

    //课程分类
    Observer<LearningInfo> mLearningResultObserver = new Observer<LearningInfo>() {
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
        public void onNext(LearningInfo resultInfo) {
            if (resultInfo == null || resultInfo.courses == null) {
                Base.showToast(R.string.errmsg_data_error);
            }
            refreshMainData(resultInfo);
        }
    };

    //登录
    private void getNetDataCourseTypeList() {
        if (!NetworkUtil.isAvailable(getMContext())) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put(CommonConstant.PARAM_ORG_ID, Store.getOrganId());

        Network.getCourseApi("tab_学习").getCourseTypeList(map)
                .subscribeOn(Schedulers.io())
                //拦截服务器返回的错误
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ServerResponseFunc<LearningInfo>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mLearningResultObserver);
    }
}
