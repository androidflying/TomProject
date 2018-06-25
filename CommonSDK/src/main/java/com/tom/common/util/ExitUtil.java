package com.tom.common.util;

import android.app.ActivityManager;
import android.content.Context;

import com.tom.baselib.utils.ActivityUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/6/25
 * 描述：
 */
public class ExitUtil {

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
