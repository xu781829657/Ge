package com.android.ge.ui.setting;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.frame.AppManager;
import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.ge.R;
import com.android.ge.controller.Store;
import com.android.ge.network.Network;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.network.response.ServerResponseFunc;
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

public class ChangePasswordActivity extends CommonBaseActivity {
    @Bind(R.id.tiedt_old_password)
    TextInputEditText mEtOld;
    @Bind(R.id.tiedt_new_password)
    TextInputEditText mEtNew;
    @Bind(R.id.tiedt_new_password_again)
    TextInputEditText mEtNewAgain;

    @Bind(R.id.check_show_new_pass)
    CheckBox mCheckNewPass;

    @Bind(R.id.check_show_new_pass_again)
    CheckBox mCheckNewPassAgain;

    @Override
    protected void initData() {
        ImageView backIv = (ImageView) findViewById(R.id.iv_back);
        TextView titleTv = (TextView) findViewById(R.id.tv_title);
        titleTv.setText(Base.string(R.string.title_set_password));
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView commitTv = (TextView) findViewById(R.id.tv_title_commit);
        commitTv.setText(Base.string(R.string.save));
        commitTv.setVisibility(View.VISIBLE);
        commitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInput()) {
                    putNetDataChangePass();
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

    @Override
    protected int getContentViewId() {
        return R.layout.activity_change_password;
    }


    private boolean checkInput() {
        return checkOldPassword(mEtOld.getText().toString())
                && checkNewPassword(mEtNew.getText().toString()) && checkNewPassword(mEtNewAgain.getText().toString())
                && checPassAgain(mEtNew.getText().toString(), mEtNewAgain.getText().toString());

    }

    private boolean checkOldPassword(String pass) {
        if (TextUtils.isEmpty(pass)) {
            Base.showToast(R.string.msg_old_password_null);
            return false;
        }

        if (pass.length() < 6 || pass.length() > 20) {
            Base.showToast(R.string.msg_old_password_length_error);
            return false;
        }
        return true;
    }

    private boolean checkNewPassword(String pass) {
        if (TextUtils.isEmpty(pass)) {
            Base.showToast(R.string.msg_new_password_null);
            return false;
        }

        if (pass.length() < 6 || pass.length() > 20) {
            Base.showToast(R.string.msg_new_password_length_error);
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
            LogUtils.d(getClass(), "bean:" + bean);
            dismissLoadingDialog();
            Base.showToast(R.string.msg_change_password_success);
            PreferencesUtils.clearUserData(mContext);
            gotoActivity(LoginActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
            AppManager.create().finishAllActivity();
        }
    };

    //提交修改的put请求
    private void putNetDataChangePass() {
        if (!NetworkUtil.isAvailable(mContext)) {
            Base.showToast(Base.string(R.string.errmsg_network_unavailable));
            return;
        }
        showLoadingDialog(null);
        Map<String, String> map = new HashMap<>();
        map.put("oldpassword", mEtOld.getText().toString());
        map.put("newpassword", mEtNew.getText().toString());
        map.put("newpassword_confirm", mEtNewAgain.getText().toString());

        Network.getCourseApi("个人中心-修改密码").postChangePassword(map)
                .subscribeOn(Schedulers.io())
                //拦截服务器返回的错误
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ServerResponseFunc<Object>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mResultObserver);
    }

}
