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
public abstract class SimpleCallback<V> extends Callback<V, String> {

    public SimpleCallback() {
    }

    @Override
    public Type getSucceed() {
        Type superClass = getClass().getGenericSuperclass();
        return ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    @Override
    public Type getFailed() {
        return String.class;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onException(Exception e) {
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onEnd() {
    }
}
