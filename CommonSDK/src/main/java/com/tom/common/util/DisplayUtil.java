package com.tom.common.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tom.baselib.utils.Utils;
import com.tom.common.R;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/4/24
 * 描述：图片展示第三方库接口
 */
public class DisplayUtil {

    /**
     * 加载显示一般的小头像
     *
     * @param iv
     * @param url
     */
    public static void showSimpleHeader(ImageView iv, String url) {

        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
//                .error()
                .priority(Priority.HIGH)
                .placeholder(R.drawable.default_pic);
        Glide.with(Utils.getApp())
                .load(url)
                .apply(requestOptions)
                .into(iv);

    }

    /**
     * 加载显示一般需要展示的图片
     *
     * @param iv
     * @param url
     */
    public static void showSimpleNormal(ImageView iv, String url) {
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
//                .error()
                .priority(Priority.NORMAL)
                .placeholder(R.drawable.default_pic);
        Glide.with(Utils.getApp())
                .load(url)
                .apply(requestOptions)
                .into(iv);
    }

    /**
     * 加载不带缓存的图片
     *
     * @param iv
     * @param url
     */
    public static void showSimpleWithoutCache(ImageView iv, String url) {
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .error()
                .priority(Priority.IMMEDIATE)
                .placeholder(R.drawable.default_pic);

        Glide.with(Utils.getApp())
                .load(url)
                .apply(requestOptions)
                .into(iv);
    }


}
