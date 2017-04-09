package com.android.ge.controller.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.android.ge.R;
import com.android.ge.model.CourseBean;
import com.android.ge.ui.customview.CourseContentInfoView;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xudengwang on 17/4/8.
 */

public class ClassifyCourseListAdapter extends BaseCommonAdapter<CourseBean> {
    public ClassifyCourseListAdapter(Context context, List<CourseBean> datas) {
        super(context, R.layout.item_for_classify_course_list, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CourseBean courseBean, int position) {
        holder.setText(R.id.tv_course_title, courseBean.getTitle());
        ((CourseContentInfoView) holder.getView(R.id.view_course_content_info)).setCourseBean(courseBean);
        setImageFromInternet((ImageView) holder.getView(R.id.iv_course_cover), courseBean.getCover(), R.drawable.questionnaire_loading_icon);

    }
}
