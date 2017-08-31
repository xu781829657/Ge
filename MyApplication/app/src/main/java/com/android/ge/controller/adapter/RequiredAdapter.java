package com.android.ge.controller.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.base.frame.Base;
import com.android.base.util.ScreenUtils;
import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.model.CourseBean;
import com.android.ge.ui.webview.CourseWebActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xudengwang on 17/3/20.
 */

public class RequiredAdapter extends BaseCommonAdapter<CourseBean> implements MultiItemTypeAdapter.OnItemClickListener {

    public RequiredAdapter(Context context, List<CourseBean> datas) {
        super(context, R.layout.item_for_required_course, datas);
        setOnItemClickListener(this);
    }

    @Override
    protected void convert(ViewHolder holder, CourseBean courseBean, int position) {
        GridLayoutManager.LayoutParams itemParams = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        itemParams.width = GridLayoutManager.LayoutParams.MATCH_PARENT;
        itemParams.height = GridLayoutManager.LayoutParams.WRAP_CONTENT;
        if (position % 2 == 0) {
            itemParams.setMargins((int) (ScreenUtils.getScreenDensity(Base.getContext()) * 12), 0, (int) (ScreenUtils.getScreenDensity(Base.getContext()) * 5),
                    (int) (ScreenUtils.getScreenDensity(Base.getContext()) * 10));
        } else {
            itemParams.setMargins((int) (ScreenUtils.getScreenDensity(Base.getContext()) * 5), 0, (int) (ScreenUtils.getScreenDensity(Base.getContext()) * 12),
                    (int) (ScreenUtils.getScreenDensity(Base.getContext()) * 10));
        }
        holder.itemView.setLayoutParams(itemParams);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.getView(R.id.rel_cover).getLayoutParams();
        params.width = (int) (ScreenUtils.getScreenWidth(mContext) - 34 * ScreenUtils.getScreenDensity(mContext)) / 2;
        params.height = (int) params.width * 86 / 171;
        holder.getView(R.id.rel_cover).setLayoutParams(params);

        holder.setText(R.id.tv_course_title, courseBean.getTitle());


        if (!TextUtils.isEmpty(courseBean.getTeacher_name())) {
            holder.setText(R.id.tv_course_auth, String.format(mContext.getString(R.string.format_auth), courseBean.getTeacher_name()));
            holder.getView(R.id.tv_course_auth).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.tv_course_auth).setVisibility(View.GONE);
        }
        if (courseBean.getProgress() == 0) {
            holder.getView(R.id.view_bg_cover).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.view_bg_cover).setVisibility(View.GONE);
        }
        holder.setText(R.id.tv_course_progress, String.format(mContext.getString(R.string.format_total_progress),
                courseBean.getProgress() + "%"));
//        holder.setText(R.id.tv_course_progress, String.format(Base.string(R.string.format_total_progress), courseBean.getProgress()));
//        int progress = courseBean.getProgress();
//        if(progress == 0){
//            holder.getView(R.id.view_bg_cover).setVisibility(View.VISIBLE);
//            holder.getView(R.id.tv_begin).setVisibility(View.VISIBLE);
//        } else {
//            holder.getView(R.id.view_bg_cover).setVisibility(View.GONE);
//            holder.getView(R.id.tv_begin).setVisibility(View.GONE);
//        }

        setImageFromInternet((ImageView) holder.getView(R.id.iv_course_cover), courseBean.getCover(), R.drawable.demo_course_loading_icon);

    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        CourseBean courseBean = mDatas.get(position);
        Bundle bundle = new Bundle();
        bundle.putString(CommonConstant.PARAM_COURSE_ID, courseBean.getId());
        bundle.putString(CommonConstant.PARAM_ENTRY_TYPE,CommonConstant.TYPE_COURSES);
        bundle.putString(CommonConstant.PARAM_ENTRY_ID,courseBean.getId());
        bundle.putString(CommonConstant.PARAM_CX,courseBean.getCx());
        gotoActivity(CourseWebActivity.class, bundle);
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
