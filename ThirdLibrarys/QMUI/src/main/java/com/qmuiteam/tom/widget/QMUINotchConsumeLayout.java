package com.qmuiteam.tom.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.qmuiteam.tom.util.QMUINotchHelper;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/7/18
 * 描述：
 */


public class QMUINotchConsumeLayout extends FrameLayout implements INotchInsetConsumer {
    public QMUINotchConsumeLayout(Context context) {
        this(context, null);
    }

    public QMUINotchConsumeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QMUINotchConsumeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFitsSystemWindows(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!QMUINotchHelper.isNotchOfficialSupport()) {
            notifyInsetMaybeChanged();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (!QMUINotchHelper.isNotchOfficialSupport()) {
            notifyInsetMaybeChanged();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean notifyInsetMaybeChanged() {
        setPadding(
                QMUINotchHelper.getSafeInsetLeft(this),
                QMUINotchHelper.getSafeInsetTop(this),
                QMUINotchHelper.getSafeInsetRight(this),
                QMUINotchHelper.getSafeInsetBottom(this)
        );
        return true;
    }
}
