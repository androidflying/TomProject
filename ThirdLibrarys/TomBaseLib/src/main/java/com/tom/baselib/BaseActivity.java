package com.tom.baselib;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.tom.baselib.utils.BarUtils;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/4/18
 * 描述：Activity基类
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    protected View mContentView;
    protected Activity mActivity;

    /**
     * 上次点击时间
     */
    private long lastClick = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        //默认实现沉浸式状态栏
        BarUtils.setStatusBarAlpha(this);

        if (BarUtils.isNavBarVisible(this)) {
            if (isTransparent()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    BarUtils.setNavBarImmersive(this);
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    BarUtils.setNavBarColor(this, getResources().getColor(android.R.color.transparent));
                }
            }
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            initData(bundle);
        }
        setBaseView(bindLayout());
        initView(savedInstanceState, mContentView);
        doBusiness();
    }

    protected void setBaseView(@LayoutRes int layoutId) {
        setContentView(mContentView = LayoutInflater.from(this).inflate(layoutId, null));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(final View view) {
        if (!isFastClick()) {
            onWidgetClick(view);
        }
    }

    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }


}
