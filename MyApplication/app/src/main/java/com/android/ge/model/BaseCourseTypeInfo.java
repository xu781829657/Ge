package com.android.ge.model;

import java.util.List;

/**
 * Created by xudengwang on 17/3/20.
 * "title" : "标题", "total_count" : 10, "courses" : [{课程结构体A},{课程结构体B}]},  --必修课

 */

public class BaseCourseTypeInfo {
    protected String title;
    protected int total_count;
    protected List<CourseBean> courses;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<CourseBean> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseBean> courses) {
        this.courses = courses;
    }
}
