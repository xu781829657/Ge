package com.android.ge.ui.tabmain;

import android.view.View;
import android.widget.ImageView;

import com.android.ge.R;
import com.android.ge.ui.base.CommonBaseFragment;
import com.android.ge.ui.setting.PersonalCenterActivity;

import butterknife.Bind;

/**
 * Created by xudengwang on 17/3/18.
 */

public class MeFragment extends CommonBaseFragment {
    @Bind(R.id.iv_setting)
    ImageView mIvSetting;

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
    }
}
