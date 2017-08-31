package com.android.ge.model.task;

import com.android.ge.utils.NumberUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xudengwang on 17/4/8.
 * "task_id": "taskid:1123",
 "title" : "",
 "deadline_timestamp" : 1481111111, --截止时间
 "progress_current" : "1", --任务进度.
 "progress_total" : "10", --任务进度.
 "courses" : [{课程结构体A},{课程结构体B}, ...]，
 "part_count" : 3, -- 可选部分，课件数量
 "quiz_count" : 2,  -- 可选部分，考试数量
 "survey_count" : 0,  -- 可选部分，问卷数量

 "id": 1,
 "org_id": 1,
 "title": "任务一",
 "cat_id": 0,
 "cover": "",
 "content": "任务1：如何提高口语方式",
 "begin_at": "2017-04-24 21:42:24",
 "end_at": "2017-08-24 21:42:24",
 "user_id": "1,2",
 "courses_id": "1,2,4",
 "examination_id": "1,2",
 "questionnaire_id": "",
 "marketable": 1,
 "p_order": 1,
 "created_at": null,
 "updated_at": null,
 "courses_total": 3,
 "examination_total": 2,
 "questionnaire_total": 0
 */

public class TaskBean implements Serializable {
    //private String task_id;
    private String id;
    private String title;
    private String end_at;
    private int courses_total;//课件数量
    private int assessment_total;//测试数量
    private int survey_total;//问卷数量
    //private long deadline_timestamp;
//    private String progress_current;
//    private String progress_total;
//    private int part_count;
//    private int quiz_count;
//    private int survey_count;
    public List<TaskCourseBean> courses;
    private int progress;
    private String progress_current;
    private String progress_total;

    public int getProgress() {
        return NumberUtil.getProgress(getProgress_current(),getProgress_total());
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnd_at() {
        return end_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }

    public int getCourses_total() {
        return courses_total;
    }

    public void setCourses_total(int courses_total) {
        this.courses_total = courses_total;
    }

    public int getExamination_total() {
        return assessment_total;
    }

    public void setExamination_total(int examination_total) {
        this.assessment_total = examination_total;
    }

    public int getQuestionnaire_total() {
        return survey_total;
    }

    public void setQuestionnaire_total(int questionnaire_total) {
        this.survey_total = questionnaire_total;
    }

    public List<TaskCourseBean> getCourses() {
        return courses;
    }

    public void setCourses(List<TaskCourseBean> courses) {
        this.courses = courses;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}

