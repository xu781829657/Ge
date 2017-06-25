package com.android.ge.ui.customview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.base.util.ScreenUtils;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.controller.adapter.CourseTypeChildAdapter;
import com.android.ge.model.CourseTypeDetailInfo;
import com.android.ge.ui.course.ClassifyCourseListActivity;

/**
 * Created by xudengwang on 17/6/24.
 */

public class CourseTypeView extends LinearLayout {

    private Context mContext;
    private TextView mTvCatName;
    private TextView mTvCheckAll;
    private WordWrapView mWorpWrapView;
    //private GridView mGridView;

    private CourseTypeDetailInfo mInfo;
    private CourseTypeChildAdapter mAdapter;


    private int[] mBgColors = {R.color.course_type_child_bg_1,
            R.color.course_type_child_bg_2,
            R.color.course_type_child_bg_3,
            R.color.course_type_child_bg_4,
            R.color.course_type_child_bg_5};
    private int[] mTvColors = {R.color.course_type_child_tv_1,
            R.color.course_type_child_tv_2,
            R.color.course_type_child_tv_3,
            R.color.course_type_child_tv_4,
            R.color.course_type_child_tv_5,};
    private float mDensity;


    public CourseTypeView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public CourseTypeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public CourseTypeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        View view = View.inflate(mContext, R.layout.view_course_type, null);
        mTvCatName = (TextView) view.findViewById(R.id.tv_cat_name);
        mTvCheckAll = (TextView) view.findViewById(R.id.tv_check_all);
        //mGridView = (GridView) view.findViewById(R.id.gd_course_type);
        mWorpWrapView = (WordWrapView) view.findViewById(R.id.view_word_warp);

    }

    private void initData() {
        mTvCheckAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(CommonConstant.KEY_COURSE_TYPE_ID, mInfo.getId());
                bundle.putInt(CommonConstant.KEY_COURSE_TYPE, ClassifyCourseListActivity.COURSE_TAG);
                bundle.putString(CommonConstant.KEY_TITLE, mInfo.getTitle());
                Intent intent = new Intent(mContext, ClassifyCourseListActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);

            }
        });

    }

    public void setData(final CourseTypeDetailInfo info) {
        if (info == null) {
            return;
        }
        mInfo = info;
        if (!TextUtils.isEmpty(info.getCat_name())) {
            mTvCatName.setText(info.getCat_name());
            mTvCatName.setVisibility(View.VISIBLE);
        } else {
            mTvCatName.setVisibility(View.GONE);
        }
        if (info.child != null && info.child.size() > 0) {
            for (int i = 0; i < info.child.size(); i++) {
                final int position = i;
                TextView tv_name = new TextView(mContext);
                tv_name.setPadding(ScreenUtils.dpToPx(15), ScreenUtils.dpToPx(25), ScreenUtils.dpToPx(15), ScreenUtils.dpToPx(25));
                tv_name.setBackgroundColor(ContextCompat.getColor(Base.getContext(), mBgColors[i % 5]));
                tv_name.setTextColor(ContextCompat.getColor(Base.getContext(), mTvColors[i % 5]));
                tv_name.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CourseTypeDetailInfo detailInfo = info.child.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putString(CommonConstant.KEY_COURSE_TYPE_ID, detailInfo.getId());
                        bundle.putInt(CommonConstant.KEY_COURSE_TYPE, ClassifyCourseListActivity.COURSE_TAG);
                        bundle.putString(CommonConstant.KEY_TITLE, detailInfo.getTitle());
                        Intent intent = new Intent(mContext, ClassifyCourseListActivity.class);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                });
                mWorpWrapView.addView(tv_name);
            }
            mWorpWrapView.setVisibility(VISIBLE);
        } else {
            mWorpWrapView.setVisibility(VISIBLE);
        }
//        if (info.child != null && info.child.size() > 0) {
//            refreshAdapter();
//        } else {
//            mGridView.setVisibility(View.GONE);
//        }

    }


//    private void refreshAdapter() {
//        if (mAdapter == null) {
//            mAdapter = new CourseTypeChildAdapter();
//            mGridView.setAdapter(mAdapter);
//        }
//        mAdapter.setData(mInfo.child);
//        mGridView.setVisibility(View.VISIBLE);
//
//        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CourseTypeDetailInfo info = mAdapter.getData().get(position);
//                Bundle bundle = new Bundle();
//                bundle.putString(CommonConstant.KEY_COURSE_TYPE_ID, info.getId());
//                bundle.putInt(CommonConstant.KEY_COURSE_TYPE, ClassifyCourseListActivity.COURSE_TAG);
//                bundle.putString(CommonConstant.KEY_TITLE, info.getTitle());
//                Intent intent = new Intent(mContext, ClassifyCourseListActivity.class);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);
//            }
//        });
//    }

}
