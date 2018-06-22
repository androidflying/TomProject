package com.tom.common.base;

import android.os.Bundle;

import com.qmuiteam.tom.widget.dialog.QMUITipDialog;
import com.tom.baselib.BaseActivity;
import com.tom.baselib.utils.ToastUtils;
import com.tom.common.util.ShareUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isNeedRegister()) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }
        //统计应用启动数据
        PushAgent.getInstance(this).onAppStart();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        //Session启动、App使用时长等基础数据统计
        MobclickAgent.onPause(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Session启动、App使用时长等基础数据统计
        MobclickAgent.onResume(this);
    }


    public void showLoadingDialog() {
    }

    public void showLoadingDialog(String message) {
    }

    public void missLoadingDialog() {

    }
}
