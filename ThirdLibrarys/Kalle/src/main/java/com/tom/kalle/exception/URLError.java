package com.tom.kalle.exception;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class URLError extends ConnectException {
    public URLError(String message) {
        super(message);
    }

    public URLError(String message, Throwable cause) {
        super(message, cause);
    }
}