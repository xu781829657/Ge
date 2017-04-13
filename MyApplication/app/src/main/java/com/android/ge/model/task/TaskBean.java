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
 */

public class TaskBean implements Serializable{
    private String task_id;
    private String title;
    private long deadline_timestamp;
    private String progress_current;
    private String progress_total;
    private int part_count;
    private int quiz_count;
    private int survey_count;
    public List<TaskCourseBean> courses;
    private int progress;
    public int getProgress() {
        return NumberUtil.getProgress(getProgress_current(),getProgress_total());
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDeadline_timestamp() {
        return deadline_timestamp;
    }

    public void setDeadline_timestamp(long deadline_timestamp) {
        this.deadline_timestamp = deadline_timestamp;
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
}
