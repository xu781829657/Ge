package com.android.ge.controller.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.android.base.frame.Base;
import com.android.ge.R;
import com.android.ge.model.RankUserBean;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xudengwang on 17/4/10.
 */

public class RankAdapter extends BaseCommonAdapter<RankUserBean> {
    public RankAdapter(Context context,  List<RankUserBean> datas) {
        super(context, R.layout.item_for_rank, datas);
    }

    @Override
    protected void convert(ViewHolder holder, RankUserBean bean, int position) {

        holder.setText(R.id.tv_rank, bean.getRank() + "");
        holder.setText(R.id.tv_name, bean.getName());
        //holder.setText(R.id.tv_score, bean.getMembership().getSum_points() + "");

        //holder.setImageFromInternet(R.id.aiv_avatar, bean.getMembership().getAvatar_url(), R.drawable.icon_head);

        switch (bean.getRank()) {
            case 1:

                holder.setImageResource(R.id.iv_rank, R.drawable.no1);
                holder.getView(R.id.iv_rank).setVisibility(View.VISIBLE);
                holder.getView(R.id.tv_rank).setVisibility(View.GONE);

                break;
            case 2:
                holder.setImageResource(R.id.iv_rank, R.drawable.no2);
                holder.getView(R.id.iv_rank).setVisibility(View.VISIBLE);
                holder.getView(R.id.tv_rank).setVisibility(View.GONE);

                break;
            case 3:
                holder.setImageResource(R.id.iv_rank, R.drawable.no3);
                holder.getView(R.id.iv_rank).setVisibility(View.VISIBLE);
                holder.getView(R.id.tv_rank).setVisibility(View.GONE);
                break;
            default:
                holder.getView(R.id.iv_rank).setVisibility(View.GONE);
                holder.getView(R.id.tv_rank).setVisibility(View.VISIBLE);

        }

        //我的排名突出
        if (position == 0) {
            holder.getView(R.id.rel_company_rank).setBackgroundColor(Color.parseColor("#f6f6f6"));
            holder.getView(R.id.view_line).setVisibility(View.GONE);
            holder.setText(R.id.tv_name, Base.string(R.string.me));

        } else {
            holder.getView(R.id.rel_company_rank).setBackgroundColor(Color.parseColor("#ffffff"));
            holder.getView(R.id.view_line).setVisibility(View.VISIBLE);
            //holder.setText(R.id.tv_name, bean.getMembership().getName());
        }
    }
}
