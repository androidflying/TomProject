package com.android.tomflying;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.tom.banner.loader.ImageLoader;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/8
 * 描述：
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object url, ImageView iv) {
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .error(R.drawable.no_banner)
                .priority(Priority.NORMAL);
        Glide.with(context)
                .load((String) url)
                .apply(requestOptions)
                .into(iv);
    }
}
