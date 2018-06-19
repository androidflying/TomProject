package com.tom.common.valid;

import com.tom.baselib.delay.Action;
import com.tom.baselib.utils.LogUtils;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/4/20
 * 描述：
 */
public class LoginAction implements Action {
    @Override
    public void call() {
        LogUtils.e("登录之后的操作");
    }
}
