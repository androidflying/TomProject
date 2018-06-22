package com.tom.common.base;

import android.app.ActivityManager;
import android.content.Context;

import com.tom.baselib.BaseApplication;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.common.BuildConfig;
import com.tom.common.util.PushUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/6/8
 * 描述：
 */
public class CommonApplication extends BaseApplication {

    {
        //TODO 修改各个平台的配置信息
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //友盟组件初始化
        initUmeng();
    }

    /**
     * 初始化common库
     * 参数1:上下文，不能为空
     * 参数2:【友盟+】 AppKey
     * 参数3:【友盟+】 Channel
     * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
     * 参数5:Push推送业务的secret
     */
    private void initUmeng() {
        UMConfigure.init(this,
                "58edcfeb310c93091c000be2",
                "Umeng",
                UMConfigure.DEVICE_TYPE_PHONE,
                "1fe6a20054bcef865eeb0991ee84525b");
        //设置是否输出日志
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
        //设置日志是否加密
        UMConfigure.setEncryptEnabled(!BuildConfig.DEBUG);

        initPush();
    }

    private void initPush() {
        PushUtil.register();

        //设置常用人性化配置
        //设置通知栏显示条数
        PushUtil.setDisplayNotificationNumber(1);
    }

    @Override
    public void initCrashReport() {
        //线上日志收集
    }


    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            ActivityUtils.finishAllActivities();
            ActivityManager manager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            MobclickAgent.onKillProcess(context);
            manager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception ignored) {
        }
    }
}
