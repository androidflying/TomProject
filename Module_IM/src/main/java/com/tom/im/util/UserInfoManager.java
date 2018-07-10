package com.tom.im.util;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.tom.baselib.utils.LogUtils;
import com.tom.im.BuildConfig;

import java.util.LinkedHashMap;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/7/6
 * 描述：用户信息管理工具类
 */
public class UserInfoManager {

    /**
     * 用户信息全部未同步
     */
    private static final int NONE = 0;
    /**
     * 好友信息同步成功
     */
    private static final int FRIEND = 1;
    /**
     * 群组信息同步成功
     */
    private static final int GROUPS = 2;
    /**
     * 群成员信息部分同步成功,n个群组n次访问网络,存在部分同步的情况
     */
    private static final int PARTGROUPMEMBERS = 4;
    /**
     * 群成员信息同步成功
     */
    private static final int GROUPMEMBERS = 8;
    /**
     * 黑名单信息同步成功
     */
    private static final int BLACKLIST = 16;
    /**
     * 用户信息全部同步成功
     */
    private static final int ALL = 27;

    private static UserInfoManager sInstance;
    private final Context mContext;
    private Handler mWorkHandler;
    static Handler mHandler;

    private LinkedHashMap<String, UserInfo> mUserInfoCache;


    public UserInfoManager(Context context) {
        mContext = context;
    }

    public static UserInfoManager getInstance() {
        mHandler = new Handler(Looper.getMainLooper());
        return sInstance;
    }

    public static void init(Context context) {
        if (BuildConfig.DEBUG) {
            LogUtils.e("UserInfoManager init");
        }
        sInstance = new UserInfoManager(context);
    }


    /**
     * kit中提供用户信息的用户信息提供者
     * 1.读缓存
     * 2.读好友数据库
     * 3.读群组成员数据库
     * 4.网络获取
     */
    public void getUserInfo(final String userId) {
//        if (TextUtils.isEmpty(userId)) {
//            return;
//        }
//        //从缓存中获取用户信息
//        if (mUserInfoCache != null) {
//            UserInfo userInfo = mUserInfoCache.get(userId);
//            if (userInfo != null) {
//                RongIM.getInstance().refreshUserInfoCache(userInfo);
//                if (BuildConfig.DEBUG) {
//                    LogUtils.e("UserInfoManager getUserInfo from cache " + userId + " "
//                            + userInfo.getName() + " " + userInfo.getPortraitUri());
//                }
//                return;
//            }
//        }
//        mWorkHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                UserInfo userInfo;
//                //从好友数据库中读取用户信息
//                Friend friend = getFriendByID(userId);
//                if (friend != null) {
//                    String name = friend.getName();
//                    if (friend.isExitsDisplayName()) {
//                        name = friend.getDisplayName();
//                    }
//                    userInfo = new UserInfo(friend.getUserId(), name, friend.getPortraitUri());
//                    if (BuildConfig.DEBUG) {
//                        LogUtils.e("UserInfoManager getUserInfo from Friend db " + userId + " "
//                                + userInfo.getName() + " " + userInfo.getPortraitUri());
//                    }
//                    RongIM.getInstance().refreshUserInfoCache(userInfo);
//                    return;
//                }
//                //从群组成员数据库中读取用户信息
//                List<GroupMember> groupMemberList = getGroupMembersWithUserId(userId);
//                if (groupMemberList != null && groupMemberList.size() > 0) {
//                    GroupMember groupMember = groupMemberList.get(0);
//                    userInfo = new UserInfo(groupMember.getUserId(), groupMember.getName(),
//                            groupMember.getPortraitUri());
//                    if (BuildConfig.DEBUG) {
//                        LogUtils.e("UserInfoManager getUserInfo from GroupMember db " + userId + " "
//                                + userInfo.getName() + " " + userInfo.getPortraitUri());
//                    }
//                    RongIM.getInstance().refreshUserInfoCache(userInfo);
//                    return;
//                }
//                //从网络上获取用户信息
////                UserInfoEngine.getInstance(mContext).startEngine(userId);
//            }
//        });
    }

    public void getGroupInfo(final String groupsId) {
//        if (TextUtils.isEmpty(groupsId)) {
////            return;
////        }
////        mWorkHandler.post(new Runnable() {
////            @Override
////            public void run() {
////                Group groupInfo;
////                Groups group = getGroupsByID(groupsId);
////                if (group != null) {
////                    groupInfo = new Group(groupsId, group.getName(), Uri.parse(group.getPortraitUri()));
////                    RongIM.getInstance().refreshGroupInfoCache(groupInfo);
////                    if (BuildConfig.DEBUG) {
////                        LogUtils.e("SealUserInfoManager getGroupInfo from db " + groupsId + " "
////                                + groupInfo.getName() + " " + groupInfo.getPortraitUri());
////                    }
////                    return;
////                }
//////                GroupInfoEngine.getInstance(mContext).startEngine(groupsId);
////            }
////        });
    }


    /**
     * 泛型类，主要用于 API 中功能的回调处理。
     *
     * @param <T> 声明一个泛型 T。
     */
    public static abstract class ResultCallback<T> {

        public static class Result<T> {
            public T t;
        }

        public ResultCallback() {

        }

        /**
         * 成功时回调。
         *
         * @param t 已声明的类型。
         */
        public abstract void onSuccess(T t);

        /**
         * 错误时回调。
         *
         * @param errString 错误提示
         */
        public abstract void onError(String errString);


        public void onFail(final String errString) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onError(errString);
                }
            });
        }

        public void onCallback(final T t) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onSuccess(t);
                }
            });
        }
    }
}
