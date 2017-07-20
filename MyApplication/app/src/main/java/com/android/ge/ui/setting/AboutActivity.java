package com.android.ge.ui.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.frame.AppManager;
import com.android.base.frame.Base;
import com.android.ge.R;
import com.android.ge.app.AppApplication;
import com.android.ge.constant.AppConfig;
import com.android.ge.ui.base.CommonBaseActivity;
import com.android.ge.ui.login.LoginActivity;
import com.android.ge.utils.PreferencesUtils;
import com.android.ge.utils.ui.ViewDialog;

import butterknife.Bind;

/**
 * Created by xudengwang on 2017/7/20.
 */

public class AboutActivity extends CommonBaseActivity {
    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;

    @Bind(R.id.iv_logo)
    ImageView mIvLogo;

    @Bind(R.id.tv_version)
    TextView mTvVersion;

    private int hideNum;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initData() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvTitle.setText(Base.string(R.string.title_about));
        mTvVersion.setText(Base.string(R.string.app_version));
        mIvLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideNum++;
                if (hideNum >= 3) {
                    changeEnvironment();
                }
            }
        });
    }

    //切换环境
    private void changeEnvironment() {
        String msg = "";
        if (AppApplication.getInstance().ENVIRONMENT == AppConfig.ENVIRONMENT_PRO) {

            msg = "测试环境";
            //Base.showToast("已切换为测试环境，重启应用生效");
            PreferencesUtils.saveEnvironment(mContext, AppConfig.ENVIRONMENT_DEV);

        } else if (AppApplication.getInstance().ENVIRONMENT == AppConfig.ENVIRONMENT_DEV) {
            msg = "演示环境";
            //Base.showToast("已切换为生产环境，重启应用生效");
            PreferencesUtils.saveEnvironment(mContext, AppConfig.ENVIRONMENT_PRO);
        }
        ViewDialog.showSingleDialog(mContext, "注意", "当前app环境已切换为" + msg + ",需立即退出再重启后生效!", new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PreferencesUtils.clearUserData(mContext);
                        gotoActivity(LoginActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        AppManager.create().appExit(mContext);
                    }
                }
        );
    }

}
