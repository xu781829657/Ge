package com.android.ge.controller.adapter.Itemviewdelegate;

import android.widget.LinearLayout;

import com.android.base.frame.Base;
import com.android.base.util.ScreenUtils;
import com.android.ge.R;
import com.android.ge.model.CourseBean;
import com.android.ge.model.learning.BaseLearningItem;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by xudengwang on 17/3/23.
 */

public class CourseItemViewDelegate implements ItemViewDelegate<BaseLearningItem>{

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_for_course;
    }

    @Override
    public boolean isForViewType(BaseLearningItem baseItemInfo, int position) {
        return baseItemInfo.isCourse();
    }

    @Override
    public void convert(ViewHolder holder, BaseLearningItem baseItemInfo, int position) {
        CourseBean courseBean = baseItemInfo.getCourseBean();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.getView(R.id.rel_cover).getLayoutParams();
        params.width = (int) (ScreenUtils.getScreenWidth(Base.getContext()) - 34 * ScreenUtils.getScreenDensity(Base.getContext())) / 2;
        params.height = (int) params.width * 86 / 171;
        holder.getView(R.id.rel_cover).setLayoutParams(params);

        holder.setText(R.id.tv_course_title, courseBean.getTitle());
        holder.setText(R.id.tv_course_descrip, courseBean.getDesc());

    }
}
