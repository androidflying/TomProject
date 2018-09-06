package com.tom.kalle.exception;

import com.tom.kalle.http.Headers;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class DownloadError extends ReadException {

    private int mCode;
    private Headers mHeaders;

    public DownloadError(int code, Headers headers, String message) {
        super(message);
        this.mCode = code;
        this.mHeaders = headers;
    }

    public DownloadError(int code, Headers headers, Throwable cause) {
        super(cause);
        this.mCode = code;
        this.mHeaders = headers;
    }

    public int getCode() {
        return mCode;
    }

    public Headers getHeaders() {
        return mHeaders;
    }
}