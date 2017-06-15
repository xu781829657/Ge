package com.android.ge.ui.tabmain;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.controller.Store;
import com.android.ge.controller.entry.PathEntry;
import com.android.ge.model.path.PathListInfo;
import com.android.ge.model.path.PathResultInfo;
import com.android.ge.model.task.TaskListInfo;
import com.android.ge.network.Network;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.network.response.ServerResponseFunc;
import com.android.ge.ui.base.CommonBaseActivity;
import com.android.ge.ui.base.CommonBaseFragment;
import com.android.ge.ui.task.TaskListFragment;

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

public class TaskFragment extends CommonBaseFragment {
    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tabs_task)
    TabLayout mTabTask;
    @Bind(R.id.viewpager_task)
    ViewPager mViewPagerTask;
    public static final int TAB_UNSTART = 0;//待参加的
    public static final int TAB_ONGOING = 1;//进行中的
    public static final int TAB_FINISHED = 2;//已完成的

    private List<TaskListFragment> mTaskListFms;
    private List<String> mTabTitles;
    private TaskListFragment mTaskUnStartFm, mTaskOngoingFm, mTaskFinishedFm;
    private TaskTabAdapter mTabAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.fm_task;
    }

    @Override
    protected void initData() {
        mTvTitle.setText(Base.string(R.string.title_task));
        mIvBack.setVisibility(View.GONE);
        if (mTaskListFms == null) {
            mTaskListFms = new ArrayList<>();
            Bundle bundle1 = new Bundle();

            bundle1.putInt(CommonConstant.KEY_TAB_FALG, TAB_UNSTART);
            mTaskUnStartFm = new TaskListFragment();
            mTaskUnStartFm.setArguments(bundle1);
            mTaskListFms.add(mTaskUnStartFm);

            Bundle bundle2 = new Bundle();
            bundle2.putInt(CommonConstant.KEY_TAB_FALG, TAB_ONGOING);
            mTaskOngoingFm = new TaskListFragment();
            mTaskOngoingFm.setArguments(bundle2);
            mTaskListFms.add(mTaskOngoingFm);

            Bundle bundle3 = new Bundle();
            bundle3.putInt(CommonConstant.KEY_TAB_FALG, TAB_FINISHED);
            mTaskFinishedFm = new TaskListFragment();
            mTaskFinishedFm.setArguments(bundle3);
            mTaskListFms.add(mTaskFinishedFm);

        }

        if (mTabTitles == null) {
            mTabTitles = new ArrayList<>();
            mTabTitles.add(Base.string(R.string.task_unstart));
            mTabTitles.add(Base.string(R.string.task_ongoing));
            mTabTitles.add(Base.string(R.string.task_finished));
        }

        setupViewPager();

    }

    @Override
    public void onResume() {
        getNetDataLearningPath();
        super.onResume();
    }

    private void setupViewPager() {
        if (mTabAdapter == null) {
            mTabAdapter = new TaskTabAdapter(((CommonBaseActivity) getMContext()).getSupportFragmentManager(),
                    mTaskListFms, mTabTitles);
            mViewPagerTask.setOffscreenPageLimit(3);
            mViewPagerTask.setAdapter(mTabAdapter);
            mTabTask.setupWithViewPager(mViewPagerTask);
        } else {
            mTabAdapter.notifyDataSetChanged();
        }

    }

    static class TaskTabAdapter extends FragmentPagerAdapter {
        private List<TaskListFragment> mFragments = new ArrayList<>();
        private List<String> mFragmentTitles = new ArrayList<>();

        public TaskTabAdapter(FragmentManager fm, List<TaskListFragment> fms, List<String> titles) {
            super(fm);
            this.mFragments = fms;
            this.mFragmentTitles = titles;

        }

//        public void addFragment(Fragment fragment, String title) {
//            mFragments.add(fragment);
//            mFragmentTitles.add(title);
//        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }


    //学习路径结果
    Observer<PathListInfo> mPathObserver = new Observer<PathListInfo>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            LogUtils.d(getClass(), "observer course e.message:" + e.getMessage());
            e.printStackTrace();
            Base.showToast(ExceptionEngine.handleException(e,getActivity()).message);
        }

        @Override
        public void onNext(PathListInfo resultInfo) {
            if (resultInfo == null || resultInfo.learningpath == null) {
                Base.showToast(R.string.errmsg_data_error);
            }
            //EventBus.getDefault().post(new PathEntry(resultInfo));

            for (int i = 0; i < mTaskListFms.size(); i++) {
                mTaskListFms.get(i).refreshPathData(new PathEntry(resultInfo));
            }


        }
    };

    //获取学习路径
    private void getNetDataLearningPath() {
        if (!NetworkUtil.isAvailable(getMContext())) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }

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
