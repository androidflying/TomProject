package com.tom.im;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.tom.baselib.ApplicationImpl;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/6/25
 * 描述：
 */
public class AppImpl implements ApplicationImpl {

    @Override
    public void onCreate(@NonNull Application application) {
        initIM();
    }

    private void initIM() {

    }

}