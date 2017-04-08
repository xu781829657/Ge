package com.android.ge.model.path;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xudengwang on 17/4/8.
 * "path_id": "pathid:1123",
 "title" : "",
 "cover" : "",
 "progress_current" : "2", --完成进度
 "progress_total" : "10", --完成进度
 "sections":[  -- 阶段列表
 {
 "section_id" : "secid-1234",
 "index" : 1,
 "progress" : "1/10",
 "courses" : [{课程结构体A},{课程结构体B}, ...]
 },
 {
 "section_id" : "secid-56",
 "index" : 2,
 "progress" : "0/10",
 "courses" : [{课程结构体A},{课程结构体B}, ...]
 } ...
 ]
 */

public class PathBean implements Serializable{
    private String path_id;
    private String title;
    private String cover;
    private String progress_current;
    private String progress_total;
    public List<SectionBean> sections;
}
