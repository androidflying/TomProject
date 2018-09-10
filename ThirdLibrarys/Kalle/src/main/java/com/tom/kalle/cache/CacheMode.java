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
     * Http标准模式；
     * 发起请求前如果本地已经有缓存，会检查缓存是否过期，
     * 如果没过期则返回缓存数据，如果过期则带上缓存头去服务器做校验。
     * 如果服务器响应304则返回缓存数据，
     * 如果响应其它响应码则读取服务器数据，并根据服务器响应头来决定是否缓存数据到本地。
     * 如果请求失败则是正常失败。
     */
    HTTP,
    /**
     * Follow the Http standard protocol, but it will be cached.
     * 先Http标准协议再写入缓存；
     * 发起请求前如果本地已经有缓存则带缓存头，
     * 在有缓存的时候，服务器可能响应304，则返回缓存数据，
     * 如果服务器响应其它响应码，则读取服务器数据，并把请求成功后的数据缓存到本地。
     * 如果请求失败则是正常失败。
     */
    HTTP_YES_THEN_WRITE_CACHE,
    /**
     * Only get the results from the network.
     * 仅仅请求网络；
     * 发起请求前不管本地是否有缓存，都不会带上缓存头，
     * 请求成功后，不论服务器响应头如何，都不会缓存数据到本地。
     * 如果请求失败则是正常失败。
     */
    NETWORK,
    /**
     * Just get results from the network, and then decide whether to cache according to the Http protocol.
     * 先仅仅网络再按照Http标准协议；
     * 发起请求前不管本地是否有缓存，都不会带上缓存头，
     * 请求成功后根据服务器响应头来决定是否缓存数据到本地。
     * 如果请求失败则是正常失败。
     */
    NETWORK_YES_THEN_HTTP,
    /**
     * Only get the results from the network, but it will be cached.
     * 先仅仅网络再写入缓存；
     * 发起请求前不管本地是否有缓存，都不会带上缓存头，
     * 请求成功后会把数据缓存到本地。
     * 如果请求失败则是正常失败。
     */
    NETWORK_YES_THEN_WRITE_CACHE,
    /**
     * Get results first from the network, and from the cache if the network fails.
     * 先仅仅网络，失败后读取缓存；
     * 发起请求前不管本地是否有缓存，都不会带上缓存头，
     * 请求成功后正常返回，
     * 请求失败后尝试读取缓存，如果缓存不存在则继续按照之前失败的流程走，如果缓存存在则正常返回缓存。
     */
    NETWORK_NO_THEN_READ_CACHE,
    /**
     * Just get the result from the cache.
     * 仅仅读取缓存；
     * 只是去读取缓存，如果缓存不存在则会失败，如果缓存存在就返回缓存。
     */
    READ_CACHE,
    /**
     * First get the result from the cache, if the cache does not exist, get the result from the network.
     * 先读取缓存，缓存不存在再请求网络；
     * 先尝试读取缓存，如果缓存存在就返回缓存，如果缓存不存在就请求网络，
     * 请求成功后不论服务器响应头如何都不存缓存数据。
     * 如果请求失败则是正常失败。
     */
    READ_CACHE_NO_THEN_NETWORK,
    /**
     * First get the result from the cache, if the cache does not exist, get results from the network, and follow the http protocol.
     * 先读取缓存，缓存不存在再Http标准协议；
     * 先尝试读取缓存，如果缓存存在就返回缓存，如果缓存不存在就请求网络，
     * 请求成功后根据服务器响应头来决定是否缓存数据到本地。
     * 如果请求失败则是正常失败。
     */
    READ_CACHE_NO_THEN_HTTP
}