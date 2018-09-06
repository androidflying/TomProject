package com.tom.kalle.simple;

import android.text.TextUtils;

import com.tom.kalle.converter.Converter;
import com.tom.kalle.http.BodyRequest;
import com.tom.kalle.http.Canceller;
import com.tom.kalle.http.RequestMethod;
import com.tom.kalle.http.Url;
import com.tom.kalle.cache.CacheMode;

import java.lang.reflect.Type;

import static com.tom.kalle.cache.CacheMode.HTTP;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class SimpleBodyRequest extends BodyRequest implements SimpleRequest {

    public static SimpleBodyRequest.Api newApi(Url url, RequestMethod method) {
        return new SimpleBodyRequest.Api(url, method);
    }

    private final CacheMode mCacheMode;
    private final String mCacheKey;

    private final Converter mConverter;

    private SimpleBodyRequest(Api api) {
        super(api);
        this.mCacheMode = api.mCacheMode == null ? HTTP : api.mCacheMode;
        this.mCacheKey = TextUtils.isEmpty(api.mCacheKey) ? url().toString() : api.mCacheKey;

        this.mConverter = api.mConverter;
    }

    @Override
    public CacheMode cacheMode() {
        return mCacheMode;
    }

    @Override
    public String cacheKey() {
        return mCacheKey;
    }

    @Override
    public Converter converter() {
        return mConverter;
    }

    public static class Api extends BodyRequest.Api<Api> {

        private CacheMode mCacheMode;
        private String mCacheKey;

        private Converter mConverter;

        private Api(Url url, RequestMethod method) {
            super(url, method);
        }

        public Api cacheMode(CacheMode cacheMode) {
            this.mCacheMode = cacheMode;
            return this;
        }

        public Api cacheKey(String cacheKey) {
            this.mCacheKey = cacheKey;
            return this;
        }

        public Api converter(Converter converter) {
            this.mConverter = converter;
            return this;
        }

        public <S, F> SimpleResponse<S, F> perform(Type succeed, Type failed) throws Exception {
            return RequestManager.getInstance().perform(new SimpleBodyRequest(this), succeed, failed);
        }

        public <S, F> Canceller perform(Callback<S, F> callback) {
            return RequestManager.getInstance().perform(new SimpleBodyRequest(this), callback);
        }
    }
}