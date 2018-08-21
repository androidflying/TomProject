package com.android.tomflying.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.android.tomflying.ApiConstant;
import com.android.tomflying.R;
import com.android.tomflying.adapter.CateArticleAdapter;
import com.android.tomflying.base.MyActivity;
import com.android.tomflying.bean.ArticlesBean;
import com.android.tomflying.bean.LzyResponse;
import com.android.tomflying.util.JsonCallback;
import com.android.tomflying.util.OkHttpUtil;
import com.android.tomflying.valid.CollectAction;
import com.android.tomflying.valid.LoginValid;
import com.qmuiteam.tom.widget.QMUIEmptyView;
import com.qmuiteam.tom.widget.QMUITopBar;
import com.tom.baselib.delay.SingleCall;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.brvah.BaseQuickAdapter;
import com.tom.brvah.itemdecoration.RecyclerViewDecoration;
import com.tom.network.OkGo;
import com.tom.network.model.Response;
import com.tom.network.request.base.Request;

import java.util.ArrayList;

public class CategoryActivity extends MyActivity {
    public static String CID = "cid";
    public static String CNAME = "cname";
    private QMUITopBar topBar;
    private RecyclerView recyclerView;
    private QMUIEmptyView emptyView;
    private int cid;
    private String cname;
    private int page = 0;

    private CateArticleAdapter articleAdapter;
    private ArrayList<ArticlesBean.Data> articles = new ArrayList<>();

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {
        if (bundle != null) {
            cid = bundle.getInt(CID);
            cname = bundle.getString(CNAME);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_category;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        topBar = findViewById(R.id.topbar);
        recyclerView = findViewById(R.id.recyclerView);
        emptyView = new QMUIEmptyView(mActivity);
        topBar.setTitle(cname);

        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishActivity(CategoryActivity.class, true);
            }
        });

        articles.clear();
        initArticles();

    }


    private void initArticles() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        articleAdapter = new CateArticleAdapter(articles);
        articleAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
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

        articleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_favorite:
                        SingleCall.getInstance().addAction(new CollectAction((ImageView) view, articles.get(position))).addValid(new LoginValid()).doCall();
                        break;
                    default:
                        Bundle bundle = new Bundle();
                        bundle.putString(ArticleActivity.BUNDLE_URL, articles.get(position).getLink());
                        bundle.putString(ArticleActivity.BUNDLE_TITLE, articles.get(position).getTitle());
                        ActivityUtils.startActivity(bundle, ArticleActivity.class);
                }
            }
        });
        articleAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getArticles(false);
            }
        }, recyclerView);


    }

    @Override
    public void doBusiness() {

        getArticles(true);
    }

    private void getArticles(final boolean isFresh) {
        if (isFresh) {
            page = 0;
        } else {
            page++;
        }

        OkHttpUtil.getRequets(ApiConstant.Home.ARTICLES_URL + page + ApiConstant.END_URL + "?cid=" + cid, this, null, new JsonCallback<LzyResponse<ArticlesBean>>() {

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
                        getArticles(true);
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
