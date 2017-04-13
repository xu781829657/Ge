package com.android.ge.model.task;

import java.io.Serializable;

/**
 * Created by xudengwang on 17/4/9.
 * <p>
 * "sysno": 1,
 * "task_id": 1,
 * "org_id": 1,
 * "obj_id": 1,
 * "obj_type": 1,
 * "created_at": 1491631225,
 * "updated_at": 1491631276,
 * "type": "Course",
 * "id": 1
 */

public class TaskCourseBean implements Serializable{

    private int id;
    private String type;
    private long created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
