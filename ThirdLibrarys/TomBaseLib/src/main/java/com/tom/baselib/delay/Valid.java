package com.tom.baselib.delay;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/4/19
 * 描述：
 */
public interface Valid {

    /**
     * 是否满足检验器的要求，如果不满足的话，则执行doAction方法。如果满足，则执行目标action
     *
     * @return
     */
    boolean check();

    void doValid();
}