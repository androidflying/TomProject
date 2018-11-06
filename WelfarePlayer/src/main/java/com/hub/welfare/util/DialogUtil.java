package com.hub.welfare.util;

import android.content.DialogInterface;

import com.tom.baselib.utils.ActivityUtils;

import androidx.appcompat.app.AlertDialog;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/5
 * 描述：
 */
public class DialogUtil {
    public static void showSingleChoose(String[] items, int checked, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityUtils.getTopActivity());
        builder.setSingleChoiceItems(items, checked, listener);


        builder.create().show();
    }
}
