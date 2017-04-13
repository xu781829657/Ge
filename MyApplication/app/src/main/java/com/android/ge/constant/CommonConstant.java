package com.android.ge.constant;

import android.os.Environment;

/**
 * Created by xudengwang on 17/3/29.
 */

public class CommonConstant {
    public static final String TAG_CODE = "code";

    //数据传递
    public static final String KEY_ORGAN_LIST = "key_organ_list";
    public static final String KEY_TAB_FALG = "key_tab_flag";

    public static final String KEY_TITLE = "key_title";
    public static final String KEY_COURSE_TYPE_ID = "key_course_type_id";
    public static final String KEY_TASK_BEAN = "key_task_bean";
    public static final String KEY_PATH_BEAN_LIST = "key_path_bean_list";


    //参数
    public static final String PARAM_ORG_ID = "org_id";
    public static final String PARAM_COURSE_TYPE_ID = "course_type_id";

    public static final String PARAM_COURSE_STATE = "course_state";

    public static final String PARAM_PATH_ID = "path_id";
    public static final String PARAM_TOKEN = "token";
    public static final String PARAM_COURSE_ID = "course_id";
    public static final String PARAM_TIME = "time";


    public static final String TASK_COURSE_TYPE_COURSE = "Course";
    public static final String TASK_COURSE_TYPE_QUIZ = "Quiz";
    public static final String TASK_COURSE_TYPE_SURVEY = "Survey";

    public static final String ROOT_CACHE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/" + "com.android.ge";
}
