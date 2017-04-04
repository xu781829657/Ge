package com.android.ge.utils.image.provider;

import android.content.Context;

import com.android.ge.utils.image.ImageRequest;


public interface IImageLoaderProvider {

    void loadImage(ImageRequest request);

    void loadImage(Context context, ImageRequest request);
}
