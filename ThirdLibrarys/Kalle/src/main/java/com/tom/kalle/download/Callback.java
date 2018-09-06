package com.tom.kalle.download;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public interface Callback {
    /**
     * Time dimensions: The request started.
     */
    void onStart();

    /**
     * Result dimensions: File download completed.
     */
    void onFinish(String path);

    /**
     * Result dimensions: An abnormality has occurred.
     */
    void onException(Exception e);

    /**
     * Result dimensions: The request was cancelled.
     */
    void onCancel();

    /**
     * Time dimensions: The request ended.
     */
    void onEnd();
}