package com.android.tomflying.valid;

import com.android.tomflying.GlobalParams;
import com.android.tomflying.ui.LoginActivity;
import com.tom.baselib.delay.Valid;
import com.tom.baselib.utils.ActivityUtils;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/4/19
 * 描述：登录模块的处理
 */
public class LoginValid implements Valid {

    /**
     * check whether it login in or not
     *
     * @return
     */
    @Override
    public boolean check() {
        return GlobalParams.isLogin();
    }


    /**
     * if check() return false. then doValid was called
     */
    @Override
    public void doValid() {
        ActivityUtils.startActivity(LoginActivity.class);
    }

}
