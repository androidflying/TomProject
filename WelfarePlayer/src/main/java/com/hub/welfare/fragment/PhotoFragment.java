package com.hub.welfare.fragment;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.hub.welfare.ApiConstant;
import com.hub.welfare.R;
import com.hub.welfare.base.MyFragment;
import com.hub.welfare.model.HuaCateBean;
import com.tom.baselib.utils.LogUtils;
import com.tom.baselib.utils.ToastUtils;
import com.tom.network.OkGo;
import com.tom.network.callback.StringCallback;
import com.tom.network.model.Response;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/1
 * 描述：
 */
public class PhotoFragment extends MyFragment {
    private RecyclerView rv_category;
    private RecyclerView rv_photos;

    @Override
    protected void onFirstUserVisible() {
        getCategory();
    }


    @Override
    protected void onUserVisible() {
        LogUtils.e("photo view");
    }

    @Override
    protected void onUserInvisible() {
        OkGo.cancelTag(OkGo.getInstance().getOkHttpClient(), this);
    }

    @Override
    protected void destroyViewAndThing() {
        OkGo.cancelTag(OkGo.getInstance().getOkHttpClient(), this);
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
        return R.layout.fragment_photo;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        rv_category = contentView.findViewById(R.id.rv_category);
        rv_photos = contentView.findViewById(R.id.rv_photos);
    }

    private void getCategory() {

    }

}
