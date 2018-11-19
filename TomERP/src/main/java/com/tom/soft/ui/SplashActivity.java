package com.tom.soft.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.tom.baselib.utils.ActivityUtils;
import com.tom.soft.R;
import com.tom.soft.base.MyActivity;
import com.tom.soft.helper.UserHelper;

import androidx.annotation.NonNull;
import cn.bmob.v3.Bmob;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 描述：启动页
 */
public class SplashActivity extends MyActivity {

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        //TODO 展示日历信息（友好化）
    }

    @Override
    public void doBusiness() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityUtils.startActivity(MainActivity.class);
                ActivityUtils.finishActivity(SplashActivity.class);
            }
        }, 3000);
    }

    @Override
    public void onWidgetClick(View view) {

    }
}
