package com.tom.kalle.cache;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public enum CacheMode {
    /**
     * Follow the Http standard protocol.
     */
    HTTP,
    /**
     * Follow the Http standard protocol, but it will be cached.
     */
    HTTP_YES_THEN_WRITE_CACHE,
    /**
     * Only get the results from the network.
     */
    NETWORK,
    /**
     * Just get results from the network, and then decide whether to cache according to the Http protocol.
     */
    NETWORK_YES_THEN_HTTP,
    /**
     * Only get the results from the network, but it will be cached.
     */
    NETWORK_YES_THEN_WRITE_CACHE,
    /**
     * Get results first from the network, and from the cache if the network fails.
     */
    NETWORK_NO_THEN_READ_CACHE,
    /**
     * Just get the result from the cache.
     */
    READ_CACHE,
    /**
     * First get the result from the cache, if the cache does not exist, get the result from the network.
     */
    READ_CACHE_NO_THEN_NETWORK,
    /**
     * First get the result from the cache, if the cache does not exist, get results from the network, and follow the http protocol.
     */
    READ_CACHE_NO_THEN_HTTP
}