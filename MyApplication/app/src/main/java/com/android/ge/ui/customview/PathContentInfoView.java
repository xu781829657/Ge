package com.android.ge.ui.customview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.ge.R;
import com.android.ge.model.path.PathBean;
import com.android.ge.model.task.TaskBean;

/**
 * Created by xudengwang on 17/4/8.
 */

public class PathContentInfoView extends LinearLayout {
    private Context mContext;
    private TextView mTvCourseCount;
    private TextView mTvTestCount;
    private TextView mTvSurveyCount;

    public PathContentInfoView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public PathContentInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public PathContentInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public PathContentInfoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
    }

    private void init() {
        View view = View.inflate(mContext, R.layout.view_path_content_info, null);
        mTvCourseCount = (TextView) view.findViewById(R.id.tv_course_count);
        mTvTestCount = (TextView) view.findViewById(R.id.tv_test_count);
        mTvSurveyCount = (TextView) view.findViewById(R.id.tv_survey_count);
        setBackgroundColor(Color.TRANSPARENT);

        addView(view);
    }

    public void setPathBean(PathBean bean) {
        if (bean != null) {
            mTvCourseCount.setText(String.format(Base.string(R.string.format_course_course_count), bean.getCourses_total()));
            mTvTestCount.setText(String.format(Base.string(R.string.format_course_quiz_count), bean.getExamination_total()));
            mTvSurveyCount.setText(String.format(Base.string(R.string.format_course_survey_count), bean.getQuestionnaire_total()));
        }
    }
}
