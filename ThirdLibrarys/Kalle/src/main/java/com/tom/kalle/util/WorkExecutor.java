package com.tom.kalle.util;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class WorkExecutor implements Executor {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Request #" + mCount.getAndIncrement());
        }
    };

    private ThreadPoolExecutor mPoolExecutor;

    public WorkExecutor() {
        mPoolExecutor = new ThreadPoolExecutor(
                Math.max(2, Math.min(CPU_COUNT - 1, 4)),
                CPU_COUNT * 2 + 1,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(128),
                THREAD_FACTORY);
        mPoolExecutor.allowCoreThreadTimeOut(true);
    }

    @Override
    public void execute(Runnable command) {
        mPoolExecutor.execute(command);
    }
}