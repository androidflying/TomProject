package com.tom.mvp.delegate;

import android.support.annotation.NonNull;

import com.tom.mvp.common.MvpPresenter;
import com.tom.mvp.common.MvpView;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/10/31
 * 描述：
 */
public interface MvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>> {

    /**
     * Creates the presenter instance
     *
     * @return the created presenter instance
     */
    @NonNull
    P createPresenter();

    /**
     * Gets the presenter. If null is returned, then a internally a new presenter instance gets
     * created by calling {@link #createPresenter()}
     *
     * @return the presenter instance. can be null.
     */
    P getPresenter();

    /**
     * Sets the presenter instance
     *
     * @param presenter The presenter instance
     */
    void setPresenter(P presenter);

    /**
     * Gets the MvpView for the presenter
     *
     * @return The view associated with the presenter
     */
    V getMvpView();
}
