package com.tom.scanner.zxing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.tom.scanner.core.BarcodeType;
import com.tom.scanner.core.QRCodeView;
import com.tom.scanner.core.ScanResult;

import java.util.Map;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/2
 * 描述：ZXing扫码器
 */
public class ZXingView extends QRCodeView {
    private MultiFormatReader mMultiFormatReader;
    private Map<DecodeHintType, Object> mHintMap;

    public ZXingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZXingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setupReader() {
        mMultiFormatReader = new MultiFormatReader();

        if (mBarcodeType == BarcodeType.ONE_DIMENSION) {
            mMultiFormatReader.setHints(QRCodeDecoder.ONE_DIMENSION_HINT_MAP);
        } else if (mBarcodeType == BarcodeType.TWO_DIMENSION) {
            mMultiFormatReader.setHints(QRCodeDecoder.TWO_DIMENSION_HINT_MAP);
        } else if (mBarcodeType == BarcodeType.ONLY_QR_CODE) {
            mMultiFormatReader.setHints(QRCodeDecoder.QR_CODE_HINT_MAP);
        } else if (mBarcodeType == BarcodeType.ONLY_CODE_128) {
            mMultiFormatReader.setHints(QRCodeDecoder.CODE_128_HINT_MAP);
        } else if (mBarcodeType == BarcodeType.ONLY_EAN_13) {
            mMultiFormatReader.setHints(QRCodeDecoder.EAN_13_HINT_MAP);
        } else if (mBarcodeType == BarcodeType.HIGH_FREQUENCY) {
            mMultiFormatReader.setHints(QRCodeDecoder.HIGH_FREQUENCY_HINT_MAP);
        } else if (mBarcodeType == BarcodeType.CUSTOM) {
            mMultiFormatReader.setHints(mHintMap);
        } else {
            mMultiFormatReader.setHints(QRCodeDecoder.ALL_HINT_MAP);
        }
    }

    /**
     * 设置识别的格式
     *
     * @param barcodeType 识别的格式
     * @param hintMap     barcodeType 为 BarcodeType.CUSTOM 时，必须指定该值
     */
    public void setType(BarcodeType barcodeType, Map<DecodeHintType, Object> hintMap) {
        mBarcodeType = barcodeType;
        mHintMap = hintMap;

        if (mBarcodeType == BarcodeType.CUSTOM && (mHintMap == null || mHintMap.isEmpty())) {
            throw new RuntimeException("barcodeType 为 BarcodeType.CUSTOM 时 hintMap 不能为空");
        }
        setupReader();
    }

    @Override
    protected ScanResult processBitmapData(Bitmap bitmap) {
        return new ScanResult(QRCodeDecoder.syncDecodeQRCode(bitmap));
    }

    @Override
    protected ScanResult processData(byte[] data, int width, int height, boolean isRetry) {
        String result = null;
        Result rawResult = null;
        Rect scanBoxAreaRect = null;

        try {
            PlanarYUVLuminanceSource source;
            scanBoxAreaRect = mScanBoxView.getScanBoxAreaRect(height);
            if (scanBoxAreaRect != null) {
                source = new PlanarYUVLuminanceSource(data, width, height, scanBoxAreaRect.left, scanBoxAreaRect.top, scanBoxAreaRect.width(),
                        scanBoxAreaRect.height(), false);
            } else {
                source = new PlanarYUVLuminanceSource(data, width, height, 0, 0, width, height, false);
            }

            rawResult = mMultiFormatReader.decodeWithState(new BinaryBitmap(new GlobalHistogramBinarizer(source)));
            if (rawResult == null) {
                rawResult = mMultiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(source)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mMultiFormatReader.reset();
        }

        if (rawResult != null) {
            result = rawResult.getText();

            if (isShowLocationPoint()) {
                ResultPoint[] resultPoints = rawResult.getResultPoints();
                final PointF[] pointArr = new PointF[resultPoints.length];
                int pointIndex = 0;
                for (ResultPoint resultPoint : resultPoints) {
                    pointArr[pointIndex] = new PointF(resultPoint.getX(), resultPoint.getY());
                    pointIndex++;
                }
                transformToViewCoordinates(pointArr, scanBoxAreaRect);
            }
        }
        return new ScanResult(result);
    }
}
