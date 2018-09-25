package com.tom.mrvah.util;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/9/25
 * 描述：辅助开发者自定义ID
 */
public class IDUtil {
    private static final long MIN_ID = Long.MIN_VALUE;
    private static final long MAX_ID = Long.MAX_VALUE;
    private static long sCurrentId = MIN_ID;

    private IDUtil() {
    }

    /**
     * 辅助id
     * 有特殊要求的同学可以自己设计
     *
     * @return 返回不重复Id
     */
    public static long getId() {
        if (MAX_ID <= sCurrentId) {
            throw new RuntimeException("it's the biggest");
        }
        return sCurrentId++;
    }
}
