package com.tom.kalle.body;

import android.text.TextUtils;

import com.tom.kalle.Kalle;
import com.tom.kalle.http.Params;
import com.tom.kalle.util.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import static com.tom.kalle.http.Headers.VALUE_APPLICATION_URLENCODED;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class UrlBody extends BasicOutData<StringBody> implements RequestBody {

    public static Builder newBuilder() {
        return new Builder();
    }

    private final Params mParams;
    private final Charset mCharset;
    private final String mContentType;

    private UrlBody(Builder builder) {
        this.mParams = builder.mParams.build();
        this.mCharset = builder.mCharset == null ? Kalle.getConfig().getCharset() : builder.mCharset;
        this.mContentType = TextUtils.isEmpty(builder.mContentType) ? VALUE_APPLICATION_URLENCODED : builder.mContentType;
    }

    /**
     * Copy parameters from url body.
     */
    private Params copyParams() {
        return mParams;
    }

    @Override
    public long length() {
        String body = mParams.toString();
        if (TextUtils.isEmpty(body)) {
            return 0;
        }
        return IOUtils.toByteArray(body, mCharset).length;
    }

    @Override
    public String contentType() {
        return mContentType;
    }

    @Override
    protected void onWrite(OutputStream writer) throws IOException {
        String body = mParams.toString();
        IOUtils.write(writer, body, mCharset);
    }

    public static class Builder {

        private Charset mCharset;
        private String mContentType;
        private Params.Builder mParams;

        private Builder() {
            this.mParams = Params.newBuilder();
        }

        /**
         * Data charset.
         */
        public Builder charset(Charset charset) {
            this.mCharset = charset;
            return this;
        }

        /**
         * Content type.
         */
        public Builder contentType(String contentType) {
            this.mContentType = contentType;
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, int value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, long value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, boolean value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, char value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, double value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, float value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, short value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, CharSequence value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, String value) {
            mParams.add(key, value);
            return this;
        }

        /**
         * Add parameters.
         */
        public Builder param(String key, List<String> values) {
            mParams.add(key, values);
            return this;
        }

        /**
         * Add parameters.
         */
        public Builder params(Params params) {
            mParams.add(params);
            return this;
        }

        /**
         * Remove parameters.
         */
        public Builder removeParam(String key) {
            mParams.remove(key);
            return this;
        }

        /**
         * Clear parameters.
         */
        public Builder clearParams() {
            mParams.clear();
            return this;
        }

        public UrlBody build() {
            return new UrlBody(this);
        }
    }
}