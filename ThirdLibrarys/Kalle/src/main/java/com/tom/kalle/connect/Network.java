package com.tom.kalle.connect;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public interface Network {

    /**
     * The network has always been available.
     */
    Network DEFAULT = new Network() {
        @Override
        public boolean isAvailable() {
            return true;
        }
    };

    /**
     * Check the network is enable.
     */
    boolean isAvailable();
}