package com.tom.common.util;

import com.tom.common.net.JsonCallback;
import com.tom.network.OkGo;
import com.tom.network.model.HttpParams;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/6/6
 * 描述：二次封装的网络请求工具
 */
public class NetHttpUtil {

    public static <T> void getRequets(String url, Object tag, HttpParams params, JsonCallback<T> callback) {
        OkGo.<T>get(url)
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

    public static void cancelTag(Object tag) {
        OkGo.cancelTag(OkGo.getInstance().getOkHttpClient(), tag);
    }

    public static void cancelAll() {
        OkGo.cancelAll(OkGo.getInstance().getOkHttpClient());
    }
}
