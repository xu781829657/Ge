package com.android.ge.controller.adapter;

import android.content.Context;

import com.android.ge.R;
import com.android.ge.model.NewsBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by xudengwang on 17/3/19.
 */

public class NewsAdapter extends CommonAdapter<NewsBean>{
    public NewsAdapter(Context context,  List<NewsBean> datas) {
        super(context, R.layout.item_for_news, datas);
    }

    @Override
    protected void convert(ViewHolder holder, NewsBean newsBean, int position) {
        holder.setText(R.id.tv_news_info,newsBean.title);

    }

}
