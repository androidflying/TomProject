package com.hub.welfare.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.View;

import com.tom.baselib.BaseLazyFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/1
 * 描述：
 */
public abstract class MyFragment extends BaseLazyFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNeedRegister()) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public void onWidgetClick(View view) {

    }
}
