package com.tom.kalle.http;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public interface ProgressBar<T> {
    /**
     * Progress has changed.
     */
    void progress(T origin, int progress);
}