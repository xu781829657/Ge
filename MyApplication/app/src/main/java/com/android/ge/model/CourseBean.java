package com.android.ge.model;

/**
 * Created by xudengwang on 17/3/20.
 *  "course_id" : "cid-123456",
 "title" : "课程名称",
 "cover" : "简介图url",
 "desc" : "课程简介",
 "tags" : [{"tag_id" : "1", "name" : "心理"},{"tag_id" : "2", "name" : "物理"}],  -- 课程标签
 "parts" : [{coursePart}, {coursePart}], -- 可选部分，参看具体CoursePart结构体
 "teacher_id" : 12,  -- 关联教师,
 "is_finish" : false,  --  可选部分，是否已经完成
 "is_lock" : false,  -- 可选部分，是否锁定
 "part_count" : 3, -- 可选部分，课件数量
 "quiz_count" : 2,  -- 可选部分，考试数量
 "survey_count" : 0,  -- 可选部分，问卷数量
 "progress_current" : "1", --可选部分，课程进度.
 "progress_total" : "10", --可选部分，课程进度.
 */

public class CourseBean {
    private String title;
    private String course_id;
    private String cover;
    private String desc;


    private String teacher_id;
    private boolean is_finish;
    private boolean is_lock;
    private int part_count;
    private int quiz_count;
    private int survey_count;
    private String progress_current;
    private String progress_total;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public boolean is_finish() {
        return is_finish;
    }

    public void setIs_finish(boolean is_finish) {
        this.is_finish = is_finish;
    }

    public boolean is_lock() {
        return is_lock;
    }

    public void setIs_lock(boolean is_lock) {
        this.is_lock = is_lock;
    }

    public int getPart_count() {
        return part_count;
    }

    public void setPart_count(int part_count) {
        this.part_count = part_count;
    }

    public int getQuiz_count() {
        return quiz_count;
    }

    public void setQuiz_count(int quiz_count) {
        this.quiz_count = quiz_count;
    }

    public int getSurvey_count() {
        return survey_count;
    }

    public void setSurvey_count(int survey_count) {
        this.survey_count = survey_count;
    }

    public String getProgress_current() {
        return progress_current;
    }

    public void setProgress_current(String progress_current) {
        this.progress_current = progress_current;
    }

    public String getProgress_total() {
        return progress_total;
    }

    public void setProgress_total(String progress_total) {
        this.progress_total = progress_total;
    }
}