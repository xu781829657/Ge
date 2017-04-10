package com.android.ge.ui.tabmain;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.ge.R;
import com.android.ge.ui.base.CommonBaseFragment;
import com.android.ge.ui.setting.LearningSummaryActivity;
import com.android.ge.ui.setting.PersonalCenterActivity;
import com.android.ge.ui.setting.RankActivity;

import butterknife.Bind;

/**
 * Created by xudengwang on 17/3/18.
 */

public class MeFragment extends CommonBaseFragment {
    @Bind(R.id.iv_setting)
    ImageView mIvSetting;

    @Bind(R.id.lin_my_list)
    LinearLayout mLinMyList;

    @Bind(R.id.lin_learn_summary)
    LinearLayout mLinlearnSummary;

    @Override
    public int getContentViewId() {
        return R.layout.fm_me;
    }

    @Override
    protected void initData() {
        mIvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(PersonalCenterActivity.class, false);
            }
        });
        mLinMyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(RankActivity.class, false);
            }
        });
        mLinlearnSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(LearningSummaryActivity.class, false);
            }
        });
    }
}
