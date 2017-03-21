package com.android.ge.controller.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.base.util.ScreenUtils;
import com.android.ge.R;
import com.android.ge.model.CourseBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xudengwang on 17/3/20.
 */

public class RecommandAdapter extends CommonAdapter<CourseBean> {

    public RecommandAdapter(Context context, List<CourseBean> datas) {
        super(context, R.layout.item_for_recomand_course, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CourseBean courseBean, int position) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.getView(R.id.rel_cover).getLayoutParams();
        params.width = (int) (ScreenUtils.getScreenWidth(mContext) - 34 * ScreenUtils.getScreenDensity(mContext)) / 2;
        params.height = (int) params.width * 86 / 171;
        holder.getView(R.id.rel_cover).setLayoutParams(params);

        holder.setText(R.id.tv_course_title, courseBean.getTitle());
        holder.setText(R.id.tv_course_descrip, courseBean.getDesc());
    }
}
