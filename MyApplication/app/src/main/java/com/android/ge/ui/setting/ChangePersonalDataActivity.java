package com.android.ge.ui.setting;

import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.base.util.NetworkUtil;
import com.android.ge.R;
import com.android.ge.ui.base.CommonBaseActivity;
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

public class ChangePersonalDataActivity extends CommonBaseActivity {

    @Bind(R.id.tiedt_last_name)
    TextInputEditText mLastNameEdt;
    @Bind(R.id.tiedt_first_name)
    TextInputEditText mFirstNameEdt;
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

        mFirstNameEdt.setText(PreferencesUtils.getUserData(mContext, PreferencesUtils.KEY_FIRST_NAME));
        mLastNameEdt.setText(PreferencesUtils.getUserData(mContext, PreferencesUtils.KEY_LAST_NAME));
        mEmailEdt.setText(PreferencesUtils.getUserData(mContext, PreferencesUtils.KEY_EMAIL));
        mPhoneEdt.setText(PreferencesUtils.getUserData(mContext, PreferencesUtils.KEY_PHONE));
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_change_personal_data;
    }


    //检查输入
    private boolean checkInput() {
        if (TextUtils.isEmpty(mFirstNameEdt.getText().toString())) {
            Base.showToast(R.string.name_null);
            return false;
        }
        if (TextUtils.isEmpty(mLastNameEdt.getText().toString())) {
            Base.showToast(R.string.xing_null);
            return false;
        }
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
//    Observer<MenberShipsBean> mResultObserver = new Observer<MenberShipsBean>() {
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
//        public void onNext(MenberShipsBean bean) {
//            dismissLoadingDialog();
//            if (bean != null) {
//                if(!TextUtils.isEmpty(bean.getError())){
//                    Base.showToast(bean.getError());
//                    return;
//                }
//                Base.showToast(R.string.msg_modify_success);
//                if (bean.getOrganization() != null) {
//                    PreferencesUtils.saveUserDataItem(mContext, PreferencesUtils.KEY_ORGAN_ID, bean.getOrganization().getId() + "");
//                }
//                PreferencesUtils.saveUserDataItem(mContext, PreferencesUtils.KEY_USER_ID, bean.getId()+"");
//                PreferencesUtils.saveUserDataItem(mContext, PreferencesUtils.KEY_AVATAR_URL, bean.getAvatar_url());
//                PreferencesUtils.saveUserDataItem(mContext, PreferencesUtils.KEY_EMAIL, bean.getEmail());
//                PreferencesUtils.saveUserDataItem(mContext, PreferencesUtils.KEY_PHONE, bean.getPhone());
//                PreferencesUtils.saveUserDataItem(mContext, PreferencesUtils.KEY_FIRST_NAME, bean.getFirst_name());
//                PreferencesUtils.saveUserDataItem(mContext, PreferencesUtils.KEY_LAST_NAME, bean.getLast_name());
//            }
//
//        }
//    };

    //提交修改的put请求
    private void putNetDataPersonalData() {
        if (!NetworkUtil.isAvailable(mContext)) {
            Base.showToast(R.string.errmsg_network_unavailable);
            return;
        }


        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(mFirstNameEdt.getText().toString())) {
            map.put("first_name", mFirstNameEdt.getText().toString());
        }
        if (!TextUtils.isEmpty(mLastNameEdt.getText().toString())) {
            map.put("last_name", mLastNameEdt.getText().toString());
        }
        if (!TextUtils.isEmpty(mEmailEdt.getText().toString())) {
            map.put("email", mEmailEdt.getText().toString());
        }
        if (!TextUtils.isEmpty(mPhoneEdt.getText().toString())) {
            map.put("phone", mPhoneEdt.getText().toString());
        }

        if (map.size() == 0) {
            Base.showToast(R.string.msg_no_data_commit);
            return;
        }
        showLoadingDialog(null);

//        Network.getCourseApi().putPersonalData(Integer.valueOf(PreferencesUtils.getUserData(Base.getContext(), PreferencesUtils.KEY_USER_ID)), map)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(mResultObserver);
    }


}
