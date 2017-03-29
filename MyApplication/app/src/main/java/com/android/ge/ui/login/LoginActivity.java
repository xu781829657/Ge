package com.android.ge.ui.login;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.ge.R;
import com.android.ge.model.login.LoginResultInfo;
import com.android.ge.network.Network;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.ui.base.CommonBaseActivity;
import com.android.ge.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xudengwang on 17/3/28.
 */

public class LoginActivity extends CommonBaseActivity {

    @Bind(R.id.tiedt_login_account)
    TextInputEditText mAccountEdt;
    @Bind(R.id.tiedt_login_pass)
    TextInputEditText mPassEdt;
    @Bind(R.id.btn_login)
    Button mLoginBtn;

    @Bind(R.id.tv_forget_pass)
    TextView mForgetTv;

    @Bind(R.id.iv_login_logo)
    ImageView mIvLogo;

    private int hideNum = 0;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

        mForgetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                gotoActivity(FindPasswordVerifyActivity.class, false);
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mAccountEdt.getText().toString();
                String password = mPassEdt.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Base.showToast(R.string.login_name_null);
                    mAccountEdt.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Base.showToast(R.string.login_password_null);
                    mPassEdt.requestFocus();
                    return;
                }

                postNetDataLogin();
            }
        });
        mAccountEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(mAccountEdt.toString()) || TextUtils.isEmpty(mPassEdt.getText().toString())) {
                    mLoginBtn.setEnabled(false);
                } else {
                    mLoginBtn.setEnabled(true);
                }

            }
        });

        mPassEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(mAccountEdt.toString()) || TextUtils.isEmpty(mPassEdt.getText().toString())) {
                    mLoginBtn.setEnabled(false);
                } else {
                    mLoginBtn.setEnabled(true);
                }

            }
        });


        mPassEdt.setTransformationMethod(PasswordTransformationMethod.getInstance());

    }

    //课程数观察器
    Observer<LoginResultInfo> mTokenObserver = new Observer<LoginResultInfo>() {
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
            //httpcode 密码错误
        }

        @Override
        public void onNext(LoginResultInfo resultInfo) {
            if (resultInfo != null) {
                //保存token
                PreferencesUtils.saveUserDataItem(Base.getContext(), PreferencesUtils.KEY_TOKEN, resultInfo.getData().getToken());
            }


            //getNetDataMemberships();

        }
    };

    //获取课程数
    private void postNetDataLogin() {
        if (!NetworkUtil.isAvailable(mContext)) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }
        showLoadingDialog(null);

        Map<String, String> map = new HashMap<>();
        map.put("email", mAccountEdt.getText().toString());
        map.put("password", mPassEdt.getText().toString());

        Network.getCourseApi("登录").postLoginData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mTokenObserver);
    }


}
