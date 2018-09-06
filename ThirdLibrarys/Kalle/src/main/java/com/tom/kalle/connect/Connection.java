package com.tom.kalle.connect;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public interface Connection extends Closeable {

    /**
     * Gets output stream for socket.
     */
    OutputStream getOutputStream() throws IOException;

    /**
     * Gets response code for server.
     */
    int getCode() throws IOException;

    /**
     * Gets response headers for server.
     */
    Map<String, List<String>> getHeaders() throws IOException;

    /**
     * Gets input stream for socket.
     */
    InputStream getInputStream() throws IOException;

    /**
     * Cancel connect.
     */
    void disconnect();

}