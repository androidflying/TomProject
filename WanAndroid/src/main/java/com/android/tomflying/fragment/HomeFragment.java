package com.android.tomflying.fragment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.android.tomflying.ApiConstant;
import com.android.tomflying.GlideImageLoader;
import com.android.tomflying.R;
import com.android.tomflying.adapter.MainArticleAdapter;
import com.android.tomflying.base.MyFragment;
import com.android.tomflying.bean.ArticlesBean;
import com.android.tomflying.bean.BannerBean;
import com.android.tomflying.bean.LzyResponse;
import com.android.tomflying.event.LoginEvent;
import com.android.tomflying.ui.ArticleActivity;
import com.android.tomflying.ui.CategoryActivity;
import com.android.tomflying.util.JsonCallback;
import com.android.tomflying.util.OkHttpUtil;
import com.android.tomflying.valid.CollectAction;
import com.android.tomflying.valid.LoginValid;
import com.qmuiteam.tom.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.tom.widget.QMUIEmptyView;
import com.qmuiteam.tom.widget.QMUITopBar;
import com.tom.banner.Banner;
import com.tom.banner.BannerConfig;
import com.tom.banner.OnBannerListener;
import com.tom.baselib.delay.SingleCall;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.brvah.BaseQuickAdapter;
import com.tom.brvah.itemdecoration.RecyclerViewDecoration;
import com.tom.network.OkGo;
import com.tom.network.model.Response;
import com.tom.network.request.base.Request;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/7
 * 描述：首页
 */
public class HomeFragment extends MyFragment {

    ArrayList<BannerBean> banners = new ArrayList<>();
    private Banner banner;
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> targets = new ArrayList<>();
    private ArrayList<ArticlesBean.Data> articles = new ArrayList<>();
    private RecyclerView recyclerView;
    private QMUIEmptyView emptyView;
    private QMUICollapsingTopBarLayout mCollapsingTopBarLayout;
    private QMUITopBar mTopBar;

    private MainArticleAdapter articleAdapter;

    private int page = 0;

    @Override
    protected void onFirstUserVisible() {
        //加载首页轮播图
        getBanner();
        getArticles(true);
    }

    @Override
    protected void onUserVisible() {
        banner.startAutoPlay();
    }

    @Override
    protected void onUserInvisible() {
        banner.stopAutoPlay();
    }

    @Override
    protected void destroyViewAndThing() {
        OkGo.getInstance().cancelTag(this);
    }

    private void getBanner() {
        OkHttpUtil.getRequets(ApiConstant.Home.BANNER_URL, this, null, new JsonCallback<LzyResponse<List<BannerBean>>>() {

            @Override
            public void onSuccess(Response<LzyResponse<List<BannerBean>>> response) {
                images.clear();
                titles.clear();
                banners.clear();
                banners.addAll(response.body().data);
                for (int i = 0; i < banners.size(); i++) {

                    if (banners.get(i).getIsVisible() == 1) {
                        images.add(banners.get(i).getImagePath());
                        titles.add(banners.get(i).getTitle());
                        targets.add(banners.get(i).getUrl());
                    }
                }

                banner.setImages(images);
                banner.setBannerTitles(titles);
                banner.start();
            }
        });

    }

    private void getArticles(final boolean isFresh) {
        if (!isFresh) {
            page++;
        } else {
            page = 0;
        }
        OkHttpUtil.getRequets(ApiConstant.Home.ARTICLES_URL + page + ApiConstant.END_URL, this, null, new JsonCallback<LzyResponse<ArticlesBean>>() {

            @Override
            public void onStart(Request<LzyResponse<ArticlesBean>, ? extends Request> request) {
                if (isFresh) {
                    showLoadingDialog();
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
            }

            @Override
            public void onSuccess(Response<LzyResponse<ArticlesBean>> response) {
                ArticlesBean data = response.body().data;
                articleAdapter.setEnableLoadMore(!data.isOver());
                //说明到最后一页了
                if (data.getCurPage() == data.getPageCount()) {
                    articleAdapter.loadMoreEnd(true);
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


        });

    }


    @Override
    public boolean isNeedRegister() {
        return true;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        emptyView = new QMUIEmptyView(mActivity);
        recyclerView = contentView.findViewById(R.id.rv_home);
        mCollapsingTopBarLayout = contentView.findViewById(R.id.collapsing_topbar_layout);
        mTopBar = contentView.findViewById(R.id.topbar);
        mCollapsingTopBarLayout.setScrimUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (((int) animation.getAnimatedValue()) == 255) {
                    mTopBar.setTitle(getString(R.string.app_name));
                    mCollapsingTopBarLayout.setTitle(getString(R.string.app_name));
                    banner.stopAutoPlay();
                } else {
                    mTopBar.setTitle("");
                    mCollapsingTopBarLayout.setTitle("");
                    banner.startAutoPlay();
                }
            }
        });
        initBanner(contentView);
        articles.clear();
        initArticles();
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public void onWidgetClick(View view) {

    }

    private void initBanner(View contentView) {
        banner = contentView.findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString(ArticleActivity.BUNDLE_URL, banners.get(position).getUrl());
                bundle.putString(ArticleActivity.BUNDLE_TITLE, banners.get(position).getTitle());
                ActivityUtils.startActivity(bundle, ArticleActivity.class);
            }
        });
    }

    private void initArticles() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        articleAdapter = new MainArticleAdapter(articles);
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
                Bundle bundle = new Bundle();
                switch (view.getId()) {

                    case R.id.tv_cate:
                        bundle.putString(CategoryActivity.CNAME, articles.get(position).getChapterName());
                        bundle.putInt(CategoryActivity.CID, articles.get(position).getChapterId());
                        ActivityUtils.startActivity(bundle, CategoryActivity.class);
                        break;
                    case R.id.iv_favorite:
                        SingleCall.getInstance().addAction(new CollectAction((ImageView) view, articles.get(position))).addValid(new LoginValid()).doCall();
                        break;
                    default:
                        bundle.putString(ArticleActivity.BUNDLE_URL, articles.get(position).getLink());
                        bundle.putString(ArticleActivity.BUNDLE_TITLE, articles.get(position).getTitle());
                        ActivityUtils.startActivity(bundle, ArticleActivity.class);
                        break;
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        getArticles(true);

    }

}
