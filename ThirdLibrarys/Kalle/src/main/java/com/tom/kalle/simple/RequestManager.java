package com.tom.kalle.simple;

import com.tom.kalle.Kalle;
import com.tom.kalle.http.CancelerManager;
import com.tom.kalle.http.Canceller;

import java.lang.reflect.Type;
import java.util.concurrent.Executor;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class RequestManager {

    private static RequestManager sInstance;

    public static RequestManager getInstance() {
        if (sInstance == null) {
            synchronized (RequestManager.class) {
                if (sInstance == null) {
                    sInstance = new RequestManager();
                }
            }
        }
        return sInstance;
    }

    private final Executor mExecutor;
    private final CancelerManager mCancelManager;

    private RequestManager() {
        this.mExecutor = Kalle.getConfig().getWorkExecutor();
        this.mCancelManager = new CancelerManager();
    }

    /**
     * Submit a request to the queue.
     *
     * @param request  request.
     * @param callback accept the result callback.
     * @param <S>      target object parameter.
     * @param <F>      target object parameter.
     * @return this request corresponds to the task cancel handle.
     */
    public <S, F> Canceller perform(final SimpleUrlRequest request, Callback<S, F> callback) {
        final Work<SimpleUrlRequest, S, F> work = new Work<>(new UrlWorker<S, F>(request, callback.getSucceed(), callback.getFailed()), new AsyncCallback<S, F>(callback) {
            @Override
            public void onEnd() {
                super.onEnd();
                mCancelManager.removeCancel(request);
            }
        });
        mCancelManager.addCancel(request, work);
        mExecutor.execute(work);
        return work;
    }

    /**
     * Execute a request.
     *
     * @param request request.
     * @param succeed the data type when the business succeed.
     * @param failed  the data type when the business failed.
     * @param <S>     target object parameter.
     * @param <F>     target object parameter.
     * @return the response to this request.
     */
    public <S, F> SimpleResponse<S, F> perform(SimpleUrlRequest request, Type succeed, Type failed) throws Exception {
        return new UrlWorker<S, F>(request, succeed, failed).call();
    }

    /**
     * Submit a request to the queue.
     *
     * @param request  request.
     * @param callback accept the result callback.
     * @param <S>      target object parameter.
     * @param <F>      target object parameter.
     * @return this request corresponds to the task cancel handle.
     */
    public <S, F> Canceller perform(final SimpleBodyRequest request, Callback<S, F> callback) {
        Work<SimpleBodyRequest, S, F> work = new Work<>(new BodyWorker<S, F>(request, callback.getSucceed(), callback.getFailed()), new AsyncCallback<S, F>(callback) {
            @Override
            public void onEnd() {
                super.onEnd();
                mCancelManager.removeCancel(request);
            }
        });
        mCancelManager.addCancel(request, work);
        mExecutor.execute(work);
        return work;
    }

    /**
     * Execute a request.
     *
     * @param request request.
     * @param succeed the data type when the business succeed.
     * @param failed  the data type when the business failed.
     * @param <S>     target object parameter.
     * @param <F>     target object parameter.
     * @return the response to this request.
     */
    public <S, F> SimpleResponse<S, F> perform(SimpleBodyRequest request, Type succeed, Type failed) throws Exception {
        return new BodyWorker<S, F>(request, succeed, failed).call();
    }

    /**
     * Cancel multiple requests based on tag.
     *
     * @param tag specified tag.
     */
    public void cancel(Object tag) {
        mCancelManager.cancel(tag);
    }

    private static class AsyncCallback<S, F> extends Callback<S, F> {

        private final Callback<S, F> mCallback;
        private final Executor mExecutor;

        AsyncCallback(Callback<S, F> callback) {
            this.mCallback = callback;
            this.mExecutor = Kalle.getConfig().getMainExecutor();
        }

        @Override
        public Type getSucceed() {
            return mCallback.getSucceed();
        }

        @Override
        public Type getFailed() {
            return mCallback.getFailed();
        }

        @Override
        public void onStart() {
            if (mCallback == null) {
                return;
            }
            mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    mCallback.onStart();
                }
            });
        }

        @Override
        public void onResponse(final SimpleResponse<S, F> response) {
            if (mCallback == null) {
                return;
            }
            mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    mCallback.onResponse(response);
                }
            });
        }

        @Override
        public void onException(final Exception e) {
            if (mCallback == null) {
                return;
            }
            mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    mCallback.onException(e);
                }
            });
        }

        @Override
        public void onCancel() {
            if (mCallback == null) {
                return;
            }
            mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    mCallback.onCancel();
                }
            });
        }

        @Override
        public void onEnd() {
            if (mCallback == null) {
                return;
            }
            mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    mCallback.onEnd();
                }
            });
        }
    }
}