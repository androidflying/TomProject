package com.tom.im.base;

import android.os.Bundle;

import com.tom.baselib.BaseActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 描述：模块中基类Activity
 */
public abstract class MyActivity extends BaseActivity {

    @Override
    public boolean isTransparent() {
        return false;
    }

    public void showLoadingDialog() {
        //TODO
    }

    public void showLoadingDialog(String message) {
        //TODO
    }

    public void missLoadingDialog() {
        //TODO 
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isNeedRegister()) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }
}

