package com.android.ge.ui.tabmain;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.android.ge.R;
import com.android.ge.ui.base.CommonBaseActivity;
import com.android.ge.widgets.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by xudengwang on 2017/3/14.
 */

public class MaintabActivity extends CommonBaseActivity{
    String value = "1/10";
    @Bind(R.id.rg_tab)
    RadioGroup mRgTab;
    @Bind(R.id.vp_tab)
    NoScrollViewPager mVpTab;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_main_tab ;
    }

    @Override
    protected void initData() {

        mRgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch (checkId){
                    case R.id.rbtn_homepage:
                    mVpTab.setCurrentItem(0);
                    break;
                    case R.id.rbtn_learning:
                        mVpTab.setCurrentItem(1);
                        break;
                    case R.id.rbtn_task:
                        mVpTab.setCurrentItem(2);
                        break;
                    case R.id.rbtn_me:
                        mVpTab.setCurrentItem(3);
                        break;

                }

            }
        });

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomePageFragment());
        adapter.addFragment(new LearningFragment());
        adapter.addFragment(new TaskFragment());
        adapter.addFragment(new MeFragment());

        mVpTab.setAdapter(adapter);
        mVpTab.setCurrentItem(0);

    }


    static class TabAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments = new ArrayList<>();

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        public List<Fragment> getmFragments() {
            return mFragments;
        }
    }
}
