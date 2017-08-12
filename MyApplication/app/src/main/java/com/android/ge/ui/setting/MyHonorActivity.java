package com.android.ge.ui.setting;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.ge.R;
import com.android.ge.ui.base.CommonBaseActivity;

import butterknife.Bind;

/**
 * Created by xudengwang on 17/8/11.
 */

public class MyHonorActivity extends CommonBaseActivity {
    @Bind(R.id.rv_test_medal)
    RecyclerView mRvTestMedal;
    @Bind(R.id.rv_learn_medal)
    RecyclerView mRvLearnMedal;

    @Bind(R.id.lin_test_medal)
    LinearLayout mLinTestMedal;
    @Bind(R.id.lin_learn_medal)
    LinearLayout mLinLearnMedal;

    @Bind(R.id.tv_test_medal_num)
    TextView mTvTestMedalNum;
    @Bind(R.id.tv_learn_medal_num)
    TextView mTvLearnMedalNum;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_honor;
    }

    @Override
    protected void initData() {

    }
}
