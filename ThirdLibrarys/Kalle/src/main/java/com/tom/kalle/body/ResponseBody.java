package com.tom.kalle.body;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public interface ResponseBody extends Closeable {

    /**
     * Transform the response data into a string.
     * 将响应数据转换为字符串。
     */
    String string() throws IOException;

    /**
     * Transform the response data into a byte array.
     * 将响应数据转换为字节数组。
     */
    byte[] byteArray() throws IOException;

    /**
     * Transform the response data into a stream.
     * 将响应数据转换为输入流。
     */
    InputStream stream() throws IOException;
}