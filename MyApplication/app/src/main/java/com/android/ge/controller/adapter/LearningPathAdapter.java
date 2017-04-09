package com.android.ge.controller.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.base.frame.Base;
import com.android.base.util.ScreenUtils;
import com.android.ge.R;
import com.android.ge.model.path.PathBean;
import com.android.ge.model.task.TaskBean;
import com.android.ge.widgets.view.RoundProgressBar;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xudengwang on 17/4/8.
 */

public class LearningPathAdapter extends BaseCommonAdapter<PathBean> {
    public LearningPathAdapter(Context context, List<PathBean> datas) {
        super(context, R.layout.item_for_learning_path, datas);
    }

    @Override
    public void convert(ViewHolder holder, PathBean pathBean, int position) {
        // holder.setText(R.id.tv_task_name, taskBean.)


        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.getView(R.id.rel_cover).getLayoutParams();
        params.width = (int) (ScreenUtils.getScreenWidth(mContext) - 34 * ScreenUtils.getScreenDensity(mContext));
        params.height = (int) params.width * 140 / 340;
        holder.getView(R.id.rel_cover).setLayoutParams(params);

        holder.setText(R.id.tv_path_title, pathBean.getTitle());
        setImageFromInternet((ImageView) holder.getView(R.id.iv_path_cover), pathBean.getCover(), R.drawable.demo_course_loading_icon);
    }

}
