package com.android.tomflying.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import android.view.View;

import com.android.tomflying.R;
import com.android.tomflying.base.MyActivity;
import com.qmuiteam.tom.widget.QMUITopBar;
import com.tom.baselib.utils.ActivityUtils;

public class AboutActivity extends MyActivity {
    private QMUITopBar topBar;

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_about;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        topBar = findViewById(R.id.topbar);
        topBar.setTitle("关于我们");

        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishActivity(AboutActivity.class, true);
            }
        });
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
