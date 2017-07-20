package com.android.ge.ui.tabmain;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.android.base.frame.Base;
import com.android.base.util.ArraysUtils;
import com.android.ge.R;
import com.android.ge.ui.base.CommonBaseActivity;
import com.android.ge.ui.base.CommonBaseFragment;
import com.android.ge.widgets.view.NoScrollViewPager;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by xudengwang on 2017/3/14.
 */

public class MaintabActivity extends CommonBaseActivity {
    String value = "1/10";
    @Bind(R.id.rg_tab)
    RadioGroup mRgTab;
    @Bind(R.id.vp_tab)
    NoScrollViewPager mVpTab;

    private List<CommonBaseFragment> mFragments = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main_tab;
    }

    @Override
    protected void initData() {

        mRgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch (checkId) {
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
        mFragments.add(new HomePageFragment());
        mFragments.add(new LearningFragment());
        mFragments.add(new TaskFragment());
        mFragments.add(new MeFragment());

        PageFragmentAdapter adapter = new PageFragmentAdapter(getSupportFragmentManager(), mFragments);
        mVpTab.setOffscreenPageLimit(1);
        mVpTab.setAdapter(adapter);
        mVpTab.setCurrentItem(0);

    }

    public class PageFragmentAdapter extends FragmentPagerAdapter {

        private List<CommonBaseFragment> dataFragmentList;

        public PageFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public PageFragmentAdapter(FragmentManager fm, List<CommonBaseFragment> argDataFragmentList) {
            super(fm);
            this.dataFragmentList = argDataFragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return dataFragmentList.get(position);
        }

        @Override
        public int getCount() {
            if (ArraysUtils.isNotEmpty(dataFragmentList)) {
                return dataFragmentList.size();
            }
            return 0;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {

            return super.getItemPosition(object);
        }
    }


    private long lastBackKeyDownTick = 0;
    public static final long MAX_DOUBLE_BACK_DURATION = 1500;

    @Override
    public void onBackPressed() {
        long currentTick = System.currentTimeMillis();
        if (currentTick - lastBackKeyDownTick > MAX_DOUBLE_BACK_DURATION) {
            Base.showToast("再按一次退出");
            lastBackKeyDownTick = currentTick;
        } else {
            finish();
            System.exit(0);
        }
    }
}
