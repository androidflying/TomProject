package com.android.tomflying.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.android.tomflying.R;
import com.android.tomflying.adapter.MyFragmentPagerAdapter;
import com.android.tomflying.base.MyActivity;
import com.android.tomflying.base.MyFragment;
import com.android.tomflying.fragment.GuideFragment;
import com.android.tomflying.fragment.HomeFragment;
import com.android.tomflying.fragment.ProjectFragment;
import com.android.tomflying.fragment.UserFragment;
import com.qmuiteam.tom.widget.QMUITabSegment;
import com.qmuiteam.tom.widget.QMUIViewPager;
import com.tom.baselib.utils.BarUtils;

import java.util.ArrayList;

public class MainActivity extends MyActivity {
    QMUITabSegment tabSegment;
    QMUIViewPager viewPager;


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
        tabSegment = findViewById(R.id.tabSegment);
        viewPager = findViewById(R.id.viewPager);

        initSegment();
        initViewPagers();
    }


    private void initSegment() {


        QMUITabSegment.Tab home = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(mActivity, R.mipmap.icon_tab_home),
                ContextCompat.getDrawable(mActivity, R.mipmap.icon_tab_home_selected),
                "首页", false
        );

        QMUITabSegment.Tab guide = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(mActivity, R.mipmap.icon_tab_guide),
                ContextCompat.getDrawable(mActivity, R.mipmap.icon_tab_guide_selected),
                "导航", false
        );
        QMUITabSegment.Tab project = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(mActivity, R.mipmap.icon_tab_project),
                ContextCompat.getDrawable(mActivity, R.mipmap.icon_tab_project_selected),
                "项目", false
        );
        QMUITabSegment.Tab user = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(mActivity, R.mipmap.icon_tab_user),
                ContextCompat.getDrawable(mActivity, R.mipmap.icon_tab_user_selected),
                "我的", false
        );

        tabSegment.addTab(home);
        tabSegment.addTab(guide);
        tabSegment.addTab(project);
        tabSegment.addTab(user);

    }

    private void initViewPagers() {
        ArrayList<MyFragment> fragments = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        GuideFragment guideFragment = new GuideFragment();
        ProjectFragment projectFragment = new ProjectFragment();
        UserFragment userFragment = new UserFragment();
        fragments.add(homeFragment);
        fragments.add(guideFragment);
        fragments.add(projectFragment);
        fragments.add(userFragment);

        MyFragmentPagerAdapter mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);

        viewPager.setAdapter(mPagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        tabSegment.setupWithViewPager(viewPager, false);

        tabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
                updateStatusBar(index);
            }

            @Override
            public void onTabUnselected(int index) {

            }

            @Override
            public void onTabReselected(int index) {
            }

            @Override
            public void onDoubleTap(int index) {

            }
        });

    }

    private void updateStatusBar(int index) {
        switch (index) {
            case 0:
                BarUtils.setStatusBarLightMode(mActivity, false);
                break;
            case 1:
                BarUtils.setStatusBarLightMode(mActivity, true);
                break;
            case 2:
                BarUtils.setStatusBarLightMode(mActivity, true);
                break;
            case 3:
                BarUtils.setStatusBarLightMode(mActivity, false);
                break;
            default:
                BarUtils.setStatusBarLightMode(mActivity, false);
        }
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
