package com.android.ge.ui.tabmain;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.android.base.frame.Base;
import com.android.base.util.ArraysUtils;
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.base.util.ScreenUtils;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.controller.Store;
import com.android.ge.controller.adapter.LearningCourseTypeAdapter;
import com.android.ge.controller.diliver.RecycleViewDivider;
import com.android.ge.model.BaseCourseTypeInfo;
import com.android.ge.model.CourseBean;
import com.android.ge.model.HomePageResultInfo;
import com.android.ge.model.LearningResultInfo;
import com.android.ge.model.learning.BaseLearningItem;
import com.android.ge.model.learning.TitleItemInfo;
import com.android.ge.network.Network;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.ui.base.CommonBaseFragment;

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

public class LearningFragment extends CommonBaseFragment {
    @Bind(R.id.rv_learning)
    RecyclerView mRvLearning;
    private List<BaseCourseTypeInfo> mCourseTypes = new ArrayList<>();
    private LearningCourseTypeAdapter mAdapter;
    private ArrayList<BaseLearningItem> mLearningItemList = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.fm_learning;
    }

    @Override
    protected void initData() {
        getNetDataCourseTypeList();
    }

    @Override
    public void onResume() {
//        initFalseData();
//        refreshAdapter();

        super.onResume();
    }

    private void refreshMainData(LearningResultInfo resultInfo) {

        if (resultInfo.data == null || resultInfo.data.size() == 0) {
            return;
        }
        mLearningItemList.clear();
        for (int i = 0; i < resultInfo.data.size(); i++) {
            BaseCourseTypeInfo typeInfo = resultInfo.data.get(i);
            if (typeInfo.getCourses() != null && typeInfo.getCourses().size() > 0) {
                BaseLearningItem titleLearningItem = new BaseLearningItem();
                TitleItemInfo titleItemInfo = new TitleItemInfo();
                titleItemInfo.setId(typeInfo.getId());
                titleItemInfo.setTitle(typeInfo.getTitle());
                titleItemInfo.setTotal_count(typeInfo.getTotal_count());
                titleLearningItem.setTitleItemInfo(titleItemInfo);
                mLearningItemList.add(titleLearningItem);
                for (int j = 0; j < typeInfo.getCourses().size(); j++) {
                    CourseBean courseBean = typeInfo.getCourses().get(0);
                    BaseLearningItem courseLearningItem = new BaseLearningItem();
                    courseLearningItem.setCourseBean(courseBean);
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
            StaggeredGridLayoutManager treasureLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            mRvLearning.setLayoutManager(treasureLayoutManager);
            mRvLearning.setHasFixedSize(true);
            mRvLearning.addItemDecoration(new RecycleViewDivider(
                    getMContext(), GridLayoutManager.HORIZONTAL, (int) (ScreenUtils.getScreenDensity(getMContext()) * 10), getResources().getColor(R.color.white)));
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
            titleItemInfo.setTotal_count(courseTypeInfo.getTotal_count());
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


    private void initFalseData() {
        for (int j = 0; j < 3; j++) {
            BaseCourseTypeInfo info = new BaseCourseTypeInfo();
            info.setTitle("光华学校必读课");
            info.setTotal_count(5);
            ArrayList<CourseBean> beans = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                CourseBean bean = new CourseBean();
                bean.setTitle("必读课" + i);
                bean.setDesc("课程描述" + i);
                beans.add(bean);
            }
            info.setCourses(beans);
            mCourseTypes.add(info);
        }

    }


    //课程分类
    Observer<LearningResultInfo> mLearningResultObserver = new Observer<LearningResultInfo>() {
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
        public void onNext(LearningResultInfo resultInfo) {
            if (resultInfo == null || resultInfo.data == null) {
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

        Network.getCourseApi("tab_首页").getCourseTypeList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mLearningResultObserver);
    }
}
