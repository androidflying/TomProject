package com.tom.soft.helper;

import com.tom.soft.model.Department;
import com.tom.soft.model.TomUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/19
 * 描述：用户表的相关操作
 */
public class UserHelper {

    /**
     * 用户注册
     *
     * @param name
     * @param password
     * @param mobile
     * @param listener
     */
    public static void register(String name, String password, String mobile, SaveListener<TomUser> listener) {
        BmobUser bu = new BmobUser();
        bu.setUsername(name);
        bu.setPassword(password);
        bu.setMobilePhoneNumber(mobile);
        //为了省钱，默认手机号已验证
        bu.setMobilePhoneNumberVerified(true);
        bu.signUp(listener);
    }

    /**
     * 通过用户名登录
     *
     * @param name
     * @param password
     * @param listener
     */
    public static void loginByName(String name, String password, LogInListener<TomUser> listener) {
        BmobUser.loginByAccount(name, password, listener);
    }

    /**
     * 通过手机号登录
     *
     * @param phone
     * @param password
     * @param listener
     */
    public static void loginByPhone(String phone, String password, LogInListener<TomUser> listener) {
        BmobUser.loginByAccount(phone, password, listener);
    }

    public static void fetchUserInfo(FetchUserInfoListener<String> listener) {
        BmobUser.fetchUserJsonInfo(listener);
    }

    /**
     * 退出登录
     */
    public static void logout() {
        BmobUser.logOut();
    }

    /**
     * 是否登录用户
     *
     * @return true 已登录 ::: false 未登录
     */
    public static boolean isLogin() {
        TomUser user = BmobUser.getCurrentUser(TomUser.class);
        return user != null;
    }

    /**
     * 修改密码
     *
     * @param oldPwd
     * @param newPwd
     * @param listener
     */
    public static void changePassword(String oldPwd, String newPwd, UpdateListener listener) {
        BmobUser.updateCurrentUserPassword(oldPwd, newPwd, listener);
    }

    /**
     * 修改用户名
     *
     * @param name
     * @param listener
     */
    public static void updateUsernameInfo(String name, UpdateListener listener) {
        TomUser newUser = new TomUser();
        newUser.setUsername(name);
        TomUser bmobUser = BmobUser.getCurrentUser(TomUser.class);
        newUser.update(bmobUser.getObjectId(), listener);
    }

    /**
     * 修改手机号
     *
     * @param phone
     * @param listener
     */
    public static void updateMobileInfo(String phone, UpdateListener listener) {
        TomUser newUser = new TomUser();
        newUser.setMobilePhoneNumber(phone);
        TomUser bmobUser = BmobUser.getCurrentUser(TomUser.class);
        newUser.update(bmobUser.getObjectId(), listener);
    }

    public static void updateRealnameInfo(String name, UpdateListener listener) {
        TomUser newUser = new TomUser();
        newUser.setRealName(name);
        TomUser bmobUser = BmobUser.getCurrentUser(TomUser.class);
        newUser.update(bmobUser.getObjectId(), listener);
    }
}
