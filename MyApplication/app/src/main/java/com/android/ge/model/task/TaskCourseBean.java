package com.android.ge.model.task;

import android.text.TextUtils;

import com.android.ge.utils.NumberUtil;

/**
 * Created by xudengwang on 17/5/21.
 * "id": 1,
 * "org_id": 1,
 * "title": "GE课程考试题目",
 * "cover": "http://ganxike-production-common.ufile.ucloud.com.cn/face05.jpg",
 * "content": "",
 * "pass_score": 60,
 * "courses_id": 1,
 * "marketable": 1,
 * "created_at": 1493020982,
 * "updated_at": 1493020982
 */

public class TaskCourseBean {
    private String id;
    private String cover;
    private String content;

    private String cx;
    private String title;
    private String progress_current;
    private String progress_total;

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

    public int getProgress() {
        return NumberUtil.getProgress(getProgress_current(), getProgress_total());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
