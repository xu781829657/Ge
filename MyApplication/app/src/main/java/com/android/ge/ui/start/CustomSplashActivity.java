package com.android.ge.ui.start;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.ge.R;
import com.android.ge.ui.base.CommonBaseActivity;
import com.android.ge.ui.login.LoginActivity;
import com.android.ge.ui.tabmain.MaintabActivity;
import com.android.ge.utils.PreferencesUtils;
import com.android.ge.utils.update.UpdateEventEntry;
import com.android.ge.utils.update.UpdateManager;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;

/**
 * Created by xudengwang on 2016/11/25.
 */

public class CustomSplashActivity extends CommonBaseActivity {

    @Bind(R.id.iv_custom)
    ImageView mIvCustom;

    @Bind(R.id.btn_biting)
    Button mBtnBiting;

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        mContext = this;
        String splash_url = PreferencesUtils.getUserData(mContext, PreferencesUtils.KEY_SPLASH_IMAGE_URL);
        LogUtils.d(getClass(), "splash_url:" + splash_url);
        int defaultDrawableResId = R.drawable.splash_screen_page;
//        if (mContext.getResources().getConfiguration().locale.getCountry().equals("CN")) {
//            defaultDrawableResId = R.drawable.start_china_bj;
//        }
        if (!TextUtils.isEmpty(splash_url)) {
            Glide.with(Base.getContext()).load(splash_url).error(defaultDrawableResId).into(mIvCustom);
        } else {
            mIvCustom.setImageResource(defaultDrawableResId);
        }
        mBtnBiting.setText(R.string.splash_enter);
        mBtnBiting.setVisibility(View.VISIBLE);

        mIvCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipNext();
            }
        });
        mBtnBiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipNext();
            }
        });

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_custom_splash;
    }

    private void skipNext() {
        String token = PreferencesUtils.getUserData(mContext, PreferencesUtils.KEY_TOKEN);
        if (TextUtils.isEmpty(token)) {
            gotoActivity(LoginActivity.class, true);
        } else {
            gotoActivity(MaintabActivity.class, true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    // 展示app更新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showAppUpdate(UpdateEventEntry eventEntry) {
        UpdateManager manager = new UpdateManager(mContext);
        manager.showUpdateInfo(mContext, eventEntry.info);
    }

}
