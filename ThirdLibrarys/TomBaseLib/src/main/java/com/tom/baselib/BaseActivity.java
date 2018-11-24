package com.tom.baselib;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.tom.baselib.utils.AdaptScreenUtils;
import com.tom.baselib.utils.BarUtils;
import com.tom.baselib.utils.ScreenUtils;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/4/18
 * 描述：Activity基类
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    protected View mContentView;
    protected AppCompatActivity mActivity;
    private int fastTime = 200;
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
        //默认强制竖屏
        ScreenUtils.setPortrait(this);
        if (isNeedAdapt()) {
            //今日头条屏幕适配方案
            if (ScreenUtils.isPortrait()) {
                //以宽度为基准，竖屏
                AdaptScreenUtils.adaptWidth(getResources(), setAdaptVerticalScreen());
            } else {
                //以高度为基准，横屏
                AdaptScreenUtils.adaptHeight(getResources(), setAdaptHorizontalScreen());
            }
        }
        if (BarUtils.isNavBarVisible(this)) {
            BarUtils.setNavBarVisibility(this, !isTransparent());
        }
        if (BarUtils.isSupportNavBar()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                BarUtils.setNavBarColor(this, getResources().getColor(android.R.color.transparent));
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

    /**
     * 是否进行屏幕适配
     *
     * @return true：进行；false：不进行
     */
    protected abstract boolean isNeedAdapt();

    /**
     * 设置竖屏时设计图的宽度（dp）
     *
     * @return
     */
    protected abstract int setAdaptVerticalScreen();

    /**
     * 设置横屏时设计图的宽度（dp）
     *
     * @return
     */
    protected abstract int setAdaptHorizontalScreen();

    protected void setBaseView(@LayoutRes int layoutId) {
        setContentView(mContentView = LayoutInflater.from(getBaseContext()).inflate(layoutId, null));
    }

    @Override
    protected void onDestroy() {
        if (!isNeedAdapt()) {
            AdaptScreenUtils.closeAdapt(getResources());
        }
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
        if (now - lastClick >= fastTime) {
            lastClick = now;
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        //强制字体大小不随着系统设置改变而改变
        Resources resource = mActivity.getResources();
        Configuration configuration = resource.getConfiguration();
        // 设置字体的缩放比例
        configuration.fontScale = 1.0f;
        resource.updateConfiguration(configuration, resource.getDisplayMetrics());
        super.onResume();
    }
}
