package com.android.ge.constant;

import android.os.Environment;

/**
 * Created by xudengwang on 17/3/29.
 */

public class CommonConstant {
    public static final String TAG_CODE = "code";

    //数据传递
    public static final String KEY_ORGAN_LIST = "key_organ_list";


    //参数
    public static final String PARAM_ORG_ID="org_id";

    public static final String ROOT_CACHE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/" + "com.android.ge";
}
