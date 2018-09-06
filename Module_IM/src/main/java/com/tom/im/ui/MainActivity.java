package com.tom.im.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.easy.photo.EasyPhotos;
import com.tom.common.GlideEngine;
import com.tom.common.base.CommonActivity;
import com.tom.common.util.SelectorUtil;
import com.tom.im.R;

import java.util.ArrayList;

public class MainActivity extends CommonActivity {

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectorUtil.selectMultiplePhoto(MainActivity.this, 9, null, 101);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> images = new ArrayList<>();

                images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533906345015&di=4e6feb8b9fa1a8fade8e0bbb3a3156df&imgtype=0&src=http%3A%2F%2Fcdn.lineshapespace.com%2F2013%2F10%2Fgrow_your_business-1024x512.jpg");
                images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533906345013&di=c81cecc0749b377b09e14978d53cc5b5&imgtype=0&src=http%3A%2F%2Fhacked.com%2Fwp-content%2Fuploads%2F2018%2F04%2Fshopin-splash.png");
                images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533906383536&di=9ceb8dd886c3bdc03e2f220c7ace49d2&imgtype=0&src=http%3A%2F%2Fd.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F0b55b319ebc4b74560d94cd3c3fc1e178b8215a1.jpg");
                images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533906383536&di=c5ba4618b8df21144a8835847a16c2a1&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F6c224f4a20a44623530945a79422720e0df3d730.jpg");
                images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533906383536&di=d81e429fd586ec0a885e7af9fb4cfc99&imgtype=0&src=http%3A%2F%2Fd.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fb17eca8065380cd79a75c52cad44ad3458828183.jpg");

                EasyPhotos.createPreview(MainActivity.this, GlideEngine.getInstance()).setSelectedPhotoPaths(images)
                        .start(2);
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
