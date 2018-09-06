package com.tom.kalle.simple;

import com.tom.kalle.http.Headers;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public final class SimpleResponse<Succeed, Failed> {

    public static <Succeed, Failed> Builder<Succeed, Failed> newBuilder() {
        return new Builder<>();
    }

    private final int mCode;
    private final Headers mHeaders;
    private final boolean mFromCache;

    private final Succeed mSucceed;
    private final Failed mFailed;

    private SimpleResponse(Builder<Succeed, Failed> builder) {
        this.mCode = builder.mCode;
        this.mHeaders = builder.mHeaders;
        this.mFromCache = builder.mFromCache;

        this.mSucceed = builder.mSucceed;
        this.mFailed = builder.mFailed;
    }

    /**
     * Get the headers code of handle.
     */
    public int code() {
        return mCode;
    }

    /**
     * Get http headers headers.
     */
    public Headers headers() {
        return mHeaders;
    }

    /**
     * Whether the data returned from the cache.
     *
     * @return true: the data from cache, false: the data from network.
     */
    public boolean fromCache() {
        return mFromCache;
    }

    /**
     * Business successful.
     */
    public boolean isSucceed() {
        return mFailed == null || mSucceed != null;
    }

    /**
     * Get business success data.
     */
    public Succeed succeed() {
        return mSucceed;
    }

    /**
     * Get business failure data.
     */
    public Failed failed() {
        return mFailed;
    }

    public static final class Builder<Succeed, Failed> {
        private int mCode;
        private Headers mHeaders;
        private boolean mFromCache;

        private Failed mFailed;
        private Succeed mSucceed;

        private Builder() {
        }

        public Builder<Succeed, Failed> code(int code) {
            this.mCode = code;
            return this;
        }

        public Builder<Succeed, Failed> headers(Headers headers) {
            this.mHeaders = headers;
            return this;
        }

        public Builder<Succeed, Failed> fromCache(boolean fromCache) {
            this.mFromCache = fromCache;
            return this;
        }

        public Builder<Succeed, Failed> succeed(Succeed succeed) {
            this.mSucceed = succeed;
            return this;
        }

        public Builder<Succeed, Failed> failed(Failed failed) {
            this.mFailed = failed;
            return this;
        }

        public SimpleResponse<Succeed, Failed> build() {
            return new SimpleResponse<>(this);
        }
    }
}