package com.android.ge.model.task;

import com.android.ge.utils.NumberUtil;

/**
 * Created by xudengwang on 17/5/21.
 * "id": 1,
 * "org_id": 1,
 * "missions_id": 1,
 * "detail_id": 1,
 * "detail_type": "courses",
 * "order": 1,
 * "created_at": null,
 * "updated_at": null,
 * "progress_current": 1,
 * "progress_total": 10
 */

public class TaskDetailBean {
    private String id;
    private String detail_type;
    private String progress_current;
    private String progress_total;

    public TaskCourseBean courses;
    public TaskQuizBean examinations;
    public TaskQuestionnaireBean questionnaire;

    private int progress;

    public int getProgress() {
        return NumberUtil.getProgress(getProgress_current(), getProgress_total());
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetail_type() {
        return detail_type;
    }

    public void setDetail_type(String detail_type) {
        this.detail_type = detail_type;
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
