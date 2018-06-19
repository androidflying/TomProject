package com.tom.scanner.core;

import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Build;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/2
 * 描述：
 */
public class ProcessDataTask extends AsyncTask<Void, Void, String> {
    private Camera mCamera;
    private byte[] mData;
    private Delegate mDelegate;
    private int orientation;

    public ProcessDataTask(Camera camera, byte[] data, Delegate delegate, int orientation) {
        mCamera = camera;
        mData = data;
        mDelegate = delegate;
        this.orientation = orientation;
    }

    public ProcessDataTask perform() {
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return this;
    }

    public void cancelTask() {
        if (getStatus() != Status.FINISHED) {
            cancel(true);
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size size = parameters.getPreviewSize();
        int width = size.width;
        int height = size.height;

        byte[] data = mData;

        if (orientation == QRCodeUtil.ORIENTATION_PORTRAIT) {
            data = new byte[mData.length];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    data[x * height + height - y - 1] = mData[x + y * width];
                }
            }
            int tmp = width;
            width = height;
            height = tmp;
        }

        try {
            if (mDelegate == null) {
                return null;
            }
            return mDelegate.processData(data, width, height, false);
        } catch (Exception e1) {
            try {
                return mDelegate.processData(data, width, height, true);
            } catch (Exception e2) {
                return null;
            }
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        mDelegate = null;
    }

    public interface Delegate {
        String processData(byte[] data, int width, int height, boolean isRetry);
    }
}
