package com.android.tomflying.bean;

import java.io.Serializable;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/6/6
 * 描述：
 */
public class SimpleResponse implements Serializable {
    private static final long serialVersionUID = -1477609349345966116L;

    private int errorCode;
    private String errorMsg;

    public LzyResponse toLzyResponse() {
        LzyResponse lzyResponse = new LzyResponse();
        lzyResponse.errorCode = errorCode;
        lzyResponse.errorMsg = errorMsg;
        return lzyResponse;
    }
}
