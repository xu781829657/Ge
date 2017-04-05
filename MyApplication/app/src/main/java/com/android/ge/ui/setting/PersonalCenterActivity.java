package com.android.ge.ui.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.base.frame.AppManager;
import com.android.base.frame.Base;
import com.android.base.util.FileUtils;
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.base.util.PhotoUtils;
import com.android.ge.R;
import com.android.ge.app.AppApplication;
import com.android.ge.constant.AppConfig;
import com.android.ge.network.Network;
import com.android.ge.ui.base.CommonBaseActivity;
import com.android.ge.ui.login.LoginActivity;
import com.android.ge.utils.PreferencesUtils;
import com.android.ge.utils.image.ImageLoader;
import com.android.ge.utils.image.ImageRequest;
import com.android.ge.utils.ui.DialogUtils;
import com.android.ge.utils.ui.ViewDialog;
import com.kyleduo.switchbutton.SwitchButton;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.Bind;
import cn.carbs.android.avatarimageview.library.AvatarImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by xudengwang on 2016/10/25.
 */

public class PersonalCenterActivity extends CommonBaseActivity implements PhotoUtils.OnAccomplishCallBack {

    @Bind(R.id.aiv_avatar)
    AvatarImageView mAivAvatar;

    @Bind(R.id.lin_logout)
    LinearLayout mLinLogout;//注销

    @Bind(R.id.rel_change_password)
    RelativeLayout mRelChangePass;

    @Bind(R.id.rel_personal_info)
    RelativeLayout mRelPersonalInfo;

    @Bind(R.id.rel_version_update)
    RelativeLayout mRelVersionUpdate;

    @Bind(R.id.tv_name)
    TextView mTvName;

    @Bind(R.id.tv_update)
    TextView mTvUpdate;

    @Bind(R.id.tv_runmodel)
    TextView mTvRunmodel;

    @Bind(R.id.rel_runmodel)
    RelativeLayout mRlRunmodel;

    @Bind(R.id.swbtn_runmodel)
    SwitchButton mSwBtnRunModel;

    private int hideNum;
    PhotoUtils mPhotoUtil;

    private static final String HEAD_IMAGE_NAME = "avatar";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_personal_center;
    }

    @Override
    protected void initData() {
        mContext = this;
        mPhotoUtil = new PhotoUtils();
        ImageView backIv = (ImageView) findViewById(R.id.iv_back);
        TextView titleTv = (TextView) findViewById(R.id.tv_title);
        titleTv.setText(Base.string(R.string.title_personal_center));
        titleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideNum++;
                if (hideNum >= 3) {
                    mRlRunmodel.setVisibility(View.VISIBLE);
                }
            }
        });
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mAivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.showCommonPhotoSlectListDialog(mContext, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mPhotoUtil.doTakePhoto(PersonalCenterActivity.this);
                                break;
                            case 1:
                                mPhotoUtil.doPickPhotoFromGallery(PersonalCenterActivity.this);
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        });


        mRelChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gotoActivity(ChangePasswordActivity.class, false);
            }
        });

        mRelPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gotoActivity(ChangePersonalDataActivity.class, false);
            }
        });
        mRelVersionUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getNetDataAppUpdate();
            }
        });


        mLinLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PreferencesUtils.clearUserData(mContext);
                gotoActivity(LoginActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                AppManager.create().finishAllActivity();
            }
        });

        refreshAvatar();
        String name = PreferencesUtils.getUserData(mContext, PreferencesUtils.KEY_FIRST_NAME) + PreferencesUtils.getUserData(mContext, PreferencesUtils.KEY_LAST_NAME);
        LogUtils.d(getClass(), "name:" + name);
        mTvName.setText(name);

        if (PreferencesUtils.getUpdate(mContext)) {
            mTvUpdate.setVisibility(View.VISIBLE);
        } else {
            mTvUpdate.setVisibility(View.GONE);
        }
        if (AppApplication.getInstance().ENVIRONMENT == AppConfig.ENVIRONMENT_PRO) {
            mSwBtnRunModel.setChecked(true);
            mTvRunmodel.setText("生产环境 " + mContext.getString(R.string.app_version));
        } else if (AppApplication.getInstance().ENVIRONMENT == AppConfig.ENVIRONMENT_DEV) {
            mSwBtnRunModel.setChecked(false);
            mTvRunmodel.setText("测试环境 " + mContext.getString(R.string.app_version));
        }
        mSwBtnRunModel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String msg = "";
                if (isChecked) {
                    msg = "生产环境";
                    //Base.showToast("已切换为生产环境，重启应用生效");
                    PreferencesUtils.saveEnvironment(mContext, AppConfig.ENVIRONMENT_PRO);
                } else {
                    msg = "测试环境";
                    //Base.showToast("已切换为测试环境，重启应用生效");
                    PreferencesUtils.saveEnvironment(mContext, AppConfig.ENVIRONMENT_DEV);
                }
                ViewDialog.showSingleDialog(mContext, "注意", "当前app环境已切换为" + msg + ",需立即退出再重启后生效!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PreferencesUtils.clearUserData(mContext);
                        AppManager.create().appExit(mContext);
                    }
                });
            }
        });
    }

    private void refreshAvatar() {
        String avatar_url = PreferencesUtils.getUserData(mContext, PreferencesUtils.KEY_AVATAR_URL);
        LogUtils.d(getClass(), "avatar_url:" + avatar_url);
        if (!TextUtils.isEmpty(avatar_url)) {
            ImageRequest imageRequest = new ImageRequest.Builder().imgView(mAivAvatar).placeHolder(R.drawable.icon_head).url(avatar_url).create();
            ImageLoader.getProvider().loadImage(imageRequest);
        }
    }

