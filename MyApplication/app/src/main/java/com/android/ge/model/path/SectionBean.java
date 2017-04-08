package com.android.ge.model.path;

import java.io.Serializable;

/**
 * Created by xudengwang on 17/4/8.
 * "section_id" : "secid-1234",
 "index" : 1,
 "progress" : "1/10",
 "courses" : [{课程结构体A},{课程结构体B}, ...]
 */

public class SectionBean implements Serializable{
    private String section_id;
    private int index;
    private String progress;
}
