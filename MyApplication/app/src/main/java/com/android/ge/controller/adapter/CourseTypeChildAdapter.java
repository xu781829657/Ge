package com.android.ge.controller.adapter;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.base.frame.Base;
import com.android.ge.R;
import com.android.ge.model.CourseTypeDetailInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/5/27 0027.
 */
public class CourseTypeChildAdapter extends BaseAdapter {

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

    private List<CourseTypeDetailInfo> mDatas;

    public CourseTypeChildAdapter() {
    }

    @Override
    public int getCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_for_course_type_child, null);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_course_type_name);
        tv_name.setText(mDatas.get(position).getCat_name());
        tv_name.setBackgroundColor(ContextCompat.getColor(Base.getContext(),mBgColors[position%5]));
        tv_name.setTextColor(ContextCompat.getColor(Base.getContext(),mTvColors[position%5]));
        return view;
    }

    public List<CourseTypeDetailInfo> getData() {
        return mDatas;
    }

    public void setData(List<CourseTypeDetailInfo> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public void refreshData() {
        notifyDataSetChanged();
    }
}
