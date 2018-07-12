package com.android.tomflying.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.SharedElementCallback;
import android.view.View;

import com.android.tomflying.R;
import com.android.tomflying.base.MyActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.qmuiteam.tom.util.QMUIStatusBarHelper;

import java.util.List;

public class PictureActivity extends MyActivity {

    public static String IMG_URL = "url";

    private PhotoView photoView;
    private String url;

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {
        url = bundle.getString(IMG_URL);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_picture;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        photoView = findViewById(R.id.photoView);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                RequestOptions requestOptions = new RequestOptions()
                        .centerInside()
                        .error(R.drawable.no_banner)
                        .placeholder(R.drawable.no_banner)
                        .priority(Priority.NORMAL);
                Glide.with(mActivity)
                        .load(url)
                        .apply(requestOptions)
                        .into(photoView);
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
