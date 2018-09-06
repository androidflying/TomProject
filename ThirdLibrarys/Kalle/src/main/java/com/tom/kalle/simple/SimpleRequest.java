package com.tom.kalle.simple;

import com.tom.kalle.converter.Converter;
import com.tom.kalle.http.Headers;
import com.tom.kalle.http.Url;
import com.tom.kalle.cache.CacheMode;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public interface SimpleRequest {
    /**
     * Get the file download address.
     */
    Url url();

    /**
     * Get headers.
     */
    Headers headers();

    /**
     * Get cache mode.
     */
    CacheMode cacheMode();

    /**
     * Get cache key.
     */
    String cacheKey();

    /**
     * Get converter.
     */
    Converter converter();
}