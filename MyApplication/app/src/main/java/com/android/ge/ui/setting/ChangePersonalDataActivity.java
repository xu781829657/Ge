package com.android.ge.ui.setting;

import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.ge.R;
import com.android.ge.controller.entry.AvatarEntry;
import com.android.ge.model.user.UserInfo;
import com.android.ge.network.Network;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.network.response.ServerResponseFunc;
import com.android.ge.ui.base.CommonBaseActivity;
import com.android.ge.utils.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xudengwang on 2016/10/27.
 */

public class ChangePersonalDataActivity extends CommonBaseActivity {

    //    @Bind(R.id.tiedt_last_name)
//    TextInputEditText mLastNameEdt;
    @Bind(R.id.tiedt_name)
    TextInputEditText mNameEdt;
    @Bind(R.id.tiedt_phone)
    TextInputEditText mPhoneEdt;
    @Bind(R.id.tiedt_email)
    TextInputEditText mEmailEdt;

    @Override
    protected void initData() {
        mContext = this;
        ImageView backIv = (ImageView) findViewById(R.id.iv_back);
        TextView titleTv = (TextView) findViewById(R.id.tv_title);
        titleTv.setText(Base.string(R.string.title_change_persona_data));
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView commintTv = (TextView) findViewById(R.id.tv_title_commit);
        commintTv.setText(Base.string(R.string.commit));
        commintTv.setVisibility(View.VISIBLE);
        commintTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInput()) {
                    putNetDataPersonalData();
                }

            }
        });

        mNameEdt.setText(PreferencesUtils.getUserData(mContext, PreferencesUtils.KEY_FIRST_NAME));
        mEmailEdt.setText(PreferencesUtils.getUserData(mContext, PreferencesUtils.KEY_EMAIL));
        mPhoneEdt.setText(PreferencesUtils.getUserData(mContext, PreferencesUtils.KEY_PHONE));
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_change_personal_data;
    }


    //检查输入
    private boolean checkInput() {
        if (TextUtils.isEmpty(mNameEdt.getText().toString())) {
            Base.showToast(R.string.name_null);
            return false;
        }
//        if (TextUtils.isEmpty(mLastNameEdt.getText().toString())) {
//            Base.showToast(R.string.xing_null);
//            return false;
//        }
        if (TextUtils.isEmpty(mPhoneEdt.getText().toString())) {
            Base.showToast(R.string.phone_null);
            return false;
        }
        if (TextUtils.isEmpty(mEmailEdt.getText().toString())) {
            Base.showToast(R.string.email_null);
            return false;
        }
        return true;
    }


    //结果
    Observer<Object> mResultObserver = new Observer<Object>() {
        @Override
        public void onCompleted() {
            dismissLoadingDialog();
        }

        @Override
        public void onError(Throwable e) {
            LogUtils.d(getClass(), "observer course e.message:" + e.getMessage());
            e.printStackTrace();
            dismissLoadingDialog();
            Base.showToast(ExceptionEngine.handleException(e).message);
        }

        @Override
        public void onNext(Object bean) {
            dismissLoadingDialog();
            PreferencesUtils.saveUserDataItem(mContext, PreferencesUtils.KEY_EMAIL, mEmailEdt.getText().toString());
            PreferencesUtils.saveUserDataItem(mContext, PreferencesUtils.KEY_PHONE, mPhoneEdt.getText().toString());
            PreferencesUtils.saveUserDataItem(mContext, PreferencesUtils.KEY_NAME, mNameEdt.getText().toString());
            EventBus.getDefault().post(new AvatarEntry());
            Base.showToast(R.string.toast_success_edt_user_info);
            finish();
        }
    };

    //提交修改的put请求
    private void putNetDataPersonalData() {
        if (!NetworkUtil.isAvailable(mContext)) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }

        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(mNameEdt.getText().toString())) {
            map.put("name", mNameEdt.getText().toString());
        }
        if (!TextUtils.isEmpty(mEmailEdt.getText().toString())) {
            map.put("email", mEmailEdt.getText().toString());
        }
        if (!TextUtils.isEmpty(mPhoneEdt.getText().toString())) {
            map.put("mobile", mPhoneEdt.getText().toString());
        }

        if (map.size() == 0) {
            Base.showToast(R.string.msg_no_data_commit);
            return;
        }
        showLoadingDialog(null);

        Network.getCourseApi("修改个人资料").postEditUserInfo(map)
                .subscribeOn(Schedulers.io())
                //拦截服务器返回的错误
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ServerResponseFunc<Object>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mResultObserver);
    }


}
