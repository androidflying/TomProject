package com.hub.welfare.base;

import com.hub.welfare.BuildConfig;
import com.tom.baselib.BaseApplication;
import com.tom.network.OkGo;
import com.tom.network.cache.CacheMode;
import com.tom.network.cookie.CookieJarImpl;
import com.tom.network.cookie.store.DBCookieStore;
import com.tom.network.cookie.store.MemoryCookieStore;
import com.tom.network.interceptor.HttpLoggingInterceptor;
import com.tom.network.model.HttpHeaders;
import com.tom.network.model.HttpParams;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/1
 * 描述：
 */
public class MyApplication extends BaseApplication {
    @Override
    protected void initNetWork() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkHttp");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        //添加OkGo默认debug日志
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor);
        }

        //超时时间设置，默认60秒
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
        //使用sp保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));

        //使用数据库保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));

        //使用内存保持cookie，app退出后，cookie消失
//        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));


        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                .setCacheTime(6 * 60 * 60 * 1000)
                .setRetryCount(3)
        ;
    }

    @Override
    protected void modulesApplicationInit() {

    }

    @Override
    public void initCrashReport() {

    }
}
