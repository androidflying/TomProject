package com.tom.scanner.core;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Build;

import java.lang.ref.WeakReference;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/2
 * 描述：
 */
class ProcessDataTask extends AsyncTask<Void, Void, ScanResult> {
    private Camera mCamera;
    private byte[] mData;
    private boolean mIsPortrait;
    private String mPicturePath;
    private Bitmap mBitmap;
    private WeakReference<QRCodeView> mQRCodeViewRef;
    private static long sLastStartTime = 0;

    ProcessDataTask(Camera camera, byte[] data, QRCodeView qrCodeView, boolean isPortrait) {
        mCamera = camera;
        mData = data;
        mQRCodeViewRef = new WeakReference<>(qrCodeView);
        mIsPortrait = isPortrait;
    }

    ProcessDataTask(String picturePath, QRCodeView qrCodeView) {
        mPicturePath = picturePath;
        mQRCodeViewRef = new WeakReference<>(qrCodeView);
    }

    ProcessDataTask(Bitmap bitmap, QRCodeView qrCodeView) {
        mBitmap = bitmap;
        mQRCodeViewRef = new WeakReference<>(qrCodeView);
    }

    ProcessDataTask perform() {
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return this;
    }

    void cancelTask() {
        if (getStatus() != Status.FINISHED) {
            cancel(true);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        mQRCodeViewRef.clear();
        mBitmap = null;
        mData = null;
    }

    private ScanResult processData(QRCodeView qrCodeView) {
        if (mData == null) {
            return null;
        }

        int width = 0;
        int height = 0;
        byte[] data = mData;
        try {
            Camera.Parameters parameters = mCamera.getParameters();
            Camera.Size size = parameters.getPreviewSize();
            width = size.width;
            height = size.height;

            if (mIsPortrait) {
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

            return qrCodeView.processData(data, width, height, false);
        } catch (Exception e1) {
            e1.printStackTrace();
            try {
                if (width != 0 && height != 0) {
                    return qrCodeView.processData(data, width, height, true);
                } else {
                    return null;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
    }

    @Override
    protected ScanResult doInBackground(Void... params) {
        QRCodeView qrCodeView = mQRCodeViewRef.get();
        if (qrCodeView == null) {
            return null;
        }

        if (mPicturePath != null) {
            return qrCodeView.processBitmapData(QRCodeUtil.getDecodeAbleBitmap(mPicturePath));
        } else if (mBitmap != null) {
            ScanResult result = qrCodeView.processBitmapData(mBitmap);
            mBitmap = null;
            return result;
        } else {
            long startTime = System.currentTimeMillis();

            ScanResult scanResult = processData(qrCodeView);

            return scanResult;
        }
    }

    @Override
    protected void onPostExecute(ScanResult result) {
        QRCodeView qrCodeView = mQRCodeViewRef.get();
        if (qrCodeView == null) {
            return;
        }

        if (mPicturePath != null || mBitmap != null) {
            mBitmap = null;
            qrCodeView.onPostParseBitmapOrPicture(result);
        } else {
            qrCodeView.onPostParseData(result);
        }
    }
}
