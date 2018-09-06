package com.tom.kalle.http;

import java.io.IOException;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public interface Chain {
    /**
     * Get the request in the chain.
     *
     * @return target request.
     */
    Request request();

    /**
     * Proceed to the next request processing.
     *
     * @param request target request.
     * @return {@link Response}.
     * @throws IOException io exception may occur during the implementation, you can handle or throw.
     */
    Response proceed(Request request) throws IOException;

    /**
     * Return {@link Call}, request will be executed on it.
     *
     * @return {@link Call}.
     */
    Call call();
}