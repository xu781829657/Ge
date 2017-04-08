package com.android.ge.model.task;

import java.io.Serializable;

/**
 * Created by xudengwang on 17/4/8.
 * "task_id": "taskid:1123",
 "title" : "",
 "deadline_timestamp" : 1481111111, --截止时间
 "progress_current" : "1", --任务进度.
 "progress_total" : "10", --任务进度.
 "courses" : [{课程结构体A},{课程结构体B}, ...]，
 "part_count" : 3, -- 可选部分，课件数量
 "quiz_count" : 2,  -- 可选部分，考试数量
 "survey_count" : 0,  -- 可选部分，问卷数量
 */

public class TaskBean implements Serializable{
    private String task_id;
    private String title;
    private long deadline_timestamp;
    private String progress_current;
    private String progress_total;
    private int part_count;
    private int quiz_count;
    private int survey_count;


}
