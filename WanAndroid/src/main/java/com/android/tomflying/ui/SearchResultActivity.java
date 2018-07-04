package com.android.tomflying.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.tomflying.ApiConstant;
import com.android.tomflying.R;
import com.android.tomflying.adapter.SearchArticleAdapter;
import com.android.tomflying.base.MyActivity;
import com.android.tomflying.bean.ArticlesBean;
import com.android.tomflying.bean.LzyResponse;
import com.android.tomflying.util.JsonCallback;
import com.android.tomflying.util.OkHttpUtil;
import com.google.gson.Gson;
import com.qmuiteam.tom.widget.QMUIEmptyView;
import com.qmuiteam.tom.widget.QMUITopBar;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.brvah.BaseQuickAdapter;
import com.tom.brvah.itemdecoration.RecyclerViewDecoration;
import com.tom.network.OkGo;
import com.tom.network.callback.StringCallback;
import com.tom.network.model.HttpParams;
import com.tom.network.model.Response;
import com.tom.network.request.base.Request;

import java.util.ArrayList;


/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 描述：
 */

public class SearchResultActivity extends MyActivity {

    public static String SEARCH_CONTENT = "search_content";

    private String search_content;
    private QMUITopBar topBar;
    private RecyclerView recyclerView;
    private QMUIEmptyView emptyView;
    private int page = 0;


    private SearchArticleAdapter articleAdapter;
    private ArrayList<ArticlesBean.Data> articles = new ArrayList<>();

    @Override
    public boolean isTransparent() {
        return super.isTransparent();
    }

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {
        search_content = bundle.getString(SEARCH_CONTENT);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_search_result;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        topBar = findViewById(R.id.topbar);
        topBar.setTitle("搜索结果");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishActivity(SearchResultActivity.class, true);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        emptyView = new QMUIEmptyView(mActivity);

        articles.clear();
        initArticles();
    }


    private void initArticles() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        articleAdapter = new SearchArticleAdapter(articles);
        recyclerView.setAdapter(articleAdapter);
        recyclerView.addItemDecoration(new RecyclerViewDecoration(mActivity, LinearLayoutManager.VERTICAL));
        articleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(ArticleActivity.BUNDLE_URL, articles.get(position).getLink());
                bundle.putString(ArticleActivity.BUNDLE_TITLE, articles.get(position).getTitle());
                ActivityUtils.startActivity(bundle, ArticleActivity.class);
            }
        });


        articleAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getResult(false);
            }
        }, recyclerView);


    }

    @Override
    public void doBusiness() {
        getResult(true);
    }

    private void getResult(final boolean isFresh) {
        if (!isFresh) {
            page++;
        } else {
            page = 0;
        }
        HttpParams params = new HttpParams();
        params.put("k", search_content);
        OkHttpUtil.postRequest(ApiConstant.Guide.SEARCH_URL + page + ApiConstant.END_URL, this, params, new JsonCallback<LzyResponse<ArticlesBean>>() {

            @Override
            public void onStart(Request<LzyResponse<ArticlesBean>, ? extends Request> request) {
                if (isFresh) {
                    showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(Response<LzyResponse<ArticlesBean>> response) {
                ArticlesBean data = response.body().data;
                articleAdapter.setEnableLoadMore(!data.isOver());
                //说明到最后一页了
                if (data.getCurPage() == data.getPageCount()) {
                    articleAdapter.loadMoreEnd();
                }
                if (data.getData() == null || data.getData().size() == 0) {
                    emptyView.show("暂无数据", "快去看看其他模块吧");
                    articleAdapter.setEmptyView(emptyView);
                } else {
                    if (isFresh) {
                        articles.clear();
                        articles.addAll(data.getData());
                        articleAdapter.setNewData(articles);
                    } else {
                        articleAdapter.loadMoreComplete();
                        articles.addAll(data.getData());
                        articleAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onError(Response<LzyResponse<ArticlesBean>> response) {
                emptyView.setTitleText("网络错误");
                emptyView.setDetailText(response.body().errorMsg);
                emptyView.setButton("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getResult(true);
                    }
                });
                articleAdapter.setEmptyView(emptyView);
            }

            @Override
            public void onFinish() {
                missLoadingDialog();
                recyclerView.stopScroll();
            }
        });

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
