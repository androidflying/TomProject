package com.tom.kalle.simple;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public abstract class Callback<Succeed, Failed> {

    public Callback() {
    }

    /**
     * Get the data type when the business was successful.
     */
    public Type getSucceed() {
        Type superClass = getClass().getGenericSuperclass();
        return ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    /**
     * Get the data type when the business failed.
     */
    public Type getFailed() {
        Type superClass = getClass().getGenericSuperclass();
        return ((ParameterizedType) superClass).getActualTypeArguments()[1];
    }

    /**
     * Time dimensions: The request started.
     */
    public abstract void onStart();

    /**
     * Result dimensions: The request has responded.
     */
    public abstract void onResponse(SimpleResponse<Succeed, Failed> response);

    /**
     * Result dimensions: An abnormality has occurred.
     */
    public abstract void onException(Exception e);

    /**
     * Result dimensions: The request was cancelled.
     */
    public abstract void onCancel();

    /**
     * Time dimensions: The request ended.
     */
    public abstract void onEnd();
}