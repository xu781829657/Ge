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
import com.android.ge.model.learning.TitleItemInfo;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by xudengwang on 17/3/23.
 */

public class CourseTypeItemViewDelegate implements ItemViewDelegate<BaseLearningItem> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_for_course_type;
    }

    @Override
    public boolean isForViewType(BaseLearningItem baseItemInfo, int position) {
        return baseItemInfo.isTitle();
    }

    @Override
    public void convert(ViewHolder holder, BaseLearningItem baseItemInfo, int position) {
        final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams sglp = (StaggeredGridLayoutManager.LayoutParams) lp;
            sglp.setFullSpan(true);
            holder.itemView.setLayoutParams(sglp);
        }
        TitleItemInfo info = baseItemInfo.getTitleItemInfo();
        holder.setText(R.id.tv_type_title, info.getTitle());
        int total_count = Integer.valueOf(info.getTotal_count());
        if (total_count > 4) {
            holder.getView(R.id.tv_more).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.tv_more).setVisibility(View.GONE);
        }



    }
}
