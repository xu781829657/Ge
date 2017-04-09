package com.android.ge.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.ge.R;
import com.android.ge.model.CourseBean;
import com.android.ge.model.task.TaskBean;

/**
 * Created by xudengwang on 17/4/8.
 */

public class TaskContentInfoView extends LinearLayout {
    private Context mContext;
    private TextView mTvCourseCount;
    private TextView mTvTestCount;
    private TextView mTvSurveyCount;

    public TaskContentInfoView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public TaskContentInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public TaskContentInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public TaskContentInfoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
    }

    private void init() {
        View view = View.inflate(mContext, R.layout.view_task_content_info, null);
        mTvCourseCount = (TextView) view.findViewById(R.id.tv_course_count);
        mTvTestCount = (TextView) view.findViewById(R.id.tv_test_count);
        mTvSurveyCount = (TextView) view.findViewById(R.id.tv_survey_count);
        setBackgroundColor(-1);

        addView(view);
    }

    public void setTaskBean(TaskBean bean) {
        if (bean != null) {
            mTvCourseCount.setText(String.format(Base.string(R.string.format_course_course_count), bean.getPart_count()));
            mTvTestCount.setText(String.format(Base.string(R.string.format_course_quiz_count), bean.getQuiz_count()));
            mTvSurveyCount.setText(String.format(Base.string(R.string.format_course_survey_count), bean.getSurvey_count()));
        }
    }
}
