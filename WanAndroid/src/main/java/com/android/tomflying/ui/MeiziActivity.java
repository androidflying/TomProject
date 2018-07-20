package com.android.tomflying.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.android.tomflying.ApiConstant;
import com.android.tomflying.R;
import com.android.tomflying.adapter.MeiziAdapter;
import com.android.tomflying.base.MyActivity;
import com.android.tomflying.bean.MeiziBean;
import com.android.tomflying.util.OkHttpUtil;
import com.google.gson.Gson;
import com.qmuiteam.tom.widget.QMUIEmptyView;
import com.qmuiteam.tom.widget.QMUITopBar;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.baselib.utils.ToastUtils;
import com.tom.brvah.BaseQuickAdapter;
import com.tom.network.callback.StringCallback;
import com.tom.network.model.Response;
import com.tom.network.request.base.Request;

import java.util.ArrayList;

public class MeiziActivity extends MyActivity {
    private QMUITopBar topBar;
    private RecyclerView recyclerView;
    private QMUIEmptyView emptyView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private MeiziAdapter meiziAdapter;
    private ArrayList<MeiziBean.Results> meizi = new ArrayList<>();

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_meizi;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        topBar = findViewById(R.id.topbar);
        recyclerView = findViewById(R.id.recyclerView);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        emptyView = new QMUIEmptyView(mActivity);
        topBar.setTitle("妹子集中营");

        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishActivity(MeiziActivity.class, true);
            }
        });
        emptyView.setLoadingShowing(true);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.app_color_blue));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                meiziAdapter.setEmptyView(emptyView);
                ToastUtils.showShort("妹子虽好，莫要贪杯哟~");
                doBusiness();
            }
        });

        initMeizi();
    }

    private void initMeizi() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        meiziAdapter = new MeiziAdapter(meizi);
        meiziAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerView.setAdapter(meiziAdapter);
        meiziAdapter.setEmptyView(emptyView);
        meiziAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                view.setTransitionName("pic");
                Bundle bundle = new Bundle();
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(MeiziActivity.this, view, "pic");
                bundle.putString(PictureActivity.IMG_URL, meizi.get(position).getUrl());
                ActivityUtils.startActivity(bundle, PictureActivity.class, options.toBundle());
            }
        });
    }

    @Override
    public void doBusiness() {
        OkHttpUtil.getRequets(ApiConstant.Others.MEIZI_URL, this, null, new StringCallback() {

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onSuccess(Response<String> response) {
                MeiziBean bean = new Gson().fromJson(response.body(), MeiziBean.class);
                if (bean.isError()) {
                    emptyView.show("暂无数据", "");
                    meiziAdapter.setEmptyView(emptyView);
                } else {
                    if (bean.getResults() != null || bean.getResults().size() != 0) {
                        meizi.clear();
                        meizi.addAll(bean.getResults());
                        meiziAdapter.setNewData(meizi);
                    } else {
                        emptyView.show("暂无数据", "快去看看其他模块吧");
                        meiziAdapter.setEmptyView(emptyView);
                    }
                }
            }
        });
    }

    @Override
    public void onWidgetClick(View view) {

    }
}
