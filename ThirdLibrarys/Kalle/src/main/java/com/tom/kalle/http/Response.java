package com.tom.kalle.http;

import com.tom.kalle.body.ResponseBody;
import com.tom.kalle.util.IOUtils;

import java.io.Closeable;
import java.io.IOException;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public final class Response implements Closeable {

    public static Builder newBuilder() {
        return new Builder();
    }

    private final int mCode;
    private final Headers mHeaders;
    private final ResponseBody mBody;

    private Response(Builder builder) {
        this.mCode = builder.mCode;
        this.mHeaders = builder.mHeaders;
        this.mBody = builder.mBody;
    }

    /**
     * Get the mCode of response.
     */
    public int code() {
        return mCode;
    }

    /**
     * Get http headers.
     */
    public Headers headers() {
        return mHeaders;
    }

    /**
     * Get http body.
     */
    public ResponseBody body() {
        return mBody;
    }

    @Override
    public void close() {
        IOUtils.closeQuietly(mBody);
    }

    /**
     * It is a redirect response code.
     */
    public boolean isRedirect() {
        switch (mCode) {
            case 300:
            case 301:
            case 302:
            case 303:
            case 307:
            case 308:
                return true;
            case 304:
            case 305:
            case 306:
            default:
                return false;
        }
    }

    public static final class Builder {
        private int mCode;
        private Headers mHeaders;
        private ResponseBody mBody;

        public Builder() {
        }

        public Builder code(int code) {
            this.mCode = code;
            return this;
        }

        public Builder headers(Headers headers) {
            this.mHeaders = headers;
            return this;
        }

        public Builder body(ResponseBody body) {
            this.mBody = body;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }
}