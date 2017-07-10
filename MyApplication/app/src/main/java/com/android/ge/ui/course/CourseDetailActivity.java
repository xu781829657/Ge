package com.android.ge.ui.course;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.android.base.frame.Base;
import com.android.ge.R;
import com.android.ge.ui.base.CommonBaseActivity;
import com.android.ge.ui.base.NewFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by xudengwang on 17/7/10.
 */

public class CourseDetailActivity extends CommonBaseActivity {
    @Bind(R.id.tabs_course_detail)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager_course_detail)
    ViewPager mViewPager;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_course_detail;
    }

    @Override
    protected void initData() {
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new NewFragment(), Base.string(R.string.tab_course_detail_intro));
        adapter.addFragment(new NewFragment(), Base.string(R.string.tab_course_detail_learn_history));
        adapter.addFragment(new NewFragment(), Base.string(R.string.tab_course_detail_comment));
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
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
}
