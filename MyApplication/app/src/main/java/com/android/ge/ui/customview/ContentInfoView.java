package com.android.ge.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.base.util.NetworkUtil;
import com.android.ge.R;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by xudengwang on 17/4/8.
 */

public class ContentInfoView extends LinearLayout {
    private Context mContext;
    private TextView mTvCourseCount;
    private TextView mTvTestCount;
    private TextView mTvQuesCount;

    public ContentInfoView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public ContentInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public ContentInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public ContentInfoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
    }

    private void init() {
        View view = View.inflate(mContext, R.layout.view_content_info, null);
        mTvCourseCount = (TextView) view.findViewById(R.id.tv_course_count);
        mTvTestCount = (TextView) view.findViewById(R.id.tv_test_count);
        mTvQuesCount = (TextView) view.findViewById(R.id.tv_ques_count);


        setBackgroundColor(-1);

        addView(view);
    }
}
