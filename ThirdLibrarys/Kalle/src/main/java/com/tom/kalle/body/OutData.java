package com.tom.kalle.body;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public interface OutData {

    /**
     * Returns the size of the data.
     * 返回数据的长度
     */
    long length();

    /**
     * Get the content type of data.
     * 获取数据的内容类型
     */
    String contentType();

    /**
     * OutData data.
     */
    void writeTo(OutputStream writer) throws IOException;
}