package com.android.ge.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.base.util.ScreenUtils;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.model.CourseTypeDetailInfo;
import com.android.ge.ui.course.ClassifyCourseListActivity;
import com.android.ge.ui.customview.WordWrapView;
import com.android.ge.widgets.view.FluidLayout;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xudengwang on 17/4/8.
 */

public class CourseTypeAllAdapter extends BaseCommonAdapter<CourseTypeDetailInfo> implements MultiItemTypeAdapter.OnItemClickListener {
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

    public CourseTypeAllAdapter(Context context, List<CourseTypeDetailInfo> datas) {
        super(context, R.layout.view_course_type, datas);
        //setOnItemClickListener(this);
    }

    @Override
    protected void convert(ViewHolder holder, final CourseTypeDetailInfo info, final int position) {

        //((CourseTypeView) holder.getView(R.id.view_course_type)).setData(courseTypeDetailInfo);
        holder.setText(R.id.tv_cat_name, info.getCat_name());

        holder.getView(R.id.tv_check_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(CommonConstant.KEY_COURSE_TYPE_ID, info.getId());
                bundle.putInt(CommonConstant.KEY_COURSE_TYPE, ClassifyCourseListActivity.COURSE_TAG);
                bundle.putString(CommonConstant.KEY_TITLE, info.getTitle());
                Intent intent = new Intent(mContext, ClassifyCourseListActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        FluidLayout viewFluidLayout = holder.getView(R.id.view_fluid_layout);

       // WordWrapView wordWrapView = (WordWrapView) holder.getView(R.id.view_word_warp);

        if (info.child != null && info.child.size() > 0) {
            for (int i = 0; i < info.child.size(); i++) {
                final CourseTypeDetailInfo detailInfo = info.child.get(i);
                final int index = i;
                TextView tv_name = new TextView(mContext);
               // LinearLayout.LayoutParams  params = new LinearLayout.LayoutParams(ScreenUtils.dpToPx(235),ScreenUtils.dpToPx(88));
               // tv_name.setLayoutParams(params);
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                tv_name.setLayoutParams(params);
                FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(ScreenUtils.dpToPx(5),ScreenUtils.dpToPx(5),ScreenUtils.dpToPx(5),ScreenUtils.dpToPx(5));
                tv_name.setLayoutParams(params);
                tv_name.setIncludeFontPadding(true);
                tv_name.setText(detailInfo.getCat_name());
                tv_name.setTextSize(15);
                tv_name.setGravity(Gravity.CENTER);
                tv_name.setBackgroundColor(ContextCompat.getColor(Base.getContext(), mBgColors[i % 5]));
                tv_name.setTextColor(ContextCompat.getColor(Base.getContext(), mTvColors[i % 5]));
                tv_name.setPadding(ScreenUtils.dpToPx(15), ScreenUtils.dpToPx(15), ScreenUtils.dpToPx(15), ScreenUtils.dpToPx(15));
                tv_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CourseTypeDetailInfo detailInfo = info.child.get(index);
                        Bundle bundle = new Bundle();
                        bundle.putString(CommonConstant.KEY_COURSE_TYPE_ID, detailInfo.getCat_id());
                        bundle.putInt(CommonConstant.KEY_COURSE_TYPE, ClassifyCourseListActivity.COURSE_CLASSIFY);
                        bundle.putString(CommonConstant.KEY_TITLE, detailInfo.getCat_name());
                        Intent intent = new Intent(mContext, ClassifyCourseListActivity.class);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                });
                //wordWrapView.addView(tv_name);
                viewFluidLayout.addView(tv_name);
            }
            //wordWrapView.setVisibility(View.VISIBLE);
            viewFluidLayout.setVisibility(View.VISIBLE);
        } else {
            //wordWrapView.setVisibility(View.GONE);
            viewFluidLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
