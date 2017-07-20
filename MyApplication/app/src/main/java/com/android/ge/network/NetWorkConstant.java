package com.android.ge.network;


import com.android.ge.app.AppApplication;
import com.android.ge.constant.AppConfig;

/**
 * Created by xudengwang on 2016/10/8.
 */

public class NetWorkConstant {
    //online
    //public static String BASE_URL = "http://106.14.62.138/";

    //public static String BASE_URL = "http://106.14.62.138:81";
    //public static String BASE_URL = "http://106.14.62.138:81";
    //演示环境
    public static String BASE_URL = "http://admin.goelite.cn:8090/ ";
    //test
    //public static String BASE_URL = "http://api-staging.iwexun.com";

    static {
        if (AppApplication.getInstance().ENVIRONMENT == AppConfig.ENVIRONMENT_PRO) {  //生产环境
            BASE_URL = "http://admin.goelite.cn:8090/";
        } else if (AppApplication.getInstance().ENVIRONMENT == AppConfig.ENVIRONMENT_DEV) { //测试环境
            BASE_URL = "http://106.14.62.138:81";
        }
    }
}