//    Observer<AppUpdateResultInfo> mUpdateObserver = new Observer<AppUpdateResultInfo>() {
//        @Override
//        public void onCompleted() {
//            dismissLoadingDialog();
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            dismissLoadingDialog();
//            LogUtils.d(getClass(), "observer member e.message:" + e.getMessage());
//            e.printStackTrace();
//            ToastUtil.showLong(mContext, "请求出错,错误信息:" + e.getMessage());
//
//        }
//
//        @Override
//        public void onNext(AppUpdateResultInfo resultInfo) {
//            dismissLoadingDialog();
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
//                    LogUtils.d(getClass(), "compareResult:" + compareResult);
//                    if (compareResult < 0) {
//                        UpdateManager manager = new UpdateManager(mContext);
//                        manager.showUpdateInfo(mContext, info);
//                    } else {
//                        Base.showToast(R.string.prompt_is_new_version);
//                    }
//                } else {
//                    Base.showToast(R.string.prompt_is_new_version);
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
//            ToastUtil.showShort(mContext, mContext.getString(R.string.errmsg_network_unavailable));
//            return;
//        }
//        showLoadingDialog(null);
//        Network.getCourseApi().getAppUpdate()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(mUpdateObserver);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mPhotoUtil.onActivityResult(this, requestCode, resultCode, data, this);
    }

    @Override
    public void onAccomplish(Bitmap bitmap, byte[] photoBmpData) {
        if (bitmap != null) {

//            rx.Observable.just(FileUtils.saveBitmap(bitmap, mContext, HEAD_IMAGE_NAME))
//                    .subscribeOn(Schedulers.io())
//                    .flatMap(new Func1<File, rx.Observable<MenberShipsBean>>() {
//                        @Override
//                        public rx.Observable<MenberShipsBean> call(File file) {
//                            LogUtils.d(file.exists() + file.getPath() + file.getName());
//                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                            return Network.getCourseApi("上传头像").uploadFile(Integer.valueOf(PreferencesUtils.getUserData(Base.getContext(), PreferencesUtils.KEY_USER_ID)), requestBody);
//                        }
//                    })
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(mAvatarObserver);

        }
    }

//    Observer<MenberShipsBean> mAvatarObserver = new Observer<MenberShipsBean>() {
//        @Override
//        public void onCompleted() {
//            dismissLoadingDialog();
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            dismissLoadingDialog();
//            LogUtils.d(getClass(), "observer member e.message:" + e.getMessage());
//            e.printStackTrace();
//            Base.showToast(ExceptionEngine.handleException(e).message);
//        }
//
//        @Override
//        public void onNext(MenberShipsBean bean) {
//            if (bean != null) {
//                PreferencesUtils.saveUserDataItem(mContext, PreferencesUtils.KEY_AVATAR_URL, bean.getAvatar_url());
//                refreshAvatar();
//                EventBus.getDefault().post(new AvatarChangeEventEntry());
//            }
//        }
//    };
}
