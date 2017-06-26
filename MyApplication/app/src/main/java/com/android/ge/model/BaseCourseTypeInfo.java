package com.android.ge.model;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by xudengwang on 17/3/20.
 * "title" : "标题", "total_count" : 10, "courses" : [{课程结构体A},{课程结构体B}]},  --必修课

 */

public class BaseCourseTypeInfo {
    protected String id;
    protected String tag_id;//标签分类
    protected String cat_id;
    protected String title;//标题
    protected int total;//课程总数
    protected List<CourseBean> courses;//课程列表,可能是前几个

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CourseBean> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseBean> courses) {
        this.courses = courses;
    }

    public String getId() {
        if (TextUtils.isEmpty(id)) {
            if (!TextUtils.isEmpty(tag_id)) {
                setId(tag_id);
            } else if (!TextUtils.isEmpty(cat_id)) {
                setId(cat_id);
            }
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }
}
