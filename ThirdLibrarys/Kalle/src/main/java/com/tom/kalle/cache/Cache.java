package com.tom.kalle.cache;

import com.tom.kalle.http.Headers;

import java.io.Serializable;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class Cache implements Serializable {

    private String mKey;
    private int mCode;
    private Headers mHeaders;
    private byte[] mBody;
    private long mExpires;

    public Cache() {
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        this.mKey = key;
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        mCode = code;
    }

    public Headers getHeaders() {
        return mHeaders;
    }

    public void setHeaders(Headers headers) {
        mHeaders = headers;
    }

    public byte[] getBody() {
        return mBody;
    }

    public void setBody(byte[] body) {
        this.mBody = body;
    }

    public long getExpires() {
        return mExpires;
    }

    public void setExpires(long expires) {
        this.mExpires = expires;
    }
}
