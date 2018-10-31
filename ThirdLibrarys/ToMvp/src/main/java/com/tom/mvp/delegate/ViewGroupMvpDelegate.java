package com.tom.mvp.delegate;

import android.os.Parcelable;
import android.view.View;

import com.tom.mvp.common.MvpPresenter;
import com.tom.mvp.common.MvpView;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/10/31
 * 描述：
 */
public interface ViewGroupMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {

    /**
     * Must be called from {@link View#onAttachedToWindow()}
     */
    void onAttachedToWindow();

    /**
     * Must be called from {@link View#onDetachedFromWindow()}
     */
    void onDetachedFromWindow();

    /**
     * Must be called from {@link View#onRestoreInstanceState(Parcelable)}
     *
     * @param state The parcelable state
     */
    void onRestoreInstanceState(Parcelable state);

    /**
     * Save the instatnce state
     *
     * @return The state with all the saved data
     */
    Parcelable onSaveInstanceState();
}
