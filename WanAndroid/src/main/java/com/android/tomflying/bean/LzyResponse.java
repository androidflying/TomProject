package com.android.tomflying.bean;

import java.io.Serializable;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/6/6
 * 描述：
 */
public class LzyResponse<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    public int errorCode;
    public String errorMsg;
    public T data;
}
