package com.android.ge.controller.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.ge.R;
import com.android.ge.model.login.OrganResultInfo;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xudengwang on 2017/3/30.
 */

public class OrganListAdapter extends CommonAdapter<OrganResultInfo.OrganBean> implements MultiItemTypeAdapter.OnItemClickListener {
    private int selectPosition = -1;
    private IOrganSelectListener mListener;

    public OrganListAdapter(Context context, List<OrganResultInfo.OrganBean> datas, IOrganSelectListener listener) {
        super(context, R.layout.item_for_organ, datas);
        this.mListener = listener;
    }


    @Override
    protected void convert(ViewHolder holder, OrganResultInfo.OrganBean organBean, int position) {

        holder.setText(R.id.tv_organ_name, organBean.getName());
        if (selectPosition == position) {
            holder.getView(R.id.rel_item_organ).setBackgroundResource(R.drawable.shape_roud_rectangel_bg_item_organ_select);
            holder.getView(R.id.iv_select).setVisibility(View.VISIBLE);
            ((TextView) holder.getView(R.id.tv_organ_name)).setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.getView(R.id.rel_item_organ).setBackgroundResource(R.drawable.shape_roud_rectangel_bg_item_organ_normal);
            holder.getView(R.id.iv_select).setVisibility(View.GONE);
            ((TextView) holder.getView(R.id.tv_organ_name)).setTextColor(Color.parseColor("#AFB9C6"));
        }

    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        selectPosition = position;
        notifyDataSetChanged();

        if (mListener != null) {
            mListener.select(mDatas.get(position));
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }


    public interface IOrganSelectListener {
        void select(OrganResultInfo.OrganBean bean);
    }


}
