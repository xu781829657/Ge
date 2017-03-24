package com.android.ge.controller.adapter.Itemviewdelegate;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
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

public class CourseItemViewDelegate implements ItemViewDelegate<BaseLearningItem> {

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

        StaggeredGridLayoutManager.LayoutParams itemParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        itemParams.width = StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT;
        itemParams.height = StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT;
        if (baseItemInfo.getOriginalPosition() % 2 == 0) {
            itemParams.setMargins((int) (ScreenUtils.getScreenDensity(Base.getContext()) * 12), 0, (int) (ScreenUtils.getScreenDensity(Base.getContext()) * 5),
                    (int) (ScreenUtils.getScreenDensity(Base.getContext()) * 10));
        } else {
            itemParams.setMargins((int) (ScreenUtils.getScreenDensity(Base.getContext()) * 5), 0, (int) (ScreenUtils.getScreenDensity(Base.getContext()) * 12),
                    (int) (ScreenUtils.getScreenDensity(Base.getContext()) * 10));
        }
        holder.itemView.setLayoutParams(itemParams);


//        LinearLayout.LayoutParams itemParams = (LinearLayout.LayoutParams) holder.getView(R.id.lin_item).getLayoutParams();
//        itemParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
//        itemParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;

        holder.getView(R.id.lin_item).setLayoutParams(itemParams);


        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.getView(R.id.rel_cover).getLayoutParams();
        params.width = (int) (ScreenUtils.getScreenWidth(Base.getContext()) - 34 * ScreenUtils.getScreenDensity(Base.getContext())) / 2;
        params.height = (int) params.width * 86 / 171;

        holder.getView(R.id.rel_cover).setLayoutParams(params);

        holder.setText(R.id.tv_course_title, courseBean.getTitle());
        holder.setText(R.id.tv_course_descrip, courseBean.getDesc());


    }
}
