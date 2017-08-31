package com.android.ge.model.task;

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

public class TaskQuestionnaireBean {
    private String id;
    private String name;
    private String cover;
    private String content;
    private String cx;


    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
