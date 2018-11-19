package com.tom.soft.base;

import android.os.Bundle;
import android.view.View;

import com.qmuiteam.tom.widget.dialog.QMUITipDialog;
import com.tom.baselib.BaseLazyFragment;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 描述：基础Fragment
 */
public abstract class MyFragment extends BaseLazyFragment {
    private QMUITipDialog tipDialog;

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
}
