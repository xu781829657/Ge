package com.android.ge.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;

import java.util.Locale;

import static com.android.base.frame.Base.getResources;

/**
 * Created by xudengwang on 2016/12/20.
 */


public class DeviceUtil {
    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName() {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = Base.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(Base.getContext().getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(DateUtils.class, "Exception"+ e.getMessage());
        }
        return versionName;
    }
    /**
     * 获取屏幕分辨率
     */
    public static int[] getScreenSize(Resources resources) {
        int width = resources.getDisplayMetrics().widthPixels;
        int height = resources.getDisplayMetrics().heightPixels;
        int[] result = new int[2];
        result[0] = width;
        result[1] = height;
        return result;
    }

    public static int getPixelFromDip(Context context, float dip) {
        return getPixelFromDip(context.getResources().getDisplayMetrics(), dip);
    }

    public static double calculateScreenSize(DisplayMetrics outMetrics) {
        double x = Math.pow(outMetrics.widthPixels / outMetrics.xdpi, 2);
        double y = Math.pow(outMetrics.heightPixels / outMetrics.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        return screenInches;
    }

    public static double calculateScreenSize(Context context) {
        DisplayMetrics outMetrics = context.getResources().getDisplayMetrics();
        return calculateScreenSize(outMetrics);
    }

    /**
     *
     */
    public static int getPixelFromDip(DisplayMetrics dm, float dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, dm) + 0.5f);
    }

    /**
     * 获取手机型号
     */
    public static String getDeviceModel() {
        String model = Build.MODEL;

        if (model == null) {
            return "";
        } else {
            return model;
        }
    }

    /**
     */
    @SuppressWarnings("deprecation")
    public static String getSDKVersion() {
        return Build.VERSION.SDK;
    }

    /**
     */
    public static int getSDKVersionInt() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * @return
     */
    public static boolean isSdCardExist() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * @return IMEI
     */
    public static String getIMEI(Context context) {
        TelephonyManager teleMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return teleMgr.getDeviceId();
    }

    /**
     * @return IMSI
     */
    public static String getIMSI(Context context) {
        TelephonyManager teleMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return teleMgr.getSubscriberId();
    }

    /**
     * wifi mac + imei + cpu serial
     *
     * @return
     */
    public static String getMobileUUID(Context context) {
        String uuid = "";
        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr != null) {
            WifiInfo info = wifiMgr.getConnectionInfo();
            if (info != null && info.getMacAddress() != null) {
                uuid = info.getMacAddress().replace(":", "");
            }
        }

        TelephonyManager teleMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = teleMgr.getDeviceId();
        uuid += imei;

        if (uuid != null && uuid.length() > 64) {
            uuid = uuid.substring(0, 64);
        }
        return uuid;
    }

    /**
     * 获取手机品牌 + 型号
     *
     * @return
     */
    public static String getBrandModel() {
        return Build.BRAND + "," + Build.MODEL;
    }


    public static boolean localLanguageIsZh(){
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }
}