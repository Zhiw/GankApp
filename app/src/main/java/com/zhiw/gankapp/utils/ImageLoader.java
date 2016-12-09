package com.zhiw.gankapp.utils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import android.content.Context;
import android.widget.ImageView;

/**
 * ClassName: ImageLoader
 * Desc:
 * Created by zhiw on 16/12/9.
 */

public class ImageLoader {

    /**
     * Load image from resource and set it into imageView.
     * @param context context.
     * @param resource could be Uri/String/File/ResourceId.
     * @param imageView imageView.
     */
    public static void load(Context context, Object resource, ImageView imageView) {
        Glide.with(context)
                .load(resource)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

}
