package com.hub.welfare.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.hub.welfare.ApiConstant;
import com.hub.welfare.R;
import com.hub.welfare.adapter.JokeAdapter;
import com.hub.welfare.base.MyFragment;
import com.hub.welfare.model.JokeBean;
import com.tom.baselib.utils.LogUtils;
import com.tom.baselib.utils.ToastUtils;
import com.tom.brvah.BaseQuickAdapter;
import com.tom.brvah.itemdecoration.RecyclerViewDecoration;
import com.tom.network.OkGo;
import com.tom.network.callback.StringCallback;
import com.tom.network.model.Response;
import com.tom.network.request.base.Request;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/1
 * 描述：
 */
public class BooksFragment extends MyFragment {
    private int page = 1;
    private ContentLoadingProgressBar loadingProgressBar;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private JokeAdapter adapter;
    private List<JokeBean.Result.Data> datas = new ArrayList<>();

    @Override
    protected void onFirstUserVisible() {
        getJokes(true);
    }

    @Override
    protected void onUserVisible() {
        recyclerView.scrollToPosition(0);
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
        return R.layout.fragment_books;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        loadingProgressBar = contentView.findViewById(R.id.progressBar);
        refreshLayout = contentView.findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark);
        recyclerView = contentView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new JokeAdapter(datas);
        recyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                getJokes(false);
            }
        }, recyclerView);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getJokes(true);
            }
        });
    }

    private void getJokes(final boolean isFresh) {
        if (!isFresh) {
            page++;
        } else {
            page = 0;
        }

        OkGo.<String>get(ApiConstant.Jokes.JOKES_URL).tag(this).params("page", page).params("pagesize", 10)
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        if (isFresh) {
                            loadingProgressBar.show();
                        }
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {
                        if (response.isSuccessful() && !TextUtils.isEmpty(response.body())) {
                            JokeBean jokeBean = new Gson().fromJson(response.body(), JokeBean.class);
                            if (jokeBean.getError_code() == 0 && jokeBean.getReason().equals("Success")) {
                                if (isFresh) {
                                    datas.clear();
                                    datas.addAll(jokeBean.getResult().getData());
                                    adapter.setNewData(datas);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    adapter.loadMoreComplete();
                                    datas.addAll(jokeBean.getResult().getData());
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response.isSuccessful() && !TextUtils.isEmpty(response.body())) {
                            JokeBean jokeBean = new Gson().fromJson(response.body(), JokeBean.class);
                            if (jokeBean.getError_code() == 0 && jokeBean.getReason().equals("Success")) {

                                adapter.setEnableLoadMore(page <= 5);
                                //说明到最后一页了
                                if (page == 5) {
                                    adapter.loadMoreEnd();
                                }

                                if (isFresh) {
                                    datas.clear();
                                    datas.addAll(jokeBean.getResult().getData());
                                    adapter.setNewData(datas);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    adapter.loadMoreComplete();
                                    datas.addAll(jokeBean.getResult().getData());
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ToastUtils.showShort(response.message());
                    }

                    @Override
                    public void onFinish() {
                        loadingProgressBar.hide();
                        recyclerView.stopScroll();
                        refreshLayout.setRefreshing(false);
                    }
                });
    }
}
