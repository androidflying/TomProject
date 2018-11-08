package com.tom.common.base;

import android.os.Bundle;

import com.tom.baselib.BaseActivity;
import com.tom.common.util.DialogUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 描述：公共的基础Activity
 */
public abstract class CommonActivity extends BaseActivity {

    @Override
    public boolean isTransparent() {
        return true;
    }


    @Override
    protected boolean isNeedAdapt() {
        return true;
    }

    @Override
    public int setAdaptVerticalScreen() {
        return 360;
    }

    @Override
    public int setAdaptHorizontalScreen() {
        return 480;
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


    public void showLoadingDialog() {
        DialogUtil.showLoading();
    }

    public void showLoadingDialog(String message) {
        DialogUtil.showLoading(message);
    }

    public void missLoadingDialog() {
        DialogUtil.missLoading();
    }
}
