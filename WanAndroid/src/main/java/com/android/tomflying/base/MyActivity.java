package com.android.tomflying.base;

import android.os.Bundle;

import com.qmuiteam.tom.widget.dialog.QMUITipDialog;
import com.tom.baselib.BaseActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/7
 * 描述：
 */
public abstract class MyActivity extends BaseActivity {

    private QMUITipDialog tipDialog;

    @Override
    public boolean isTransparent() {
        return false;
    }


    @Override
    public boolean isNeedAdapt() {
        return true;
    }

    @Override
    public int setAdaptHorizontalScreen() {
        return 360;
    }


    @Override
    public int setAdaptVerticalScreen() {
        return 360;
    }

    public void showLoadingDialog() {
        if (tipDialog == null) {
            tipDialog = new QMUITipDialog.Builder(mActivity)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("正在加载")
                    .create();
        }
        tipDialog.show();
    }

    public void showLoadingDialog(String message) {
        if (tipDialog == null) {
            tipDialog = new QMUITipDialog.Builder(mActivity)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord(message)
                    .create();
        }
        tipDialog.show();
    }

    public void missLoadingDialog() {
        if (tipDialog != null && tipDialog.isShowing()) {
            tipDialog.dismiss();
        }
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
