package com.android.ge.ui.tabmain;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.controller.Store;
import com.android.ge.controller.entry.AvatarEntry;
import com.android.ge.controller.entry.BannerEntry;
import com.android.ge.model.LearningInfo;
import com.android.ge.model.user.UserInfo;
import com.android.ge.network.Network;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.network.response.ServerResponseFunc;
import com.android.ge.ui.base.CommonBaseFragment;
import com.android.ge.ui.setting.AboutActivity;
import com.android.ge.ui.setting.ChangePasswordActivity;
import com.android.ge.ui.setting.ChangePersonalDataActivity;
import com.android.ge.ui.setting.LearningSummaryActivity;
import com.android.ge.ui.setting.PersonalCenterActivity;
import com.android.ge.ui.setting.RankActivity;
import com.android.ge.utils.PreferencesUtils;
import com.android.ge.utils.image.ImageLoader;
import com.android.ge.utils.image.ImageRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import cn.carbs.android.avatarimageview.library.AvatarImageView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xudengwang on 17/3/18.
 */

public class MeFragment extends CommonBaseFragment {
    @Bind(R.id.iv_setting)
    ImageView mIvSetting;
    @Bind(R.id.aiv_avatar)
    AvatarImageView mAivHeader;
    @Bind(R.id.tv_title)
    TextView mTvTitle;

    @Bind(R.id.lin_my_list)
    LinearLayout mLinMyList;

    @Bind(R.id.lin_learn_summary)
    LinearLayout mLinlearnSummary;

    @Bind(R.id.rel_change_password)
    RelativeLayout mRelChangePass;

    @Bind(R.id.rel_personal_info)
    RelativeLayout mRelPersonalInfo;

    @Bind(R.id.rel_about)
    RelativeLayout mRelAbout;

    @Override
    public int getContentViewId() {
        return R.layout.fm_me;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        setListener();
        getNetDataUserInfo();
    }

    private void setListener() {
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


        mRelChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(ChangePasswordActivity.class, false);
            }
        });

        mRelPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(ChangePersonalDataActivity.class, false);
            }
        });
        mRelAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(AboutActivity.class, false);
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void refreshView() {
        String avatar_url = PreferencesUtils.getUserData(Base.getContext(), PreferencesUtils.KEY_AVATAR_URL);
        LogUtils.d(getClass(), "avatar_url:" + avatar_url);
        if (!TextUtils.isEmpty(avatar_url)) {
            ImageRequest imageRequest = new ImageRequest.Builder().imgView(mAivHeader).placeHolder(R.drawable
                    .icon_head).url(avatar_url).create();
            ImageLoader.getProvider().loadImage(imageRequest);
        }
        String name = PreferencesUtils.getUserData(Base.getContext(), PreferencesUtils.KEY_NAME);
        if (!TextUtils.isEmpty(name)) {
            mTvTitle.setText(name);
        }

    }


    //课程分类
    Observer<UserInfo> mUserObserver = new Observer<UserInfo>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            LogUtils.d(getClass(), "observer course e.message:" + e.getMessage());
            e.printStackTrace();
            Base.showToast(ExceptionEngine.handleException(e, getActivity()).message);
        }

        @Override
        public void onNext(UserInfo resultInfo) {
            if (resultInfo == null) {
                Base.showToast(R.string.errmsg_data_error);
            }
            Store.saveUserInfo(resultInfo);
            refreshView();
        }
    };

    //登录
    private void getNetDataUserInfo() {
        if (!NetworkUtil.isAvailable(getMContext())) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put(CommonConstant.PARAM_ORG_ID, Store.getOrganId());

        Network.getCourseApi("tab_我的个人资料").getUserInfo(map)
                .subscribeOn(Schedulers.io())
                //拦截服务器返回的错误
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ServerResponseFunc<UserInfo>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mUserObserver);
    }

    // 展示app更新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshAvatarEntry(AvatarEntry eventEntry) {
        LogUtils.d("refreshAvatarEntry!!!");
        refreshView();
    }
}
