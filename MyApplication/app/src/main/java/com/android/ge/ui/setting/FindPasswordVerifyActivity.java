package com.android.ge.ui.setting;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;
import com.android.base.util.NetworkUtil;
import com.android.ge.R;
import com.android.ge.network.error.ExceptionEngine;
import com.android.ge.ui.base.CommonBaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xudengwang on 2016/10/27.
 */

public class FindPasswordVerifyActivity extends CommonBaseActivity {
    @Bind(R.id.tiedt_account)
    TextInputEditText mEtAccount;

    @Bind(R.id.tiedt_verify_code)
    TextInputEditText mEtCode;

    @Bind(R.id.tv_send_verify)
    TextView mTvSend;

    @Bind(R.id.tv_tips)
    TextView mTvTips;

    @Bind(R.id.btn_next)
    Button mBtnNext;

    private TimeCount mTimeCount;

    @Override
    protected void initData() {
        ImageView backIv = (ImageView) findViewById(R.id.iv_back);
        TextView titleTv = (TextView) findViewById(R.id.tv_title);
        titleTv.setText(Base.string(R.string.title_forgot_password));
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAccountInput()) {
                    mTimeCount = new TimeCount(60000, 1000);
                    mTimeCount.start();
                    mTvSend.setClickable(false);
                    mTvSend.setEnabled(false);
                    if (mTvTips.getVisibility() == View.GONE) {
                        mTvTips.setVisibility(View.VISIBLE);
                    }
                    putNetDataSendSms();
                }

            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAccountInput() && checkVerifyInput()) {
                    getNetDataCheckSms();
                }
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_change_password_verify;
    }

    //验证码发送结果
    Observer<String> mSendResultObserver = new Observer<String>() {
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

            mTvSend.setText(Base.string(R.string.resend_verify));
            mTvSend.setClickable(true);
            mTvSend.setEnabled(true);
            if (mTimeCount != null) {
                mTimeCount.cancel();
                mTimeCount = null;
            }
        }

        @Override
        public void onNext(String bean) {
            LogUtils.d(getClass(), "bean:" + bean);
            dismissLoadingDialog();

//            if (!TextUtils.isEmpty(bean)) {
//                try {
//                    Base.showToast(JsonParseUtil.jsonToString(bean, TagConstant.TAG_ERROR));
//                    return;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            } else {
//                Base.showToast(R.string.msg_send_verify_success);
//            }


        }
    };

    //检查结果
//    Observer<ResultBean> mCheckResultObserver = new Observer<ResultBean>() {
//        @Override
//        public void onCompleted() {
//            dismissLoadingDialog();
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            LogUtils.d(getClass(), "observer course e.message:" + e.getMessage());
//            e.printStackTrace();
//            dismissLoadingDialog();
//            Base.showToast(ExceptionEngine.handleException(e).message);
//        }
//
//        @Override
//        public void onNext(ResultBean bean) {
//            LogUtils.d(getClass(), "bean:" + bean);
//            if (bean.getResult()) {
//                Bundle bundle = new Bundle();
//                bundle.putString("account", mEtAccount.getText().toString());
//                bundle.putString("code", mEtCode.getText().toString());
//                gotoActivity(SetNewPasswordActivity.class, bundle, false);
//            } else {
//                Base.showToast(R.string.msg_vercode_error);
//            }
//        }
//    };

    //发送验证码
    private void putNetDataSendSms() {
        if (!NetworkUtil.isAvailable(mContext)) {
            Base.showToast(R.string.errmsg_network_unavailable);
            mTimeCount.cancel();
            mTvSend.setText(Base.string(R.string.send_verify));
            mTvSend.setClickable(true);
            mTvSend.setEnabled(true);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("identity", mEtAccount.getText().toString());
        showLoadingDialog(null);

//        Network.getCourseApi().putSendSms(map)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(mSendResultObserver);
    }


    //检查验证码
    private void getNetDataCheckSms() {
        if (!NetworkUtil.isAvailable(mContext)) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("identity", mEtAccount.getText().toString());
        map.put("code", mEtCode.getText().toString());

        showLoadingDialog(null);
//        Network.getCourseApi().getCheckSms(map)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(mCheckResultObserver);
    }

    private boolean checkAccountInput() {
        if (TextUtils.isEmpty(mEtAccount.getText().toString())) {
            Base.showToast(R.string.msg_account_null);
            return false;
        }
        return true;
    }

    private boolean checkVerifyInput() {
        if (TextUtils.isEmpty(mEtCode.getText().toString())) {
            Base.showToast(R.string.msg_vercode_null);
            return false;
        }
        return true;
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTvSend.setText(String.format(Base.string(R.string.format_resend_verify), millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            mTvSend.setText(Base.string(R.string.resend_verify));
            mTvSend.setClickable(true);
            mTvSend.setEnabled(true);
            if (mTimeCount != null) {
                mTimeCount.cancel();
                mTimeCount = null;
            }
        }
    }

}
