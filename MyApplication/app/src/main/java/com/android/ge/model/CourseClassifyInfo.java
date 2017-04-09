package com.android.ge.model;

import java.util.List;

/**
 * Created by xudengwang on 17/4/8.
 */

public class CourseClassifyInfo {
    private int course_type_id;
    private int org_id;
    private int type;
    private String name;
    private String code;
    private long created_at;
    private long updated_at;

    public List<CourseBean> courses;

    public int getCourse_type_id() {
        return course_type_id;
    }

    public void setCourse_type_id(int course_type_id) {
        this.course_type_id = course_type_id;
    }

    public int getOrg_id() {
        return org_id;
    }

    public void setOrg_id(int org_id) {
        this.org_id = org_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }
}
