package com.tom.baselib.delay;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/4/19
 * 描述：自定义运行异常
 */
public class ValidException extends RuntimeException {

    public ValidException() {
    }

    public ValidException(String message) {
        super(message);
    }
}
