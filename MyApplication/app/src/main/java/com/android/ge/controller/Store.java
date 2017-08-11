package com.android.ge.controller;

import android.content.Context;
import android.text.TextUtils;

import com.android.base.frame.Base;
import com.android.ge.model.login.OrganResultInfo;
import com.android.ge.model.organ.OrganBean;
import com.android.ge.model.user.UserInfo;
import com.android.ge.utils.PreferencesUtils;

/**
 * Created by xudengwang on 2017/1/17.
 */

public class Store {
    public static void storeOrgan(Context context, OrganResultInfo.OrganBean organBean) {
        if (organBean != null) {
            PreferencesUtils.saveUserDataItem(context, PreferencesUtils.KEY_ORGAN_ID, organBean.getOrg_id() + "");
            PreferencesUtils.saveUserDataItem(context, PreferencesUtils.KEY_ORGAN_NAME, organBean.getName());
            PreferencesUtils.saveUserDataItem(context, PreferencesUtils.KEY_ORGAN_FULL_NAME, organBean.getFullname());
            PreferencesUtils.saveUserDataItem(context, PreferencesUtils.KEY_ORGAN_LOGO, organBean.getLogo());
//            //保存客户定制的splash图片
//            if (!TextUtils.isEmpty(organBean.getOrganization().getSplash_image_url())) {
//                PreferencesUtils.saveUserDataItem(context, PreferencesUtils.KEY_SPLASH_IMAGE_URL, organBean
// .getOrganization().getSplash_image_url());
//            }
//            //保存客户定制的banner图片
//            if (!TextUtils.isEmpty(organBean.getOrganization().getBanner_url())) {
//                PreferencesUtils.saveUserDataItem(context, PreferencesUtils.KEY_BANNER_IMAGE_URL, organBean
// .getOrganization().getBanner_url());
//            }
        }
    }

    public static void storeOrgan(Context context, OrganBean organBean) {
        if (organBean != null) {
            PreferencesUtils.saveUserDataItem(context, PreferencesUtils.KEY_ORGAN_ID, organBean.getOrg_id() + "");
            PreferencesUtils.saveUserDataItem(context, PreferencesUtils.KEY_ORGAN_NAME, organBean.getName());
            PreferencesUtils.saveUserDataItem(context, PreferencesUtils.KEY_ORGAN_LOGO, organBean.getLogo());
//            //保存客户定制的splash图片
//            if (!TextUtils.isEmpty(organBean.getOrganization().getSplash_image_url())) {
//                PreferencesUtils.saveUserDataItem(context, PreferencesUtils.KEY_SPLASH_IMAGE_URL, organBean
// .getOrganization().getSplash_image_url());
//            }
//            //保存客户定制的banner图片
//            if (!TextUtils.isEmpty(organBean.getOrganization().getBanner_url())) {
//                PreferencesUtils.saveUserDataItem(context, PreferencesUtils.KEY_BANNER_IMAGE_URL, organBean
// .getOrganization().getBanner_url());
//            }
        }
    }

    //组织id
    public static String getOrganId() {
        String organ_id = PreferencesUtils.getUserData(Base.getContext(), PreferencesUtils.KEY_ORGAN_ID);

        return organ_id;
    }

    //组织名字
    public static String getOrganName() {
        String organ_name = PreferencesUtils.getUserData(Base.getContext(), PreferencesUtils.KEY_ORGAN_NAME);
        return organ_name;
    }

    public static int getUserId() {
        String user_id = PreferencesUtils.getUserData(Base.getContext(), PreferencesUtils.KEY_USER_ID);
        if (!TextUtils.isEmpty(user_id)) {
            return Integer.valueOf(user_id);
        }
        return 0;
    }

    public static void saveToken(String token) {
        PreferencesUtils.saveUserDataItem(Base.getContext(), PreferencesUtils.KEY_TOKEN, token);
    }

    //获取token
    public static String getToken() {
        String token = PreferencesUtils.getUserData(Base.getContext(), PreferencesUtils.KEY_TOKEN);
        return token;
    }


    public static void saveUserInfo(UserInfo info) {
        if (info == null) {
            return;
        }
        PreferencesUtils.saveUserDataItem(Base.getContext(), PreferencesUtils.KEY_NAME, info.getName());
        PreferencesUtils.saveUserDataItem(Base.getContext(), PreferencesUtils.KEY_PHONE, info.getMobile());
        PreferencesUtils.saveUserDataItem(Base.getContext(), PreferencesUtils.KEY_EMAIL, info.getEmail());

    }
}
