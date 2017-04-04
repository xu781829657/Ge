package com.android.ge.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.android.ge.utils.image.ImageLoader;
import com.android.ge.utils.image.ImageRequest;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


/**
 * Created by xudengwang on 17/4/3.
 */
public abstract class BaseCommonAdapter<T> extends CommonAdapter<T> {
    public BaseCommonAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, layoutId,datas);
    }


    /**
     * 加载网络上的图片
     *
     * @param viewId
     * @param url
     */
    public void setImageFromInternet(ImageView iv, String url) {
        ImageRequest imageRequest = new ImageRequest.Builder().imgView(iv).url(url).create();
        ImageLoader.getProvider().loadImage(imageRequest);
    }

    public void setImageFromInternet(ImageView iv, String url, int defaultDrawableId) {
        ImageRequest imageRequest = new ImageRequest.Builder().imgView(iv).placeHolder(defaultDrawableId).url(url).create();
        ImageLoader.getProvider().loadImage(imageRequest);
    }

}
