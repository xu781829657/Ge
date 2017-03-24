package com.android.ge.model.learning;

/**
 * Created by xudengwang on 17/3/21.
 *
 *             "id" : "1",
 "title" : "aaaa",
 "total_count" : 5,
 */

public class TitleItemInfo {
    private String id;//指定类型id
    private String title;//课程类型标题
    private int total_count;//该类型下课程总数

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
        this.title = title;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }
}
