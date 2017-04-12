package com.android.ge.ui.setting;

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

import com.android.ge.R;
import com.android.ge.constant.CommonConstant;

import com.android.ge.ui.base.CommonBaseActivity;


import java.util.ArrayList;

import java.util.List;

import butterknife.Bind;

/**
 * Created by xudengwang on 17/4/10.
 */

public class RankActivity extends CommonBaseActivity {
    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tabs_rank)
    TabLayout mTabsRank;
    @Bind(R.id.viewpager_rank)
    ViewPager mViewPagerRank;
    public static final int TAB_UNSTART = 0;//待参加的
    public static final int TAB_ONGOING = 1;//进行中的
    public static final int TAB_FINISHED = 2;//已完成的

    private List<RankListFragment> mRankListFms;
    private List<String> mTabTitles;
    private RankListFragment mRankUnStartFm, mRankOngoingFm, mRankFinishedFm;
    private RankTabAdapter mTabAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_rank;
    }

    @Override
    protected void initData() {
        mTvTitle.setText(Base.string(R.string.title_rank));
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (mRankListFms == null) {
            mRankListFms = new ArrayList<>();
            Bundle bundle1 = new Bundle();

            bundle1.putInt(CommonConstant.KEY_TAB_FALG, TAB_UNSTART);
            mRankUnStartFm = new RankListFragment();
            mRankUnStartFm.setArguments(bundle1);
            mRankListFms.add(mRankUnStartFm);

            Bundle bundle2 = new Bundle();
            bundle2.putInt(CommonConstant.KEY_TAB_FALG, TAB_ONGOING);
            mRankOngoingFm = new RankListFragment();
            mRankOngoingFm.setArguments(bundle2);
            mRankListFms.add(mRankOngoingFm);

            Bundle bundle3 = new Bundle();
            bundle3.putInt(CommonConstant.KEY_TAB_FALG, TAB_FINISHED);
            mRankFinishedFm = new RankListFragment();
            mRankFinishedFm.setArguments(bundle3);
            mRankListFms.add(mRankFinishedFm);

        }

        if (mTabTitles == null) {
            mTabTitles = new ArrayList<>();
            mTabTitles.add(Base.string(R.string.rank_tab_total));
            mTabTitles.add(Base.string(R.string.rank_tab_group));
            mTabTitles.add(Base.string(R.string.rank_tab_groups));
        }

        setupViewPager();
        //getNetDataLearningPath();

    }

    private void setupViewPager() {
        if (mTabAdapter == null) {
            mTabAdapter = new RankTabAdapter(getSupportFragmentManager(),
                    mRankListFms, mTabTitles);
            mViewPagerRank.setOffscreenPageLimit(3);
            mViewPagerRank.setAdapter(mTabAdapter);
            mTabsRank.setupWithViewPager(mViewPagerRank);
        } else {
            mTabAdapter.notifyDataSetChanged();
        }

    }

    static class RankTabAdapter extends FragmentPagerAdapter {
        private List<RankListFragment> mFragments = new ArrayList<>();
        private List<String> mFragmentTitles = new ArrayList<>();

        public RankTabAdapter(FragmentManager fm, List<RankListFragment> fms, List<String> titles) {
            super(fm);
            this.mFragments = fms;
            this.mFragmentTitles = titles;

        }

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


//    //学习路径结果
//    Observer<PathResultInfo> mPathObserver = new Observer<PathResultInfo>() {
//        @Override
//        public void onCompleted() {
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            LogUtils.d(getClass(), "observer course e.message:" + e.getMessage());
//            e.printStackTrace();
//            Base.showToast(ExceptionEngine.handleException(e).message);
//        }
//
//        @Override
//        public void onNext(PathResultInfo resultInfo) {
//            if (resultInfo == null || resultInfo.data == null) {
//                Base.showToast(R.string.errmsg_data_error);
//            }
//            //EventBus.getDefault().post(new PathEntry(resultInfo));
//
//            for (int i = 0; i < mTaskListFms.size(); i++) {
//                mTaskListFms.get(i).refreshPathData(new PathEntry(resultInfo));
//            }
//
//
//        }
//    };
//
//    //获取学习路径
//    private void getNetDataLearningPath() {
//        if (!NetworkUtil.isAvailable(getMContext())) {
//            Base.showToast(R.string.errmsg_network_unavailable);
//            return;
//        }
//
//        Map<String, String> map = new HashMap<>();
//        map.put(CommonConstant.PARAM_ORG_ID, Store.getOrganId());
//
//        Network.getCourseApi("学习路径").getLearningPath(map)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(mPathObserver);
//    }
}
