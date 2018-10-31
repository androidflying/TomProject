package com.tom.mvp.delegate;

import android.content.Context;
import android.os.Parcelable;

import com.tom.mvp.common.MvpPresenter;
import com.tom.mvp.common.MvpView;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/10/31
 * 描述：
 */
public interface ViewGroupDelegateCallback<V extends MvpView, P extends MvpPresenter<V>>
        extends MvpDelegateCallback<V, P> {

    /**
     * This method must call super.onSaveInstanceState() within any view
     */
    Parcelable superOnSaveInstanceState();

    /**
     * This method must call super.onRestoreInstanceState(state)
     *
     * @param state The parcelable containing the state
     */
    void superOnRestoreInstanceState(Parcelable state);

    /**
     * Get the context
     *
     * @return Get the context
     * @since 3.0
     */
    Context getContext();
}
