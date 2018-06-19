package com.tom.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tom.baselib.BaseLazyFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 描述：公共的基础Fragment
 */
public abstract class CommonFragment extends BaseLazyFragment {

    @Override
    public boolean isTransparent() {
        return false;
    }


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
}
