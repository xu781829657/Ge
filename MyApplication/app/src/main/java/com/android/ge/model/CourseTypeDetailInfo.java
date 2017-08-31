package com.android.ge.model;

import java.util.List;

/**
 * Created by xudengwang on 17/6/25.
 */

public class CourseTypeDetailInfo extends BaseCourseTypeInfo {
    private String cat_name;
    private String cover;
    public List<CourseTypeDetailInfo> child;


    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCover() {
        if (cover == null) {
            return "";
        }
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
