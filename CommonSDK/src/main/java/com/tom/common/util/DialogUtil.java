package com.tom.common.util;

import com.qmuiteam.tom.widget.dialog.QMUIDialog;
import com.qmuiteam.tom.widget.dialog.QMUIDialogAction;
import com.qmuiteam.tom.widget.dialog.QMUITipDialog;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.baselib.utils.PermissionUtils;
import com.tom.baselib.utils.PermissionUtils.OnRationaleListener.ShouldRequest;
import com.tom.baselib.utils.Utils;
import com.tom.common.R;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/7/4
 * 描述：弹窗工具类
 */
public class DialogUtil {
    private static QMUITipDialog tipDialog;

    public static void showLoading() {
        if (tipDialog == null) {
            tipDialog = new QMUITipDialog.Builder(ActivityUtils.getTopActivity())
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("正在加载")
                    .create();
        }
        tipDialog.show();
    }

    public static void showLoading(String msg) {
        if (tipDialog == null) {
            tipDialog = new QMUITipDialog.Builder(ActivityUtils.getTopActivity())
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord(msg)
                    .create();
        }
        tipDialog.show();
    }


    public static void missLoading() {
        if (tipDialog != null && tipDialog.isShowing()) {
            tipDialog.dismiss();
        }
    }


    public static void showOpenAppSettingDialog() {
        new QMUIDialog.MessageDialogBuilder(Utils.getApp())
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_denied_forever_message)
                .addAction(android.R.string.ok, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        PermissionUtils.launchAppDetailsSettings();
                    }
                })
                .addAction(0, android.R.string.cancel, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    public static void showRationaleDialog(final ShouldRequest shouldRequest) {
        new QMUIDialog.MessageDialogBuilder(Utils.getApp())
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_rationale_message)
                .addAction(android.R.string.ok, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        shouldRequest.again(true);
                    }
                })
                .addAction(android.R.string.cancel, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        shouldRequest.again(false);
                    }
                })
                .setCancelable(false)
                .create()
                .show();

    }

    //TODO 把常用的对话框封装到这里
}
