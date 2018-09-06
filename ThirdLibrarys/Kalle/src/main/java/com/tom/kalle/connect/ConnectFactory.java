package com.tom.kalle.connect;

import com.tom.kalle.http.Request;

import java.io.IOException;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public interface ConnectFactory {
    /**
     * According to the request attribute,
     * and the server to establish a connection.
     * 根据请求属性，与服务器建立连接。
     */
    Connection connect(Request request) throws IOException;
}