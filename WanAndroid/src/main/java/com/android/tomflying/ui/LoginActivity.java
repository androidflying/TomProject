package com.android.tomflying.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.android.tomflying.R;
import com.android.tomflying.adapter.MyFragmentPagerAdapter;
import com.android.tomflying.base.MyActivity;
import com.android.tomflying.base.MyFragment;
import com.android.tomflying.fragment.LoginFragment;
import com.android.tomflying.fragment.RegisterFragment;
import com.qmuiteam.tom.widget.QMUITabSegment;
import com.qmuiteam.tom.widget.QMUITopBar;
import com.qmuiteam.tom.widget.QMUIViewPager;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.baselib.utils.KeyboardUtils;

import java.util.ArrayList;

public class LoginActivity extends MyActivity {

    private QMUITopBar topBar;
    private QMUITabSegment tabSegment;
    private QMUIViewPager viewPager;

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

        topBar = findViewById(R.id.topbar);
        tabSegment = findViewById(R.id.tabSegment);
        viewPager = findViewById(R.id.viewPager);

        topBar.setTitle("我的");

        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftInput(mActivity);
                ActivityUtils.finishActivity(LoginActivity.class, true);
            }
        });

        tabSegment.setIndicatorWidthAdjustContent(false);
        tabSegment.addTab(new QMUITabSegment.Tab("登录"));
        tabSegment.addTab(new QMUITabSegment.Tab("注册"));

        ArrayList<MyFragment> fragments = new ArrayList<>();
        LoginFragment loginFragment = new LoginFragment();
        RegisterFragment registerFragment = new RegisterFragment();
        fragments.add(loginFragment);
        fragments.add(registerFragment);

        MyFragmentPagerAdapter mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);

        viewPager.setAdapter(mPagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        tabSegment.setupWithViewPager(viewPager, false);

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
