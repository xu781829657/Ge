package com.android.ge.model.learning;

import com.android.ge.model.CourseBean;

/**
 * Created by xudengwang on 17/3/21.
 */

public class BaseLearningItem {
    private boolean isTitle;
    private boolean isCourse;

    private TitleItemInfo titleItemInfo;
    private CourseBean courseBean;

    public TitleItemInfo getTitleItemInfo() {
        return titleItemInfo;
    }

    public void setTitleItemInfo(TitleItemInfo titleItemInfo) {
        this.titleItemInfo = titleItemInfo;
        if (titleItemInfo != null) {
            setTitle(true);
        }
    }

    public CourseBean getCourseBean() {
        return courseBean;
    }

    public void setCourseBean(CourseBean courseBean) {
        this.courseBean = courseBean;
        if (courseBean != null) {
            setCourse(true);
        }
    }


    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public boolean isCourse() {
        return isCourse;
    }

    public void setCourse(boolean course) {
        isCourse = course;
    }
}
