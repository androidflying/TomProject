package com.android.tomflying.base;

import com.android.tomflying.BuildConfig;
import com.tencent.bugly.Bugly;
import com.tom.baselib.BaseApplication;


/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/6/8
 * 描述：
 */
public class MyApplication extends BaseApplication {

    @Override
    protected void modulesApplicationInit() {

    }

    @Override
    public void initCrashReport() {
        Bugly.init(getApplicationContext(), "e332b521ef", BuildConfig.DEBUG);
    }
}
