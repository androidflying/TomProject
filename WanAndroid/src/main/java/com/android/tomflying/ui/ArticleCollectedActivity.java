package com.android.tomflying.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.tomflying.ApiConstant;
import com.android.tomflying.R;
import com.android.tomflying.adapter.CollectArticleAdapter;
import com.android.tomflying.base.MyActivity;
import com.android.tomflying.bean.CollectArticleBean;
import com.android.tomflying.bean.LzyResponse;
import com.android.tomflying.bean.SimpleResponse;
import com.android.tomflying.util.JsonCallback;
import com.android.tomflying.util.OkHttpUtil;
import com.qmuiteam.tom.widget.QMUIEmptyView;
import com.qmuiteam.tom.widget.QMUITopBar;
import com.qmuiteam.tom.widget.dialog.QMUIDialog;
import com.qmuiteam.tom.widget.dialog.QMUITipDialog;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.brvah.BaseQuickAdapter;
import com.tom.brvah.itemdecoration.RecyclerViewDecoration;
import com.tom.network.OkGo;
import com.tom.network.model.HttpParams;
import com.tom.network.model.Response;
import com.tom.network.request.base.Request;

import java.util.ArrayList;

public class ArticleCollectedActivity extends MyActivity {
    private QMUITopBar topBar;
    private RecyclerView recyclerView;
    private QMUIEmptyView emptyView;
    private int page = 0;


    private CollectArticleAdapter articleAdapter;
    private ArrayList<CollectArticleBean.Data> articles = new ArrayList<>();

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_article_collected;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        topBar = findViewById(R.id.topbar);
        topBar.setTitle("收藏的文章");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishActivity(ArticleCollectedActivity.class, true);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        emptyView = new QMUIEmptyView(mActivity);

        articles.clear();
        initArticles();
    }

    @Override
    public void doBusiness() {
        getArticles(true);
    }

    @Override
    public void onWidgetClick(View view) {

    }

    private void initArticles() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        articleAdapter = new CollectArticleAdapter(articles);
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

        articleAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                showMenuDialog(position);
                return true;
            }
        });

        articleAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getArticles(false);
            }
        }, recyclerView);


    }

    private void showMenuDialog(final int position) {
        new QMUIDialog.MenuDialogBuilder(mActivity)
                .addItem("取消收藏", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HttpParams params = new HttpParams();
                        params.put("originId", articles.get(position).getOriginId());
                        OkHttpUtil.postRequest(ApiConstant.User.DO_UNCOLLECT_OUT_ARTICLE_URL + articles.get(position).getId() + ApiConstant.END_URL, this, params, new JsonCallback<SimpleResponse>() {
                            @Override
                            public void onSuccess(Response<SimpleResponse> response) {
                                if (response.body().toLzyResponse().errorCode == 0) {
                                    articleAdapter.remove(position);
                                    showSuccess("取消成功");
                                }

                            }
                        });
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    private void showSuccess(String message) {
        final QMUITipDialog dialog = new QMUITipDialog.Builder(mActivity)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(message)
                .create();

        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 500);

    }

    private void getArticles(final boolean isFresh) {
        if (isFresh) {
            page = 0;
        } else {
            page++;
        }

        OkHttpUtil.getRequets(ApiConstant.User.COLLECT_ARTICLES_URL + page + ApiConstant.END_URL, this, null, new JsonCallback<LzyResponse<CollectArticleBean>>() {

            @Override
            public void onStart(Request<LzyResponse<CollectArticleBean>, ? extends Request> request) {
                if (isFresh) {
                    showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(Response<LzyResponse<CollectArticleBean>> response) {

                CollectArticleBean data = response.body().data;
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
            public void onError(Response<LzyResponse<CollectArticleBean>> response) {
                emptyView.setTitleText("网络错误");
                emptyView.setDetailText(response.message());
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
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
