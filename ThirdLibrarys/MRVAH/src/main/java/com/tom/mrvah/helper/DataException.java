package com.tom.mrvah.helper;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/9/25
 * 描述：处理数据的时候异常
 */
class DataException extends RuntimeException {

    DataException() {
    }

    DataException(String message) {
        super(message);
    }
}
