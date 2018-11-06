package com.tom.mrvah.helper;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import androidx.recyclerview.widget.DiffUtil;

import com.tom.mrvah.entity.HandleBase;
import com.tom.mrvah.entity.MultiTypeEntity;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/9/25
 * 描述：异步适配器，数据差异的计算在子线程完成，避免不必要的数据量过大计算而引起的ANR
 *      如果计算时间过长可考虑直接刷新全部数据
 *      刷新全部数据时注意，不可改变mData的引用，不然刷新无效
 */
public abstract class AsynAdapterHelper<T extends MultiTypeEntity> extends RecyclerViewAdapterHelper<T> {

    private static final int HANDLE_DATA_UPDATE = 1;

    protected ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();
    protected ScheduledFuture<?> mFuture;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HANDLE_DATA_UPDATE) {
                DiffUtil.DiffResult diffResult = (DiffUtil.DiffResult) msg.obj;
                handleResult(diffResult);
            }
        }
    };

    public AsynAdapterHelper(List<T> data) {
        super(data);
    }

    public AsynAdapterHelper(List<T> data, @RefreshMode int mode) {
        super(data, mode);
    }

    @Override
    protected void startRefresh(HandleBase<T> refreshData) {
        cancelFuture();
        mFuture = mExecutor.schedule(new HandleTask(refreshData),
                0, TimeUnit.MILLISECONDS);
    }

    @Override
    public void release() {
        cancelFuture();
        if (!mExecutor.isShutdown()) {
            mExecutor.shutdown();
            mExecutor = null;
        }
        super.release();
    }

    protected void cancelFuture() {
        if (mFuture != null && !mFuture.isCancelled()) {
            mFuture.cancel(true);
            mFuture = null;
        }
    }

    /**
     * 重置刷新队列
     */
    public void reset() {
        clearQueue();
        cancelFuture();
    }

    private final class HandleTask implements Runnable {

        private HandleBase<T> mHandleBase;

        HandleTask(HandleBase<T> handleBase) {
            mHandleBase = handleBase;
        }

        @Override
        public void run() {
            final List<T> newData = mHandleBase.getNewData();
            final T newHeader = mHandleBase.getNewHeader();
            final T newFooter = mHandleBase.getNewFooter();
            final int refreshType = mHandleBase.getRefreshType();
            final int level = mHandleBase.getLevel();
            DiffUtil.DiffResult result = handleRefresh(newData, newHeader, newFooter, level, refreshType);
            Message message = mHandler.obtainMessage(HANDLE_DATA_UPDATE);
            message.obj = result;
            message.sendToTarget();
        }
    }
}
