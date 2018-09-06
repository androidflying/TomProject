package com.tom.kalle.interceptor;

import com.tom.kalle.http.Response;
import com.tom.kalle.http.Chain;

import java.io.IOException;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public interface Interceptor {

    /**
     * When intercepting the {@link Chain},
     * here can do some signature and login operation,
     * it should send a response and return.
     *
     * @param chain request chain.
     * @return the server connection.
     * @throws IOException io exception may occur during the implementation, you can handle or throw.
     */
    Response intercept(Chain chain) throws IOException;
}