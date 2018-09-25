package com.tom.mrvah.adapter;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/9/25
 * 描述：错误实体类的适配器
 */
@FunctionalInterface
public interface ErrorEntityAdapter<T> {
    T createErrorEntity(int type, int level);
}
