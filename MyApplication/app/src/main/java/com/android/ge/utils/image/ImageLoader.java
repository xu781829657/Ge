package com.android.ge.utils.image;


import com.android.ge.utils.image.provider.GlideImageLoaderProvider;
import com.android.ge.utils.image.provider.IImageLoaderProvider;
import com.android.ge.utils.image.provider.PicassoImageLoaderProvider;

public class ImageLoader {

    private static volatile IImageLoaderProvider mProvider;

    public static IImageLoaderProvider getProvider() {
        if (mProvider == null) {
            synchronized (ImageLoader.class) {
                if (mProvider == null) {
                    mProvider = new GlideImageLoaderProvider();
                }
            }
        }
        return mProvider;
    }

}
