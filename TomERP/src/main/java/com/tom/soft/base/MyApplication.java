package com.tom.soft.base;

import com.tom.baselib.BaseApplication;

import cn.bmob.v3.Bmob;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 描述：自定义Application
 */
public class MyApplication extends BaseApplication {

    @Override
    protected void initNetWork() {
        Bmob.initialize(this, "e43506375002b16bca25edb6f0033ecd");
    }

    @Override
    protected void modulesApplicationInit() {

    }

    @Override
    public void initCrashReport() {
//        Bugly.init(getApplicationContext(), "e332b521ef", BuildConfig.DEBUG);
    }
}
