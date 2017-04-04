package com.android.ge.model;

/**
 * Created by xudengwang on 17/3/19.
 * <p>
 * {"index" : "2", "type": "news", "title" : "GE大型留学活动展，3月12我们不见不散"}
 */

public class NewsBean {
    private String sysno;
    private String title;

    public String getSysno() {
        return sysno;
    }

    public void setSysno(String sysno) {
        this.sysno = sysno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
