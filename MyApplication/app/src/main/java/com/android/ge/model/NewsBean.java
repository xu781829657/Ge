package com.android.ge.model;

import android.text.TextUtils;

/**
 * Created by xudengwang on 17/3/19.
 * <p>
 * {"index" : "2", "type": "news", "title" : "GE大型留学活动展，3月12我们不见不散"}
 */

public class NewsBean {
    private String id;
    private String title;
    private String cover;
    private String created_at;
    private String pv;//阅读数量

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            title = "";
        }
        this.title = title;
    }

    public String getCover() {

        if (TextUtils.isEmpty(cover)) {
            cover = "";
        }
        return cover;
    }

    public String getCreated_at() {
        if (TextUtils.isEmpty(created_at)) {
            created_at = "";
        }
        return created_at;
    }

    public String getPv() {
        if (TextUtils.isEmpty(pv)) {
            pv = "0";
        }
        return pv;
    }
}
