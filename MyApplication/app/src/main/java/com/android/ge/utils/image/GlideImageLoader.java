package com.android.ge.utils.image;

import android.content.Context;
import android.widget.ImageView;

import com.android.ge.R;
import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by xudengwang on 17/4/4.
 */

public class GlideImageLoader extends ImageLoader{

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        Glide.with(context).load(path).placeholder(R.drawable.demo_banner_loading_icon).into(imageView);
    }
}
