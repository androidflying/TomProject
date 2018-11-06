package com.tom.common.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.tom.baselib.delay.CallUnit;
import com.tom.common.GlobalParams;
import com.tom.common.R;
import com.tom.common.base.CommonActivity;
import com.tom.common.util.DisplayUtil;
import com.tom.common.valid.LoginAction;


public class LoginActivity extends CommonActivity {

    ImageView iv;

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {


        iv = findViewById(R.id.iv);
        iv.setOnClickListener(this);
    }

    @Override
    public void doBusiness() {
        DisplayUtil.showSimpleWithoutCache(iv, "https://gss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2018-04-24/32e000682f77c90e22c0b012450fe801.gif");

    }

    @Override
    public void onWidgetClick(View view) {
        GlobalParams.setIsLogin(true);
        CallUnit.newInstance(new LoginAction()).doCall();
        finish();
    }

}
