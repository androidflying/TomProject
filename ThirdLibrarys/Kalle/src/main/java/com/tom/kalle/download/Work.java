package com.tom.kalle.download;

import com.tom.kalle.http.Canceller;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class Work<T extends Download> extends FutureTask<String> implements Canceller {

    private BasicWorker<T> mWorker;
    private final Callback mCallback;

    Work(BasicWorker<T> work, Callback callback) {
        super(work);
        this.mWorker = work;
        this.mCallback = callback;
    }

    @Override
    public void run() {
        mCallback.onStart();
        super.run();
    }

    @Override
    protected void done() {
        try {
            mCallback.onFinish(get());
        } catch (CancellationException e) {
            mCallback.onCancel();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (isCancelled()) {
                mCallback.onCancel();
            } else if (cause != null && cause instanceof Exception) {
                mCallback.onException((Exception) cause);
            } else {
                mCallback.onException(new Exception(cause));
            }
        } catch (Exception e) {
            if (isCancelled()) {
                mCallback.onCancel();
            } else {
                mCallback.onException(e);
            }
        }
        mCallback.onEnd();
    }

    @Override
    public void cancel() {
        cancel(true);
        mWorker.cancel();
    }
}