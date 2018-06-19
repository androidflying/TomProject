package com.android.tomflying.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.android.tomflying.ApiConstant;
import com.android.tomflying.R;
import com.android.tomflying.adapter.MyFragmentPagerAdapter;
import com.android.tomflying.base.MyFragment;
import com.android.tomflying.bean.LzyResponse;
import com.android.tomflying.bean.ProjectCateBean;
import com.android.tomflying.util.JsonCallback;
import com.android.tomflying.util.OkHttpUtil;
import com.google.gson.Gson;
import com.qmuiteam.tom.widget.QMUITabSegment;
import com.qmuiteam.tom.widget.QMUIViewPager;
import com.tom.network.OkGo;
import com.tom.network.callback.StringCallback;
import com.tom.network.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/7
 * 描述：
 */
public class ProjectFragment extends MyFragment {
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
        return R.layout.fragment_project;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        tabSegment = contentView.findViewById(R.id.tabSegment);
        viewPager = contentView.findViewById(R.id.viewPager);
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    protected void onFirstUserVisible() {

        getCate();

    }

    private void getCate() {
        OkHttpUtil.getRequets(ApiConstant.Project.TREE_URL, this, null, new JsonCallback<LzyResponse<List<ProjectCateBean>>>() {
            @Override
            public void onSuccess(Response<LzyResponse<List<ProjectCateBean>>> response) {
                setTab(response.body().data);
            }
        });
    }

    private void setTab(List<ProjectCateBean> data) {
        ArrayList<MyFragment> fragments = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            tabSegment.addTab(new QMUITabSegment.Tab(
                    data.get(i).getName()));

            fragments.add(new DetailFragment().newInstance(data.get(i).getId()));
        }
        MyFragmentPagerAdapter mPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragments);

        viewPager.setAdapter(mPagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        tabSegment.setupWithViewPager(viewPager, false);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void destroyViewAndThing() {
        OkGo.getInstance().cancelTag(this);
    }
}
