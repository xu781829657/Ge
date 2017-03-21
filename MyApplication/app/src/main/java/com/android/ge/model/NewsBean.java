package com.android.ge.model;

/**
 * Created by xudengwang on 17/3/19.
 *
 *        {"index" : "2", "type": "news", "title" : "GE大型留学活动展，3月12我们不见不散"}
 */

public class NewsBean {
    private String index;
    private String type;
    private String title;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
