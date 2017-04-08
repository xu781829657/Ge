package com.android.ge.ui.start;

import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.ge.R;
import com.android.ge.ui.base.CommonBaseActivity;
import com.android.ge.utils.PreferencesUtils;
import com.bumptech.glide.Glide;

import butterknife.Bind;

public class SplashActivity extends CommonBaseActivity {
    private final int SPLASH_DISPLAY_LENGHT = 2000;
    @Bind(R.id.iv_splash)
    ImageView mIvSplash;

    @Bind(R.id.iv_custom_splash)
    ImageView mIvCustomSplash;
    @Bind(R.id.iv_custom_banner)
    ImageView mIvCustomBanner;

    @Override
    protected void initData() {
        getCustomSplashImg();
        getBannerImg();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                //getNetDataAppUpdate();
                gotoActivity(CustomSplashActivity.class, true);
            }

        }, SPLASH_DISPLAY_LENGHT);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

//    /**
//     * 更新升级
//     */
//    Observer<AppUpdateResultInfo> mUpdateObserver = new Observer<AppUpdateResultInfo>() {
//        @Override
//        public void onCompleted() {
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            LogUtils.d(getClass(), "observer member e.message:" + e.getMessage());
//            e.printStackTrace();
//            ToastUtil.showLong(mContext, "请求出错,错误信息:" + e.getMessage());
//
//        }
//
//        @Override
//        public void onNext(AppUpdateResultInfo resultInfo) {
//            try {
//                if (resultInfo != null && resultInfo.android != null) {
//                    UpdateInfo info = new UpdateInfo();
//                    info.androidApkUrl = resultInfo.android.url;
//                    info.androidVersion = resultInfo.android.version;
//                    info.androidChangeLog = resultInfo.android.description;
//                    if (resultInfo.android.must) {
//                        info.androidForce = 1;
//                    } else {
//                        info.androidForce = 0;
//                    }
//                    if (info.androidVersion == null) {
//                        return;
//                    }
//                    int compareResult = VersionUtil.compareVersion(mContext.getString(R.string.app_version), info.androidVersion);
//                    LogUtils.d(getClass(), "compareResult:" + compareResult + ",info.androidVersion:" + info.androidVersion);
//                    if (compareResult < 0) {
//                        EventBus.getDefault().post(new UpdateEventEntry(info));
//                        PreferencesUtils.saveUpdate(mContext, true);
//                    } else {
//                        PreferencesUtils.saveUpdate(mContext, false);
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//    };
//
//    //检查更新升级
//    private void getNetDataAppUpdate() {
//        if (!NetworkUtil.isAvailable(mContext)) {
//            Base.showToast(R.string.errmsg_network_unavailable);
//            return;
//        }
//        Network.getCourseApi("版本更新").getAppUpdate()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(mUpdateObserver);
//    }


    //加载启动页
    private void getCustomSplashImg() {
        String splash_url = PreferencesUtils.getUserData(mContext, PreferencesUtils.KEY_SPLASH_IMAGE_URL);
        LogUtils.d(getClass(), "splash_url:" + splash_url);
        if (!TextUtils.isEmpty(splash_url)) {
            Glide.with(Base.getContext()).load(splash_url).into(mIvCustomSplash);
        }
    }

    //加载banner图片
    private void getBannerImg() {
        String banner_url = PreferencesUtils.getUserData(mContext, PreferencesUtils.KEY_BANNER_IMAGE_URL);
        LogUtils.d(getClass(), "banner_url:" + banner_url);
        if (!TextUtils.isEmpty(banner_url)) {
            Glide.with(Base.getContext()).load(banner_url).into(mIvCustomBanner);
        }
    }

}