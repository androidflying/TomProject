package com.hub.welfare.util;

import com.hub.welfare.ConstantValues;
import com.hub.welfare.base.MyActivity;
import com.tom.baselib.utils.BarUtils;
import com.tom.baselib.utils.SPUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/1
 * 描述：夜间模式的工具类
 */
public class ThemeUtils {

    public static void setDayNight(MyActivity activity) {
        if (!getDayNight()) {
            setNightTheme(activity);
        } else {
            setDayTheme(activity);
        }
    }

    public static void setDayNight(MyActivity activity, boolean isNight) {
        if (isNight) {
            setNightTheme(activity);
        } else {
            setDayTheme(activity);
        }
    }

    public static boolean getDayNight() {
        return SPUtils.getInstance().getBoolean(ConstantValues.IS_NIGHT);
    }

    private static void setNightTheme(MyActivity activity) {
        activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        BarUtils.setStatusBarLightMode(activity, false);
        SPUtils.getInstance().put(ConstantValues.IS_NIGHT, true);
    }

    private static void setDayTheme(MyActivity activity) {
        activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        BarUtils.setStatusBarLightMode(activity, true);
        SPUtils.getInstance().put(ConstantValues.IS_NIGHT, false);
    }
}
