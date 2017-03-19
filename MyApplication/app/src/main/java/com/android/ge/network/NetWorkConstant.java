package com.android.ge.network;

import com.iwexun.iweixun.base.MyApplication;
import com.iwexun.iweixun.constant.AppConfig;

/**
 * Created by xudengwang on 2016/10/8.
 */

public class NetWorkConstant {
    //online
    public static String BASE_URL = "https://api.iwexun.com/";
    //test
    //public static String BASE_URL = "http://api-staging.iwexun.com";

    static {
//        if (MyApplication.getInstance().ENVIRONMENT == AppConfig.ENVIRONMENT_PRO) {  //生产环境
//            BASE_URL = "https://api.iwexun.com";
//        } else if (MyApplication.getInstance().ENVIRONMENT == AppConfig.ENVIRONMENT_DEV) { //测试环境
//            BASE_URL = "http://api-staging.iwexun.com";
//        }
    }
}
