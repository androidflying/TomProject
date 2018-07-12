package com.android.tomflying.util;

import com.tom.network.OkGo;
import com.tom.network.callback.StringCallback;
import com.tom.network.model.HttpParams;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/6/6
 * 描述：
 */
public class OkHttpUtil {
    public static <T> void getRequets(String url, Object tag, HttpParams params, JsonCallback<T> callback) {
        OkGo.<T>get(url)
                .tag(tag)
                .params(params)
                .execute(callback);
    }

    public static void getRequets(String url, Object tag, HttpParams params, StringCallback callback) {
        OkGo.<String>get(url)
                .tag(tag)
                .params(params)
                .execute(callback);
    }

    public static <T> void postRequest(String url, Object tag, HttpParams params, JsonCallback<T> callback) {
        OkGo.<T>post(url)
                .tag(tag)
                .params(params)
                .execute(callback);
    }


    public static void postRequest(String url, Object tag, HttpParams params, StringCallback callback) {
        OkGo.<String>post(url)
                .tag(tag)
                .params(params)
                .execute(callback);
    }
}
