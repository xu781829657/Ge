package com.android.ge.model;

import java.util.List;

/**
 * Created by xudengwang on 17/4/8.
 *     "cat_id": 1,
 "cat_name": "英语课程",
 "org_id": 1,
 "parent_id": 0,
 "is_leaf": 0,
 "p_order": 1,
 "created_at": null,
 "updated_at": null,
 */

public class CourseClassifyInfo {
    private String cat_id;
    private String cat_name;
//    private int course_type_id;
//    private int org_id;
//    private int type;
//    private String name;
//    private String code;
//    private long created_at;
//    private long updated_at;

    public List<CourseBean> courses;

}
