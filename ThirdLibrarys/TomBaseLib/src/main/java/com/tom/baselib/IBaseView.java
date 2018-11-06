package com.tom.baselib;

import android.os.Bundle;

import androidx.annotation.NonNull;
import android.view.View;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/4/18
 * 描述：视图操作接口
 */
public interface IBaseView extends View.OnClickListener {

    /**
     * 是否需要注册EventBus
     *
     * @return
     */
    boolean isNeedRegister();

    /**
     * 是否是全屏透明，包含顶部状态栏和底部导航栏
     *
     * @return
     */
    boolean isTransparent();

    /**
     * 初始化数据
     *
     * @param bundle 传递过来的 bundle
     */
    void initData(@NonNull final Bundle bundle);

    /**
     * 绑定布局
     *
     * @return 布局 Id
     */
    int bindLayout();


    /**
     * 初始化 view
     */
    void initView(final Bundle savedInstanceState, final View contentView);

    /**
     * 业务操作
     */
    void doBusiness();

    /**
     * 视图点击事件
     *
     * @param view 视图
     */
    void onWidgetClick(final View view);

}
