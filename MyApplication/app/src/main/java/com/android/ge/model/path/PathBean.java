package com.android.ge.model.path;

import android.text.TextUtils;

import com.android.ge.utils.NumberUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xudengwang on 17/4/8.
 * "path_id": "pathid:1123",
 * "title" : "",
 * "cover" : "",
 * "progress_current" : "2", --完成进度
 * "progress_total" : "10", --完成进度
 * "sections":[  -- 阶段列表
 * {
 * "section_id" : "secid-1234",
 * "index" : 1,
 * "progress" : "1/10",
 * "courses" : [{课程结构体A},{课程结构体B}, ...]
 * },
 * {
 * "section_id" : "secid-56",
 * "index" : 2,
 * "progress" : "0/10",
 * "courses" : [{课程结构体A},{课程结构体B}, ...]
 * } ...
 * ]
 * <p>
 * "id": 1,
 * "org_id": 1,
 * "title": "学习路径yi",
 * "cat_id": 0,
 * "tag_id": 0,
 * "cover": "http://ganxike-production-common.ufile.ucloud.com.cn/QQ20170415123034.jpg",
 * "content": "学习路径内容",
 * "role_id": 1,
 * "courses_id": "2,3",
 * "examination_id": "",
 * "questionnaire_id": "",
 * "marketable": 1,
 * "p_order": 1,
 * "created_at": null,
 * "updated_at": null,
 * "courses_total": 2,
 * "examination_total": 0,
 * "questionnaire_total": 0
 */

public class PathBean implements Serializable {
    private String id;
    private String title;
    private String cover;
    private int courses_total;//课件数量
    private int examination_total;//测试数量
    private int questionnaire_total;//问卷数量
    public List<SectionBean> sections;
    private String progress_current;
    private String progress_total;
    private int progress;
    private String cx;

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public int getProgress() {
        return NumberUtil.getProgress(getProgress_current(),getProgress_total());
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getCourses_total() {
        return courses_total;
    }

    public int getExamination_total() {
        return examination_total;
    }

    public int getQuestionnaire_total() {
        return questionnaire_total;
    }
}
