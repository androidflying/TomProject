package com.qmuiteam.tom.widget.popup;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.qmuiteam.tom.layout.QMUIFrameLayout;
import com.qmuiteam.tom.util.QMUIDisplayHelper;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

/**
 * 修改自 @author Lorensius W. L. T <lorenz@londatiga.net>
 */
public abstract class QMUIBasePopup {
    private static final String TAG = "QMUIBasePopup";
    protected Context mContext;
    protected PopupWindow mWindow;
    protected View mRootView;
    protected Drawable mBackground = null;
    protected WindowManager mWindowManager;
    protected Point mScreenSize = new Point();
    protected int mWindowHeight = 0;
    protected int mWindowWidth = 0;
    private RootView mRootViewWrapper;
    private PopupWindow.OnDismissListener mDismissListener;
    private View mParentViewForShow;

    /**
     * Constructor.
     *
     * @param context Context
     */
    public QMUIBasePopup(Context context) {
        mContext = context;
        mWindow = new PopupWindow(context);
        mWindow.setTouchInterceptor(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    mWindow.dismiss();
                    return false;
                }
                return false;
            }
        });

        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

    }

    public void dimBehind(float dim) {
        if (!isShowing()) {
            throw new RuntimeException("should call after method show() or in onShowEnd()");
        }
        View decorView = getDecorView();
        if (decorView != null) {
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) decorView.getLayoutParams();
            p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = dim;
            mWindowManager.updateViewLayout(decorView, p);
        }
    }

    public boolean isShowing() {
        return mWindow != null && mWindow.isShowing();
    }

    public View getDecorView() {
        View decorView = null;
        try {
            if (mWindow.getBackground() == null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    decorView = (View) mWindow.getContentView().getParent();
                } else {
                    decorView = mWindow.getContentView();
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    decorView = (View) mWindow.getContentView().getParent().getParent();
                } else {
                    decorView = (View) mWindow.getContentView().getParent();
                }
            }
        } catch (Exception ignore) {

        }

        return decorView;
    }

    public final void show(@NonNull View view) {
        show(view, view);
    }


    public final void show(@NonNull View parent, @NonNull View anchorView) {
        if (!ViewCompat.isAttachedToWindow(anchorView)) {
            return;
        }
        onShowConfig();
        if (mWindowWidth == 0 || mWindowHeight == 0 || mRootViewWrapper.isLayoutRequested() || shouldForceReMeasure()) {
            measureWindowSize();
        }
        mWindow.setWidth(mWindowWidth);
        mWindow.setHeight(mWindowHeight);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            mWindow.setAttachedInDecor(false);
        }

        Point point = onShowBegin(parent, anchorView);

        mWindow.showAtLocation(parent, Gravity.NO_GRAVITY, point.x, point.y);
        mParentViewForShow = parent;
        onShowEnd();

        // 在相关的View被移除时，window也自动移除。避免当Fragment退出后，Fragment中弹出的PopupWindow还存在于界面上。
        parent.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });
    }

    public View getParentViewForShow() {
        return mParentViewForShow;
    }

    protected void onShowConfig() {
        if (mRootViewWrapper == null) {
            throw new IllegalStateException("setContentView was not called with a view to display.");
        }
        if (mBackground == null) {
            mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } else {
            mWindow.setBackgroundDrawable(mBackground);
        }

        mWindow.setTouchable(true);
        mWindow.setFocusable(true);
        mWindow.setOutsideTouchable(true);

        mWindow.setContentView(mRootViewWrapper);

        Display screenDisplay = mWindowManager.getDefaultDisplay();
        screenDisplay.getSize(mScreenSize);
    }

    protected void measureWindowSize() {
        int widthMeasureSpec = makeWidthMeasureSpec(mRootViewWrapper);
        int heightMeasureSpec = makeHeightMeasureSpec(mRootViewWrapper);
        mRootView.measure(widthMeasureSpec, heightMeasureSpec);
        mWindowWidth = mRootView.getMeasuredWidth();
        mWindowHeight = mRootView.getMeasuredHeight();
        Log.i(TAG, "measureWindowSize: mWindowWidth = " + mWindowWidth + " ;mWindowHeight = " + mWindowHeight);
    }

    protected abstract Point onShowBegin(@NonNull View parent, @NonNull View attachedView);

    protected void onShowEnd() {
    }

    public void dismiss() {
        mWindow.dismiss();
    }

    protected int makeWidthMeasureSpec(View view) {
        return View.MeasureSpec.makeMeasureSpec(QMUIDisplayHelper.getScreenWidth(mContext), View.MeasureSpec.AT_MOST);
    }

    protected int makeHeightMeasureSpec(View view) {
        return View.MeasureSpec.makeMeasureSpec(QMUIDisplayHelper.getScreenHeight(mContext), View.MeasureSpec.AT_MOST);
    }

    protected boolean shouldForceReMeasure() {
        return false;
    }

    /**
     * Set background drawable.
     *
     * @param background Background drawable
     */
    public void setBackgroundDrawable(Drawable background) {
        mBackground = background;
    }

    protected abstract void onWindowSizeChange();

    /**
     * Set content view.
     *
     * @param layoutResID Resource id
     */
    public void setContentView(int layoutResID) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setContentView(inflater.inflate(layoutResID, null));
    }

    /**
     * Set content view.
     *
     * @param root Root view
     */
    public void setContentView(View root) {
        if (root == null) {
            throw new IllegalStateException("setContentView was not called with a view to display.");
        }
        mRootViewWrapper = new RootView(mContext);
        mRootViewWrapper.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mRootView = root;
        mRootViewWrapper.addView(root);
        mWindow.setContentView(mRootViewWrapper);
        mWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                QMUIBasePopup.this.onDismiss();
                if (mDismissListener != null) {
                    mDismissListener.onDismiss();
                }
            }
        });
    }

    /**
     * On dismiss
     */
    protected void onDismiss() {
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        mDismissListener = listener;
    }

    protected void onConfigurationChanged(Configuration newConfig) {

    }


    public class RootView extends QMUIFrameLayout {
        public RootView(Context context) {
            this(context, null);
        }

        public RootView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onConfigurationChanged(Configuration newConfig) {
            if (mWindow != null && mWindow.isShowing()) {
                mWindow.dismiss();
            }
            QMUIBasePopup.this.onConfigurationChanged(newConfig);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            if (getChildCount() == 0) {
                setMeasuredDimension(0, 0);
            }
            int parentWidthSize = MeasureSpec.getSize(widthMeasureSpec);
            int parentHeightSize = MeasureSpec.getSize(heightMeasureSpec);
            widthMeasureSpec = makeWidthMeasureSpec(this);
            heightMeasureSpec = makeHeightMeasureSpec(this);
            int targetWidthSize = MeasureSpec.getSize(widthMeasureSpec);
            int targetWidthMode = MeasureSpec.getMode(widthMeasureSpec);
            int targetHeightSize = MeasureSpec.getSize(heightMeasureSpec);
            int targetHeightMode = MeasureSpec.getMode(heightMeasureSpec);
            if (parentWidthSize < targetWidthSize) {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(parentWidthSize, targetWidthMode);
            }
            if (parentHeightSize < targetHeightSize) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(parentHeightSize, targetHeightMode);
            }
            View child = getChildAt(0);
            child.measure(widthMeasureSpec, heightMeasureSpec);
            int oldWidth = mWindowWidth, oldHeight = mWindowHeight;
            mWindowWidth = child.getMeasuredWidth();
            mWindowHeight = child.getMeasuredHeight();
            if (oldWidth != mWindowWidth || oldHeight != mWindowHeight && mWindow.isShowing()) {
                onWindowSizeChange();
            }
            Log.i(TAG, "in measure: mWindowWidth = " + mWindowWidth + " ;mWindowHeight = " + mWindowHeight);
            setMeasuredDimension(mWindowWidth, mWindowHeight);
        }

        @Override
        public void addView(View child) {
            if (getChildCount() > 0) {
                throw new RuntimeException("only support one child");
            }
            super.addView(child);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            if (getChildCount() == 0) {
                return;
            }
            View child = getChildAt(0);
            child.layout(getPaddingLeft(), getPaddingTop(), child.getMeasuredWidth(), child.getMeasuredHeight());
        }
    }
}