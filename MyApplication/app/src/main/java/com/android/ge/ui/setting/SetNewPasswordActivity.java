package com.android.ge.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.frame.AppManager;
import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.ge.R;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.ui.base.CommonBaseActivity;
import com.android.ge.ui.login.LoginActivity;
import com.android.ge.utils.PreferencesUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xudengwang on 2016/10/27.
 */

public class SetNewPasswordActivity extends CommonBaseActivity {

    @Bind(R.id.tiedt_new_password)
    TextInputEditText mEtNew;

    @Bind(R.id.tiedt_new_password_again)
    TextInputEditText mEtNewAgain;

    @Bind(R.id.check_show_new_pass)
    CheckBox mCheckNewPass;

    @Bind(R.id.check_show_new_pass_again)
    CheckBox mCheckNewPassAgain;

    @Bind(R.id.btn_commit)
    Button mBtnCommit;

    private String mAccount;
    private String mVercode;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_set_new_password;
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mAccount = bundle.getString("account");
            mVercode = bundle.getString("code");
        }

        ImageView backIv = (ImageView) findViewById(R.id.iv_back);
        TextView titleTv = (TextView) findViewById(R.id.tv_title);
        if (TextUtils.isEmpty(mVercode)) {
            titleTv.setText(Base.string(R.string.title_set_password));
        } else {
            titleTv.setText(Base.string(R.string.title_find_password));
        }

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPassword(mEtNew.getText().toString()) && checkPassword(mEtNewAgain.getText().toString())
                        && checPassAgain(mEtNew.getText().toString(), mEtNewAgain.getText().toString())) {
                    putNetDataSetPass();
                }
            }
        });


        mCheckNewPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // 显示密码
                    mEtNew.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    // 隐藏密码
                    mEtNew.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                mEtNew.setSelection(mEtNew.getText().toString().length());

            }
        });


        mCheckNewPassAgain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {

                    mEtNewAgain.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {

                    mEtNewAgain.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                mEtNewAgain.setSelection(mEtNewAgain.getText().toString().length());

            }
        });

    }


    private boolean checkInput() {
        return checkPassword(mEtNew.getText().toString()) && checkPassword(mEtNewAgain.getText().toString())
                && checPassAgain(mEtNew.getText().toString(), mEtNewAgain.getText().toString());

    }

    private boolean checkPassword(String pass) {
        if (TextUtils.isEmpty(pass)) {
            Base.showToast(R.string.msg_password_null);
            return false;
        }

        if (pass.length() < 6 || pass.length() > 20) {
            Base.showToast(R.string.msg_password_length_error);
            return false;
        }
        return true;
    }


    private boolean checPassAgain(String pass1, String pass2) {
        if (!pass1.equalsIgnoreCase(pass2)) {
            Base.showToast(R.string.msg_password_again_error);
            return false;
        }
        return true;

    }


    //结果
    Observer<String> mResultObserver = new Observer<String>() {
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
        public void onNext(String bean) {
            LogUtils.d(getClass(), "bean:" + bean);
            dismissLoadingDialog();
            Base.showToast(R.string.msg_set_password_success);
            PreferencesUtils.clearUserData(mContext);
            gotoActivity(LoginActivity.class, Intent.FLAG_ACTIVITY_SINGLE_TOP);
            AppManager.create().finishAllActivity();

        }
    };

    //提交重置密码的put请求
    private void putNetDataSetPass() {
        if (!NetworkUtil.isAvailable(mContext)) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("identity", mAccount);
        map.put("code", mVercode);
        map.put("password", mEtNew.getText().toString());
        map.put("password_confirmation", mEtNew.getText().toString());

        showLoadingDialog(null);
//        Network.getCourseApi().putSetPassword(map)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(mResultObserver);
    }
}
