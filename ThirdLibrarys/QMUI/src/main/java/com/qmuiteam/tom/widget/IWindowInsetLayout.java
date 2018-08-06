package com.qmuiteam.tom.widget;

import android.graphics.Rect;
import android.support.v4.view.WindowInsetsCompat;
import android.view.WindowInsets;

/**
 * @author cginechen
 * @date 2017-09-13
 */

public interface IWindowInsetLayout {
    boolean applySystemWindowInsets19(Rect insets);

    boolean applySystemWindowInsets21(Object insets);
}
