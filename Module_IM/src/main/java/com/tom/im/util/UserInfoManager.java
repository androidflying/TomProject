package com.tom.im.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.tom.baselib.utils.SPUtils;
import com.tom.im.BuildConfig;

import java.util.List;

import io.rong.common.RLog;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/6/23
 * 描述：
 * 数据库访问接口,目前接口有同步和异步之分
 * 第一次login时从app server获取数据,之后数据库读,数据的更新使用IMKit里的通知类消息
 * 因为存在app server获取数据失败的情况,代码里有很多这种异常情况的判断处理并重新从app server获取数据
 * 1.add...类接口为插入或更新数据库
 * 2.get...类接口为读取数据库
 * 3.delete...类接口为删除数据库数据
 * 4.sync...为同步接口,因为存在去掉sync相同名称的异步接口,有些同步类接口不带sync
 * 5.fetch...,pull...类接口都是从app server获取数据并存数据库,不同的只是返回值不一样,此类接口全部为private
 */
public class UserInfoManager {

    private final static String TAG = "UserInfoManager";
    private static final int GET_TOKEN = 800;

    private final Context mContext;

    static Handler mHandler;

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


    public UserInfoManager(Context context) {
        mContext = context;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static UserInfoManager getInstance() {
        return sInstance;
    }

    public static void init(Context context) {
        if (BuildConfig.DEBUG) {
            RLog.d(TAG, "UserInfoManager init");
        }
        sInstance = new UserInfoManager(context);
    }
}
