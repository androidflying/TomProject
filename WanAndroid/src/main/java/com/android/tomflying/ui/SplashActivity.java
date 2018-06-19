package com.android.tomflying.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.android.tomflying.ApiConstant;
import com.android.tomflying.BuildConfig;
import com.android.tomflying.ConstantValues;
import com.android.tomflying.GlobalParams;
import com.android.tomflying.R;
import com.android.tomflying.base.MyActivity;
import com.android.tomflying.bean.LoginBean;
import com.android.tomflying.bean.LzyResponse;
import com.android.tomflying.event.LoginEvent;
import com.android.tomflying.util.JsonCallback;
import com.android.tomflying.util.OkHttpUtil;
import com.google.gson.Gson;
import com.tencent.bugly.Bugly;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.baselib.utils.SPUtils;
import com.tom.baselib.utils.TimeUtils;
import com.tom.baselib.utils.ToastUtils;
import com.tom.network.OkGo;
import com.tom.network.cache.CacheMode;
import com.tom.network.callback.StringCallback;
import com.tom.network.model.HttpParams;
import com.tom.network.model.Response;
import com.tom.network.request.base.Request;

import org.greenrobot.eventbus.EventBus;

public class SplashActivity extends MyActivity {
    long currentTime;

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
    }

    @Override
    public void doBusiness() {


        String name = SPUtils.getInstance().getString(ConstantValues.NIKE_NAME);
        String password = SPUtils.getInstance().getString(ConstantValues.PASSWORD);

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            delayStart();
        } else {
            login(name, password);
        }
    }

    private void login(String name, String password) {
        HttpParams params = new HttpParams();
        params.put("username", name);
        params.put("password", password);
        OkHttpUtil.postRequest(ApiConstant.User.LOGIN_URL, this, params, new JsonCallback<LzyResponse<LoginBean>>() {

            @Override
            public void onStart(Request<LzyResponse<LoginBean>, ? extends Request> request) {
                currentTime = TimeUtils.getNowMills();
            }

            @Override
            public void onSuccess(Response<LzyResponse<LoginBean>> response) {
                SPUtils.getInstance().put(ConstantValues.NIKE_NAME, response.body().data.getUsername());
                SPUtils.getInstance().put(ConstantValues.PASSWORD, response.body().data.getPassword());
                GlobalParams.setIsLogin(true);
                EventBus.getDefault().post(new LoginEvent());
            }

            @Override
            public void onError(Response<LzyResponse<LoginBean>> response) {
                ToastUtils.showShort(response.body().errorMsg);
            }

            @Override
            public void onFinish() {
                long time = TimeUtils.getNowMills() - currentTime;
                if (time < 1500) {
                    delayStart();
                } else {
                    ActivityUtils.finishActivity(SplashActivity.this);
                    ActivityUtils.startActivity(MainActivity.class);
                }
            }
        });

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    private void delayStart() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityUtils.finishActivity(SplashActivity.this);
                ActivityUtils.startActivity(MainActivity.class);
            }
        }, 2000);
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
