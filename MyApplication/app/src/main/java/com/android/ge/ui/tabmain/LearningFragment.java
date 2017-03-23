package com.android.ge.ui.tabmain;

import android.support.v7.widget.RecyclerView;

import com.android.ge.R;
import com.android.ge.ui.base.CommonBaseFragment;

import butterknife.Bind;

/**
 * Created by xudengwang on 17/3/18.
 */

public class LearningFragment extends CommonBaseFragment{
    @Bind(R.id.rv_learning)
    RecyclerView mRvLearning;
    @Override
    public int getContentViewId() {
        return R.layout.fm_learning;
    }

    @Override
    protected void initData() {

    }
}
