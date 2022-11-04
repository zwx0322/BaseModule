package com.smartxin.baselibrary.ui.adapter.image;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

/**
 * Author: zhengwenxin
 * CreateDate: 2022/2/22 10:52
 * Description: 图片相关布局绑定适配器
 */
public class ImageBindingAdapter {

    /**
     * 使用Glide载入图片
     * @param imageView
     * @param url
     * @param placeholderRes
     */
    @BindingAdapter(value = {"url", "placeholderRes"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions().placeholder(placeholderRes))
                    .into(imageView);
        }
    }

    /**
     * 使用Glide载入图片
     * 圆形图片 常用头像
     *
     * @param imageView
     * @param url
     */
    @BindingAdapter(value = {"circleImageUrl", "circlePlaceholder"}, requireAll = false)
    public static void setCircleImageUri(ImageView imageView, String url, int circlePlaceholder) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(circlePlaceholder)
                .error(circlePlaceholder)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }
}
