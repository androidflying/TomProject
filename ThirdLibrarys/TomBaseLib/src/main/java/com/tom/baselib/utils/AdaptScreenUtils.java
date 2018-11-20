package com.tom.baselib.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/20
 * 描述：今日头条适配方案
 */
public class AdaptScreenUtils {

    private static DisplayMetrics appDm;

    /**
     * Adapt for the horizontal screen, and call it in [android.app.Activity.getResources].
     */
    public static Resources adaptWidth(Resources resources, int designWidth) {
        DisplayMetrics dm = resources.getDisplayMetrics();
        float newXdpi = dm.xdpi = (dm.widthPixels * 72f) / designWidth;
        setAppDmXdpi(newXdpi);
        return resources;
    }

    /**
     * Adapt for the vertical screen, and call it in [android.app.Activity.getResources].
     */
    public static Resources adaptHeight(Resources resources, int designHeight) {
        DisplayMetrics dm = resources.getDisplayMetrics();
        float newXdpi = dm.xdpi = (dm.heightPixels * 72f) / designHeight;
        setAppDmXdpi(newXdpi);
        return resources;
    }

    /**
     * @param resources The resources.
     * @return the resource
     */
    public static Resources closeAdapt(Resources resources) {
        DisplayMetrics dm = resources.getDisplayMetrics();
        float newXdpi = dm.xdpi = dm.density * 72;
        setAppDmXdpi(newXdpi);
        return resources;
    }

    /**
     * Value of pt to value of px.
     *
     * @param ptValue The value of pt.
     * @return value of px
     */
    public static int pt2Px(float ptValue) {
        DisplayMetrics metrics = Utils.getApp().getResources().getDisplayMetrics();
        return (int) (ptValue * metrics.xdpi / 72f + 0.5);
    }

    /**
     * Value of px to value of pt.
     *
     * @param pxValue The value of px.
     * @return value of pt
     */
    public static int px2Pt(float pxValue) {
        DisplayMetrics metrics = Utils.getApp().getResources().getDisplayMetrics();
        return (int) (pxValue * 72 / metrics.xdpi + 0.5);
    }

    private static void setAppDmXdpi(final float xdpi) {
        if (appDm == null) {
            appDm = Utils.getApp().getResources().getDisplayMetrics();
        }
        appDm.xdpi = xdpi;
    }
}
