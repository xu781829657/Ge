package com.android.ge.controller.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.ge.R;
import com.android.ge.constant.CommonConstant;
import com.android.ge.model.NewsBean;
import com.android.ge.ui.webview.NewsWebActivity;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xudengwang on 17/3/19.
 */

public class NewsMoreAdapter extends BaseCommonAdapter<NewsBean> implements MultiItemTypeAdapter.OnItemClickListener {
    public NewsMoreAdapter(Context context, List<NewsBean> datas) {
        super(context, R.layout.item_for_news, datas);
        setOnItemClickListener(this);
    }

    @Override
    protected void convert(ViewHolder holder, NewsBean newsBean, int position) {
        holder.setText(R.id.tv_news_info, newsBean.getTitle());

        if (position == mDatas.size() - 1) {
            holder.getView(R.id.view_line).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.view_line).setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        NewsBean newsBean = mDatas.get(position);
        Bundle bundle = new Bundle();
        bundle.putString(CommonConstant.PARAM_NEWS_ID,newsBean.getId());
        gotoActivity(NewsWebActivity.class, bundle);
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

}
