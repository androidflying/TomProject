package com.hub.welfare.fragment;

import android.os.Bundle;
import android.view.View;

import com.hub.welfare.R;
import com.hub.welfare.base.MyFragment;
import com.tom.baselib.utils.LogUtils;

import androidx.annotation.NonNull;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/1
 * 描述：
 */
public class VideoFragment extends MyFragment {
    @Override
    protected void onFirstUserVisible() {
        LogUtils.e("video first");
    }

    @Override
    protected void onUserVisible() {
        LogUtils.e("video view");
    }

    @Override
    protected void onUserInvisible() {
        LogUtils.e("video inview");
    }

    @Override
    protected void destroyViewAndThing() {
        LogUtils.e("video destroy");
    }

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_video;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

    }
}
