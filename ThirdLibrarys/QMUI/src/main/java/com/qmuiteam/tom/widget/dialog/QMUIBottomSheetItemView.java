package com.qmuiteam.tom.widget.dialog;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.ViewStub;
import android.widget.TextView;

import com.qmuiteam.tom.R;
import com.qmuiteam.tom.alpha.QMUIAlphaLinearLayout;

/**
 * QMUIBottomSheet 的ItemView
 *
 * @author zander
 * @date 2017-12-05
 */
public class QMUIBottomSheetItemView extends QMUIAlphaLinearLayout {

    private AppCompatImageView mAppCompatImageView;
    private ViewStub mSubScript;
    private TextView mTextView;


    public QMUIBottomSheetItemView(Context context) {
        super(context);
    }

    public QMUIBottomSheetItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QMUIBottomSheetItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAppCompatImageView = findViewById(R.id.grid_item_image);
        mSubScript = findViewById(R.id.grid_item_subscript);
        mTextView = findViewById(R.id.grid_item_title);
    }

    public AppCompatImageView getAppCompatImageView() {
        return mAppCompatImageView;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public ViewStub getSubScript() {
        return mSubScript;
    }
}
