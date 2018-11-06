package com.android.tomflying.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.tomflying.R;
import com.android.tomflying.adapter.ActiviteAdapter;
import com.android.tomflying.base.MyActivity;
import com.android.tomflying.util.CoderUtil;
import com.android.tomflying.util.LunarUtil;
import com.qmuiteam.tom.widget.QMUIFloatLayout;
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
    private TextView tv_zuowei;

    private QMUIFloatLayout floatLayout;
    private RecyclerView rv_good;
    private RecyclerView rv_bad;

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
        topBar.setTitle("老黄历");
        topBar.setSubTitle("程序员专属");

        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishActivity(CalendarActivity.class, true);
            }
        });

//        topBar.addRightImageButton(R.mipmap.icon_topbar_overflow, R.id.topbar_right_change_button)
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        QMUIDialog.CustomDialogBuilder dialogBuilder = new QMUIDialog.CustomDialogBuilder(mActivity);
//                        dialogBuilder.setLayout(R.layout.drawablehelper_createfromview);
//                        final QMUIDialog dialog = dialogBuilder.setTitle("示例效果（点击下图关闭本浮层）").create();
//                        ImageView displayImageView = dialog.findViewById(R.id.createFromViewDisplay);
//                        Bitmap createFromViewBitmap = QMUIDrawableHelper.createBitmapFromView(mContentView);
//                        displayImageView.setImageBitmap(createFromViewBitmap);
//
//                        displayImageView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                        dialog.show();
//                    }
//                });

        tv_date = findViewById(R.id.tv_date);
        tv_day = findViewById(R.id.tv_day);
        tv_week = findViewById(R.id.tv_week);
        tv_nian = findViewById(R.id.tv_nian);
        tv_nongli = findViewById(R.id.tv_nongli);
        tv_xingzuo = findViewById(R.id.tv_xingzuo);
        tv_nvshen = findViewById(R.id.tv_nvshen);
        tv_zuowei = findViewById(R.id.tv_zuowei);
        floatLayout = findViewById(R.id.floatLayout);

        rv_good = findViewById(R.id.rv_good);
        rv_bad = findViewById(R.id.rv_bad);


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
        tv_week.setText(TimeUtils.getChineseWeek(new Date()));
        tv_nian.setText("【" + TimeUtils.getChineseZodiac(new Date()) + "年】");
        tv_nongli.setText(new LunarUtil(Calendar.getInstance()).toString());
        tv_xingzuo.setText(TimeUtils.getZodiac(new Date()));
    }

    private void setTips() {
        tv_nvshen.setText(CoderUtil.getNvshen());
        for (int i = 0; i < CoderUtil.getDrink().size(); i++) {
            TextView tv_drink = new TextView(mActivity);
            tv_drink.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv_drink.setBackgroundResource(R.drawable.bg_key);
            tv_drink.setTextSize(12);
            tv_drink.setText(CoderUtil.getDrink().get(i));
            floatLayout.addView(tv_drink);
        }
        tv_zuowei.setText(CoderUtil.getZuowei());
    }

    private void setGoods() {
        ActiviteAdapter adapter = new ActiviteAdapter(CoderUtil.getGoods());
        rv_good.setHasFixedSize(true);
        rv_good.setLayoutManager(new LinearLayoutManager(mActivity));
        rv_good.setAdapter(adapter);
    }

    private void setBads() {
        ActiviteAdapter adapter = new ActiviteAdapter(CoderUtil.getBads());
        rv_bad.setHasFixedSize(true);
        rv_bad.setLayoutManager(new LinearLayoutManager(mActivity));
        rv_bad.setAdapter(adapter);
    }

    @Override
    public void onWidgetClick(View view) {

    }
}
