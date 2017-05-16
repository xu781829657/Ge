package com.android.ge.controller.web;

import android.webkit.JavascriptInterface;

import com.android.base.frame.Base;
import com.android.base.util.NetworkUtil;

/**
 * Created by xudengwang on 17/4/24.
 */

public class AndroidBridge {

    @JavascriptInterface
    public void callAndroid(final String arg) {

    }

    /**
     * 获取app网络类型
     * UnKnown(-1),
     * Wifi(1),
     * Net2G(2),
     * Net3G(3),
     * Net4G(4);
     */
    @JavascriptInterface
    public int getNetWorkType() {
        Base.showToast("获取app网络类型");
        return NetworkUtil.getNetworkType(Base.getContext()).value;
    }
}
