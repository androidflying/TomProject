package com.hub.welfare.base;

import android.os.Bundle;

import com.hub.welfare.ConstantValues;
import com.hub.welfare.util.ThemeUtils;
import com.tom.baselib.BaseActivity;
import com.tom.baselib.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/1
 * 描述：
 */
public abstract class MyActivity extends BaseActivity {

    @Override
    public boolean isTransparent() {
        return false;
    }


    @Override
    public boolean isNeedAdapt() {
        return false;
    }

    @Override
    public int setAdaptHorizontalScreen() {
        return 0;
    }


    @Override
    public int setAdaptVerticalScreen() {
        return 0;
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
