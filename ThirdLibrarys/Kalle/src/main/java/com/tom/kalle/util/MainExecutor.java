package com.tom.kalle.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class MainExecutor implements Executor {

    private Handler mHandler;

    public MainExecutor() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void execute(Runnable command) {
        mHandler.post(command);
    }
}