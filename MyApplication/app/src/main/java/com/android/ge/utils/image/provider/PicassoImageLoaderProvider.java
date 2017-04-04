package com.android.ge.utils.image.provider;

import android.content.Context;

import com.android.base.frame.Base;
import com.android.ge.utils.image.ImageRequest;
import com.squareup.picasso.Picasso;


public class PicassoImageLoaderProvider implements IImageLoaderProvider {
    @Override
    public void loadImage(ImageRequest request) {
        Picasso.with(Base.getContext()).load(request.getUrl()).placeholder(request.getPlaceHolder()).into(request.getImageView());
    }

    @Override
    public void loadImage(Context context, ImageRequest request) {
        Picasso.with(context).load(request.getUrl()).placeholder(request.getPlaceHolder()).into(request.getImageView());
    }
}
