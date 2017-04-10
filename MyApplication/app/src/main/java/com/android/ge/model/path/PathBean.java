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
 */

public class PathBean implements Serializable {
    private String path_id;
    private String title;
    private String path_name;
    private String cover;
    private String progress_current;
    private String progress_total;
    public List<SectionBean> sections;
    private int progress;

    public String getPath_id() {
        return path_id;
    }

    public void setPath_id(String path_id) {
        this.path_id = path_id;
    }

    public String getTitle() {
        if (TextUtils.isEmpty(title)) {
            setTitle(path_name);
        }
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

    public String getPath_name() {
        return path_name;
    }

    public void setPath_name(String path_name) {
        this.path_name = path_name;
    }

    public int getProgress() {
        return NumberUtil.getProgress(getProgress_current(),getProgress_total());
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
