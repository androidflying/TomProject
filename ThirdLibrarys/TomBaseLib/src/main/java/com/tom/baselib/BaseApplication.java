package com.tom.baselib;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.baselib.utils.CrashUtils;
import com.tom.baselib.utils.LogUtils;
import com.tom.baselib.utils.Utils;
import com.tom.network.OkGo;
import com.tom.network.cache.CacheMode;
import com.tom.network.cookie.CookieJarImpl;
import com.tom.network.cookie.store.MemoryCookieStore;
import com.tom.network.interceptor.HttpLoggingInterceptor;
import com.tom.network.model.HttpHeaders;
import com.tom.network.model.HttpParams;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/4/18
 * 描述：基类Application
 */
public class BaseApplication extends MultiDexApplication {



    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        Utils.init(this);
        initARouter();
        initNetWork();
        initLog();
        initCrash();

        modulesApplicationInit();

    }


    private void initARouter() {
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }

        ARouter.init(this);
    }

    private void initNetWork() {
        HttpHeaders headers = new HttpHeaders();
        HttpParams params = new HttpParams();

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

//        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));

        //使用内存保持cookie，app退出后，cookie消失
        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));


        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.NO_CACHE)
                .setRetryCount(3)
                .addCommonHeaders(headers)
                .addCommonParams(params)
        ;
    }

    private void initLog() {
        final LogUtils.Config config = LogUtils.getConfig()
                // 设置 log 总开关，包括输出到控制台和文件，默认开
                .setLogSwitch(BuildConfig.DEBUG)
                // 设置是否输出到控制台开关，默认开
                .setConsoleSwitch(BuildConfig.DEBUG)
                // 设置 log 全局标签，默认为空
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                .setGlobalTag(null)
                // 设置 log 头信息开关，默认为开
                .setLogHeadSwitch(true)
                // 打印 log 时是否存到文件的开关，默认关
                .setLog2FileSwitch(false)
                // 当自定义路径为空时，写入应用的/cache/log/目录中
                .setDir("")
                // 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
                .setFilePrefix("")
                // 输出日志是否带边框开关，默认开
                .setBorderSwitch(true)
                // 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
                .setSingleTagSwitch(true)
                // log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setConsoleFilter(LogUtils.V)
                // log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(LogUtils.V)
                // log 栈深度，默认为 1
                .setStackDeep(1);

        LogUtils.d(config.toString());
    }

    @SuppressLint("MissingPermission")
    public void initCrash() {
        if (BuildConfig.DEBUG) {
            CrashUtils.init(new CrashUtils.OnCrashListener() {
                @Override
                public void onCrash(String crashInfo, Throwable e) {
                    LogUtils.e(crashInfo);
                }
            });
        } else {
            //TODO 上线版本对异常的处理需求
            initCrashReport();
        }
    }

    public void initCrashReport() {

    }


    private void modulesApplicationInit() {
        for (String moduleImpl : ConfigModule.MODULESLIST) {
            try {
                Class<?> clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof ApplicationImpl) {
                    ((ApplicationImpl) obj).onCreate(this);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

}
