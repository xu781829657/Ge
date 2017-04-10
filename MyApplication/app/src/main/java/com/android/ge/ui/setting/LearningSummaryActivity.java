package com.android.ge.ui.setting;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.ge.R;
import com.android.ge.ui.base.CommonBaseActivity;

import butterknife.Bind;

/**
 * Created by xudengwang on 17/4/9.
 */

public class LearningSummaryActivity extends CommonBaseActivity{
    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_learning_summary;
    }

    @Override
    protected void initData() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTvTitle.setText(Base.string(R.string.title_learning_summary));

    }
}
