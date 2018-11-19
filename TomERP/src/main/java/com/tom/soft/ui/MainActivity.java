package com.tom.soft.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tom.baselib.utils.ToastUtils;
import com.tom.soft.R;
import com.tom.soft.base.MyActivity;
import com.tom.soft.helper.UserHelper;
import com.tom.soft.model.TomUser;

import java.util.Random;

import androidx.annotation.NonNull;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/19
 * 描述：
 */
public class MainActivity extends MyActivity {
    TextView tv_user;

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        tv_user = findViewById(R.id.tv_user);

        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
    }

    @Override
    public void doBusiness() {

        UserHelper.fetchUserInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {

                if (e == null) {
                    TomUser tomUser = new Gson().fromJson(s, TomUser.class);
                    tv_user.setText("昵称：" + tomUser.getUsername()
                            + "\n手机号：" + tomUser.getMobilePhoneNumber()
                            + "\n是否验证手机号：" + tomUser.getMobilePhoneNumberVerified()
                            + "\n真实姓名：" + tomUser.getRealName()

                            + "\n工号：" + tomUser.getWorkId());
                } else {
                    ToastUtils.showLong(e.toString());
                }


            }
        });
    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                break;
            case R.id.button2:
                break;
            case R.id.button3:
                break;
            case R.id.button4:
                break;
            case R.id.button5:
                break;
            case R.id.button6:
                UserHelper.register("是是非非", "123456", "13103854517", new SaveListener<TomUser>() {
                    @Override
                    public void done(TomUser tomUser, BmobException e) {
                        if (e == null) {
                            tv_user.setText("昵称：" + tomUser.getUsername()
                                    + "\n手机号：" + tomUser.getMobilePhoneNumber()
                                    + "\n是否验证手机号：" + tomUser.getMobilePhoneNumberVerified()
                                    + "\n真实姓名：" + tomUser.getRealName()

                                    + "\n工号：" + tomUser.getWorkId());
                        } else {
                            ToastUtils.showLong(e.toString());
                        }
                    }
                });
                break;
            case R.id.button7:
                UserHelper.updateRealnameInfo("田非非", new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            ToastUtils.showLong("更新成功");

                        } else {
                            ToastUtils.showLong(e.toString());
                        }
                    }
                });
                break;
            case R.id.button8:
                UserHelper.loginByName("是是非非", "123456", new LogInListener<TomUser>() {
                    @Override
                    public void done(TomUser tomUser, BmobException e) {
                        if (e == null) {
                            tv_user.setText("昵称：" + tomUser.getUsername()
                                    + "\n手机号：" + tomUser.getMobilePhoneNumber()
                                    + "\n是否验证手机号：" + tomUser.getMobilePhoneNumberVerified()
                                    + "\n真实姓名：" + tomUser.getRealName()
                                    + "\n工号：" + tomUser.getWorkId());
                        } else {
                            ToastUtils.showLong(e.toString());
                        }
                    }
                });
                break;
            case R.id.button9:

                UserHelper.logout();
                break;
            default:
        }
    }
}
