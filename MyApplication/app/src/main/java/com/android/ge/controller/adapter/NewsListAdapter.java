package com.android.ge.controller.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

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

public class NewsListAdapter extends BaseCommonAdapter<NewsBean> implements MultiItemTypeAdapter.OnItemClickListener {
    public NewsListAdapter(Context context, List<NewsBean> datas) {
        super(context, R.layout.item_for_news_more, datas);
        setOnItemClickListener(this);
    }

    @Override
    protected void convert(ViewHolder holder, NewsBean newsBean, int position) {

        holder.setText(R.id.tv_title, newsBean.getTitle());
        holder.setText(R.id.tv_time, newsBean.getCreated_at());
        holder.setText(R.id.tv_read, newsBean.getPv());

        setImageFromInternet((ImageView) holder.getView(R.id.iv_cover), newsBean.getCover());

    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        NewsBean newsBean = mDatas.get(position);
        Bundle bundle = new Bundle();
        bundle.putString(CommonConstant.PARAM_NEWS_ID, newsBean.getId());
        gotoActivity(NewsWebActivity.class, bundle);
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

}
