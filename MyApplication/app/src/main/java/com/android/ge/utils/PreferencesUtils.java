package com.android.ge.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.base.util.LogUtils;
import com.android.ge.constant.AppConfig;


@SuppressWarnings("unused")
public class PreferencesUtils {

    public static final String PREF_USER_INFO = "pref_user_info";

    public static final String PREF_FIRST = "pref_first_enter";
    public static final String PREF_FILTER = "pref_filter";
    public static final String PREF_UPDATE = "pref_update";
    public static final String PREF_ENVIRONMENT = "pref_environment";


    public static final String KEY_COURSER_FILTER_PROCEED_POS = "key_proceed_filter";
    public static final String KEY_COURSER_FILTER_FINISHED_POS = "key_finished_filter";
    public static final String KEY_COURSER_FILTER_ALL_POS = "key_ALL_filter";


    public static final String KEY_TOKEN = "key_token";
    public static final String KEY_EMAIL = "key_email";
    public static final String KEY_PHONE = "key_phone";
    public static final String KEY_LAST_NAME = "key_last_name";
    public static final String KEY_FIRST_NAME = "key_first_name";
    public static final String KEY_NAME = "key_first_name";
    public static final String KEY_AVATAR_URL = "key_avatar_url";
    public static final String KEY_ORGAN_ID = "key_organization_id";
    public static final String KEY_ORGAN_NAME = "key_organization_name";
    public static final String KEY_ORGAN_FULL_NAME = "key_organization_full_name";
    public static final String KEY_ORGAN_LOGO = "key_organization_logo";
    public static final String KEY_USER_ID = "key_user_id";
    public static final String KEY_SPLASH_IMAGE_URL ="key_splash_url";
    public static final String KEY_BANNER_IMAGE_URL ="key_banner_url";

    public static final String KEY_UPDATE = "key_update";
    public static final String KEY_ENVIRONMENT  = "key_environment";



    public static String getUserData(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_USER_INFO,
                Context.MODE_PRIVATE);
        String value = sp.getString(key, "");
        return value;
    }

    public static int getUserDataByInt(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_USER_INFO,
                Context.MODE_PRIVATE);
        int value = sp.getInt(key, 0);
        return value;
    }

    public static void saveUserDataItem(Context ctx, String key, String value) {
        LogUtils.d(PreferencesUtils.class, "saveUserDataItem key:" + key
                + ",value:" + value);
        if (value == null) {
            value = "";
        }
        SharedPreferences sp = ctx.getSharedPreferences(PREF_USER_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void saveUserDataItemByInt(Context ctx, String key, int value) {
        LogUtils.d(PreferencesUtils.class, "saveUserDataItem key:" + key
                + ",value:" + value);
        SharedPreferences sp = ctx.getSharedPreferences(PREF_USER_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    public static void clearUserData(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_USER_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }


    public static void saveFilter(Context ctx, String key, int filter_position) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_FILTER,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, filter_position);
        editor.commit();
    }

    public static int getFilter(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_FILTER,
                Context.MODE_PRIVATE);
        int value = sp.getInt(key, -1);
        return value;
    }

    public static void clearFilter(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_FILTER,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }


    public static void saveUpdate(Context ctx,boolean has_update) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_UPDATE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY_UPDATE, has_update);
        editor.commit();
    }

    public static boolean getUpdate(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_UPDATE,
                Context.MODE_PRIVATE);
        boolean value = sp.getBoolean(KEY_UPDATE, false);
        return value;
    }

    public static void saveEnvironment(Context ctx,int value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_ENVIRONMENT,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(KEY_ENVIRONMENT, value);
        editor.commit();
    }

    public static int getEnvironment(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_ENVIRONMENT,
                Context.MODE_PRIVATE);
        int value = sp.getInt(KEY_ENVIRONMENT, AppConfig.ENVIRONMENT_DEFAULT);
        return value;
    }

}
