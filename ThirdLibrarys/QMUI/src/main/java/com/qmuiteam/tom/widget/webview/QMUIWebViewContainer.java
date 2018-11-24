package com.qmuiteam.tom.widget.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;

import com.qmuiteam.tom.util.QMUINotchHelper;
import com.qmuiteam.tom.widget.QMUIWindowInsetLayout;

import androidx.annotation.NonNull;
import androidx.core.view.WindowInsetsCompat;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/21
 * 描述：
 */
public class QMUIWebViewContainer extends QMUIWindowInsetLayout {
    private QMUIWebView mWebView;
    private QMUIWebView.OnScrollChangeListener mOnScrollChangeListener;

    public QMUIWebViewContainer(Context context) {
        super(context);
    }

    public QMUIWebViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void addWebView(@NonNull QMUIWebView webView, boolean needDispatchSafeAreaInset) {
        mWebView = webView;
        mWebView.setNeedDispatchSafeAreaInset(needDispatchSafeAreaInset);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mWebView.setOnScrollChangeListener(new OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (mOnScrollChangeListener != null) {
                        mOnScrollChangeListener.onScrollChange(v, scrollX, scrollY, oldScrollX, oldScrollY);
                    }
                }
            });
        } else {
            mWebView.setCustomOnScrollChangeListener(new QMUIWebView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                }
            });
        }
        addView(mWebView, getWebViewLayoutParams());
    }

    protected FrameLayout.LayoutParams getWebViewLayoutParams() {
        return new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    public void setNeedDispatchSafeAreaInset(boolean needDispatchSafeAreaInset) {
        if (mWebView != null) {
            mWebView.setNeedDispatchSafeAreaInset(needDispatchSafeAreaInset);
        }
    }

    public void destroy() {
        removeView(mWebView);
        removeAllViews();
        mWebView.setWebChromeClient(null);
        mWebView.setWebViewClient(null);
        mWebView.destroy();
    }


    public void setCustomOnScrollChangeListener(QMUIWebView.OnScrollChangeListener onScrollChangeListener) {
        mOnScrollChangeListener = onScrollChangeListener;
    }

    @Override
    @TargetApi(19)
    public boolean applySystemWindowInsets19(Rect insets) {
        if (getFitsSystemWindows()) {
            Rect childInsets = new Rect(insets);
            mQMUIWindowInsetHelper.computeInsetsWithGravity(this, childInsets);
            setPadding(childInsets.left, childInsets.top, childInsets.right, childInsets.bottom);
            return true;
        }
        return super.applySystemWindowInsets19(insets);
    }

    @Override
    @TargetApi(21)
    public boolean applySystemWindowInsets21(Object insets) {
        if (getFitsSystemWindows()) {
            int insetLeft = 0, insetRight = 0, insetTop = 0, insetBottom = 0;
            if (insets instanceof WindowInsetsCompat) {
                WindowInsetsCompat windowInsetsCompat = (WindowInsetsCompat) insets;
                insetLeft = windowInsetsCompat.getSystemWindowInsetLeft();
                insetRight = windowInsetsCompat.getSystemWindowInsetRight();
                insetTop = windowInsetsCompat.getSystemWindowInsetTop();
                insetBottom = windowInsetsCompat.getSystemWindowInsetBottom();
            } else if (insets instanceof WindowInsets) {
                WindowInsets windowInsets = (WindowInsets) insets;
                insetLeft = windowInsets.getSystemWindowInsetLeft();
                insetRight = windowInsets.getSystemWindowInsetRight();
                insetTop = windowInsets.getSystemWindowInsetTop();
                insetBottom = windowInsets.getSystemWindowInsetBottom();
            }

            if (QMUINotchHelper.needFixLandscapeNotchAreaFitSystemWindow(this) &&
                    getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                insetLeft = Math.max(insetLeft, QMUINotchHelper.getSafeInsetLeft(this));
                insetRight = Math.max(insetRight, QMUINotchHelper.getSafeInsetRight(this));
            }

            Rect childInsets = new Rect(insetLeft, insetTop, insetRight, insetBottom);
            mQMUIWindowInsetHelper.computeInsetsWithGravity(this, childInsets);
            setPadding(childInsets.left, childInsets.top, childInsets.right, childInsets.bottom);
            return true;
        }

        return super.applySystemWindowInsets21(insets);
    }
}
