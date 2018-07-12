package com.android.tomflying.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.android.tomflying.R;
import com.android.tomflying.base.MyActivity;
import com.android.tomflying.util.LunarUtil;
import com.qmuiteam.tom.widget.QMUITopBar;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.baselib.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends MyActivity {
    private QMUITopBar topBar;
    private TextView tv_date;
    private TextView tv_day;
    private TextView tv_week;
    private TextView tv_nian;
    private TextView tv_nongli;
    private TextView tv_xingzuo;
    private TextView tv_nvshen;
    private TextView tv_drink;
    private TextView tv_zuowei;

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_calendar;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        topBar = findViewById(R.id.topbar);
        topBar.setTitle(TimeUtils.getNowString(new SimpleDateFormat("yyyy年MM月dd日")));
        topBar.setSubTitle("星期" + TimeUtils.getChineseWeek(new Date()));

        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishActivity(CalendarActivity.class, true);
            }
        });

        tv_date = findViewById(R.id.tv_date);
        tv_day = findViewById(R.id.tv_day);
        tv_week = findViewById(R.id.tv_week);
        tv_nian = findViewById(R.id.tv_nian);
        tv_nongli = findViewById(R.id.tv_nongli);
        tv_xingzuo = findViewById(R.id.tv_xingzuo);
        tv_nvshen = findViewById(R.id.tv_nvshen);
        tv_drink = findViewById(R.id.tv_drink);
        tv_zuowei = findViewById(R.id.tv_zuowei);


    }

    @Override
    public void doBusiness() {
        setDate();
        setTips();
        setGoods();
        setBads();
    }

    private void setDate() {
        tv_date.setText(TimeUtils.getNowString(new SimpleDateFormat("yyyy年MM月")));
        tv_day.setText(TimeUtils.getNowString(new SimpleDateFormat("dd")));
        tv_week.setText("星期" + TimeUtils.getChineseWeek(new Date()));
        tv_nian.setText("【" + TimeUtils.getChineseZodiac(new Date()) + "年】");
        tv_nongli.setText(new LunarUtil(Calendar.getInstance()).toString());
        tv_xingzuo.setText(TimeUtils.getZodiac(new Date()));
    }

    private void setTips() {
        tv_nvshen.setText("女神指数：★★★★☆");
        tv_drink.setText("今日宜饮：可乐、雪碧");
        tv_zuowei.setText("码神位置：东南方");
    }

    private void setGoods() {

    }

    private void setBads() {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
