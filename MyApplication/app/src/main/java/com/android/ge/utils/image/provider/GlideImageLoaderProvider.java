package com.android.ge.utils.image.provider;

import android.content.Context;

import com.android.base.frame.Base;
import com.android.ge.utils.image.ImageRequest;
import com.bumptech.glide.Glide;

/**
 * Created by xudengwang on 17/4/4.
 */

public class GlideImageLoaderProvider implements IImageLoaderProvider{
    @Override
    public void loadImage(ImageRequest request) {
        Glide.with(Base.getContext()).load(request.getUrl()).placeholder(request.getPlaceHolder()).into(request.getImageView());
    }

    @Override
    public void loadImage(Context context, ImageRequest request) {

    }
}
