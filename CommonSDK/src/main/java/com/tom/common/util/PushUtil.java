package com.tom.common.util;

import com.tom.baselib.utils.Utils;
import com.umeng.message.IUmengCallback;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.tag.TagManager;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/4/24
 * 描述：第三方推送接口
 */
public class PushUtil {

    public static void register() {
        PushAgent.getInstance(Utils.getApp()).register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
    }


    /**
     * 设置免打扰时间段
     *
     * @param startHour
     * @param startMinute
     * @param endHour
     * @param endMinute
     */
    public static void setNoDisturbMode(int startHour, int startMinute, int endHour, int endMinute) {
        PushAgent.getInstance(Utils.getApp()).setNoDisturbMode(startHour, startMinute, endHour, endMinute);
    }

    /**
     * 关闭免打扰模式
     */
    public static void closeNoDisturbMode() {
        PushAgent.getInstance(Utils.getApp()).setNoDisturbMode(0, 0, 0, 0);
    }

    public static void setDisplayNotificationNumber(int number) {
        PushAgent.getInstance(Utils.getApp()).setDisplayNotificationNumber(number);
    }

    public static void addTags(String... tags) {
        PushAgent.getInstance(Utils.getApp()).getTagManager().addTags(new TagManager.TCallBack() {

            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {

            }

        }, tags);
    }


    public static void deleteTags(String... tags) {
        PushAgent.getInstance(Utils.getApp()).getTagManager().deleteTags(new TagManager.TCallBack() {

            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {

            }

        }, tags);
    }

    public static void addAlias(String alias, String type) {
        PushAgent.getInstance(Utils.getApp()).addAlias(alias, type, new UTrack.ICallBack() {

            @Override
            public void onMessage(boolean isSuccess, String message) {

            }

        });
    }

    public static void deleteAlias(String alias, String type) {
        PushAgent.getInstance(Utils.getApp()).deleteAlias(alias, type, new UTrack.ICallBack() {

            @Override
            public void onMessage(boolean isSuccess, String message) {

            }

        });
    }

    public static void openPush() {
        PushAgent.getInstance(Utils.getApp()).enable(new IUmengCallback() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String s, String s1) {

            }

        });
    }

    public static void closePush() {
        PushAgent.getInstance(Utils.getApp()).disable(new IUmengCallback() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String s, String s1) {

            }

        });
    }

}
