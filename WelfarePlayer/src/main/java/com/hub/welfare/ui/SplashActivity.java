package com.hub.welfare.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.hub.welfare.ConstantValues;
import com.hub.welfare.R;
import com.hub.welfare.base.MyActivity;
import com.hub.welfare.util.ThemeUtils;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.baselib.utils.SPUtils;

public class SplashActivity extends MyActivity {
    LottieAnimationView animationView;

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
        animationView = findViewById(R.id.animator_view);
        animationView.useHardwareAcceleration(true);
    }

    @Override
    public void doBusiness() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animationView.cancelAnimation();
                ActivityUtils.startActivity(MainActivity.class);
                ActivityUtils.finishActivity(SplashActivity.class);
            }
        }, 2000);
    }

    @Override
    public void onWidgetClick(View view) {

    }
}
