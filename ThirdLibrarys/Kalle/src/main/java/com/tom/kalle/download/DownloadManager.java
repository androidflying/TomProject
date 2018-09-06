package com.tom.kalle.download;

import com.tom.kalle.http.CancelerManager;
import com.tom.kalle.http.Canceller;
import com.tom.kalle.Kalle;

import java.util.concurrent.Executor;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public class DownloadManager {

    private static DownloadManager sInstance;

    public static DownloadManager getInstance() {
        if (sInstance == null) {
            synchronized (DownloadManager.class) {
                if (sInstance == null) {
                    sInstance = new DownloadManager();
                }
            }
        }
        return sInstance;
    }

    private final Executor mExecutor;
    private final CancelerManager mCancelManager;

    private DownloadManager() {
        this.mExecutor = Kalle.getConfig().getWorkExecutor();
        this.mCancelManager = new CancelerManager();
    }

    /**
     * Submit a request to the queue.
     *
     * @param download download request.
     * @param callback accept the result callback.
     * @return this request corresponds to the task cancel handle.
     */
    public Canceller perform(final UrlDownload download, Callback callback) {
        final Work<UrlDownload> work = new Work<>(new UrlWorker(download), new AsyncCallback(callback) {
            @Override
            public void onEnd() {
                super.onEnd();
                mCancelManager.removeCancel(download);
            }
        });
        mCancelManager.addCancel(download, work);
        mExecutor.execute(work);
        return work;
    }

    /**
     * Execute a request.
     *
     * @param download download request.
     * @return download the completed file path.
     */
    public String perform(UrlDownload download) throws Exception {
        return new UrlWorker(download).call();
    }

    /**
     * Submit a request to the queue.
     *
     * @param download download request.
     * @param callback accept the result callback.
     * @return this request corresponds to the task cancel handle.
     */
    public Canceller perform(final BodyDownload download, Callback callback) {
        final Work<BodyDownload> work = new Work<>(new BodyWorker(download), new AsyncCallback(callback) {
            @Override
            public void onEnd() {
                super.onEnd();
                mCancelManager.removeCancel(download);
            }
        });
        mCancelManager.addCancel(download, work);
        mExecutor.execute(work);
        return work;
    }

    /**
     * Execute a request.
     *
     * @param download download request.
     * @return download the completed file path.
     */
    public String perform(BodyDownload download) throws Exception {
        return new BodyWorker(download).call();
    }

    /**
     * Cancel multiple requests based on tag.
     *
     * @param tag Specified tag.
     */
    public void cancel(Object tag) {
        mCancelManager.cancel(tag);
    }

    private static class AsyncCallback implements Callback {

        private final Callback mCallback;
        private final Executor mExecutor;

        AsyncCallback(Callback callback) {
            this.mCallback = callback;
            this.mExecutor = Kalle.getConfig().getMainExecutor();
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
        public void onFinish(final String path) {
            if (mCallback == null) {
                return;
            }
            mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    mCallback.onFinish(path);
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