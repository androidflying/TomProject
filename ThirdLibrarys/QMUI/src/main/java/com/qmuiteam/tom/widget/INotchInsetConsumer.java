package com.qmuiteam.tom.widget;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/7/18
 * 描述：
 */
public interface INotchInsetConsumer {
    /**
     * @return if true stop dispatch to child view
     */
    boolean notifyInsetMaybeChanged();
}