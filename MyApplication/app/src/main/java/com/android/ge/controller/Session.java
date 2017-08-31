package com.android.ge.controller;

import android.os.Build;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.android.base.frame.Base;
import com.android.base.util.LogUtils;

import java.util.HashMap;
import java.util.logging.LogManager;

/**
 * Created by xudengwang on 2017/8/25.
 */

public class Session {
    /**
     * 将cookie同步到WebView
     *
     * @param url   WebView要加载的url
     * @param token 要同步的cookie
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public static boolean syncCookie(String url, String token) {
        if (TextUtils.isEmpty(token)) {
            return false;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(Base.getContext());
        }
        String cookieStr = "token=" + token;
        LogUtils.d("cookieStr:" + cookieStr);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, cookieStr);//如果没有特殊需求，这里只需要将session id以"key=value"形式作为cookie即可
        String newCookie = cookieManager.getCookie(url);
        LogUtils.d("newCookie:" + newCookie);
        return TextUtils.isEmpty(newCookie) ? false : true;
    }
}
