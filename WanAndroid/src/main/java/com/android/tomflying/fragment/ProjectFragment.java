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
import com.qmuiteam.tom.widget.QMUIEmptyView;
import com.qmuiteam.tom.widget.QMUITabSegment;
import com.qmuiteam.tom.widget.QMUIViewPager;
import com.tom.network.OkGo;
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
    private QMUIEmptyView emptyView;

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
        emptyView = contentView.findViewById(R.id.mEmptyView);
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
                tabSegment.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                setTab(response.body().data);
            }

            @Override
            public void onError(Response<LzyResponse<List<ProjectCateBean>>> response) {
                tabSegment.setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
                emptyView.setTitleText("网络错误");
                if (response != null) {
                    emptyView.setDetailText(response.message());
                }
                emptyView.setButton("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getCate();
                    }
                });
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
