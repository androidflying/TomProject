package com.tom.baselib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tom.baselib.utils.CrashUtils;
import com.tom.baselib.utils.LogUtils;
import com.tom.baselib.utils.ProcessUtils;

import java.util.ArrayList;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/4/18
 * 描述：基类Application
 */
public abstract class BaseApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        if (ProcessUtils.isMainProcess(this)) {
            initARouter();
            initNetWork();
            initLog();
            initCrash();
        }
        modulesApplicationInit();

    }

    /**
     * 初始化网络请求框架，抽象出来可以在具体的项目当中使用不同的网络框架
     */
    protected abstract void initNetWork();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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


    private void initLog() {
        final LogUtils.Config config = LogUtils.getConfig()
                // 设置 log 总开关，包括输出到控制台和文件，默认开
                .setLogSwitch(BuildConfig.DEBUG)
                // 设置是否输出到控制台开关，默认开
                .setConsoleSwitch(BuildConfig.DEBUG)
                // 设置 log 全局标签，默认为空
                .setGlobalTag(null)
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                // 设置 log 头信息开关，默认为开
                .setLogHeadSwitch(true)
                // 打印 log 时是否存到文件的开关，默认关
                .setLog2FileSwitch(false)
                // 当自定义路径为空时，写入应用的/cache/log/目录中
                .setDir("")
                // 当文件前缀为空时，默认为"util"，即写入文件为"util-yyyy-MM-dd.txt"
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
                .setStackDeep(1)
                // 设置栈偏移，比如二次封装的话就需要设置，默认为 0
                .setStackOffset(0)
                // 设置日志可保留天数，默认为 -1 表示无限时长
                .setSaveDays(3)
                // 新增 ArrayList 格式化器，默认已支持 Array, Throwable, Bundle, Intent 的格式化输出
                .addFormatter(new LogUtils.IFormatter<ArrayList>() {
                    @Override
                    public String format(ArrayList list) {
                        return "LogUtils Formatter ArrayList { " + list.toString() + " }";
                    }
                });
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
            initCrashReport();
        }
    }

    /**
     * 组件化开发中各个模块中需要进行初始化的操作
     */
    protected abstract void modulesApplicationInit();

    /**
     * 初始化异常收集提交
     */
    public abstract void initCrashReport();


    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }

}
