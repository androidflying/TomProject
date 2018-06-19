package com.tom.im;

import com.tom.baselib.utils.SPUtils;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 描述：全局变量的定义及描述
 */
public class GlobalParams {

    public static boolean isLogin() {
        return SPUtils.getInstance().getBoolean(ConstantValues.IS_LOGIN);
    }

    public static void setIsLogin(boolean isLogin) {
        SPUtils.getInstance().put(ConstantValues.IS_LOGIN, isLogin);
    }

}
