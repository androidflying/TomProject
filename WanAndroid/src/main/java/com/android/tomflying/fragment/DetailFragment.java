package com.android.tomflying.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tomflying.ApiConstant;
import com.android.tomflying.R;
import com.android.tomflying.adapter.ProjectAdapter;
import com.android.tomflying.base.MyFragment;
import com.android.tomflying.bean.ArticlesBean;
import com.android.tomflying.bean.LzyResponse;
import com.android.tomflying.ui.ArticleActivity;
import com.android.tomflying.util.JsonCallback;
import com.android.tomflying.util.OkHttpUtil;
import com.android.tomflying.valid.CollectAction;
import com.android.tomflying.valid.LoginValid;
import com.google.gson.Gson;
import com.qmuiteam.tom.widget.QMUIEmptyView;
import com.tom.baselib.delay.SingleCall;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.brvah.BaseQuickAdapter;
import com.tom.brvah.itemdecoration.RecyclerViewDecoration;
import com.tom.network.OkGo;
import com.tom.network.callback.StringCallback;
import com.tom.network.model.Response;
import com.tom.network.request.base.Request;

import java.util.ArrayList;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/10
 * 描述：
 */
public class DetailFragment extends MyFragment {

    ProjectAdapter projectAdapter;
    private String CID = "cid";
    private int cid;
    private TextView tv_cate;
    private int page = 1;
    private ArrayList<ArticlesBean.Data> projects = new ArrayList<>();
    private RecyclerView recyclerView;
    private QMUIEmptyView emptyView;

    @Override
    protected void onFirstUserVisible() {
        getProjects(true, cid);
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

    private void getProjects(final boolean fresh, final int cid) {
        if (fresh) {
            page = 1;
        } else {
            page++;
        }

        OkHttpUtil.getRequets(ApiConstant.Project.PROJECTS_URL + page + ApiConstant.END_URL + "?cid=" + cid, this, null, new JsonCallback<LzyResponse<ArticlesBean>>() {

            @Override
            public void onStart(Request<LzyResponse<ArticlesBean>, ? extends Request> request) {
                if (fresh) {
                    showLoadingDialog();
                }
            }

            @Override
            public void onSuccess(Response<LzyResponse<ArticlesBean>> response) {
                ArticlesBean data = response.body().data;
                projectAdapter.setEnableLoadMore(!data.isOver());
                //说明到最后一页了
                if (data.getCurPage() == data.getPageCount()) {
                    projectAdapter.loadMoreEnd();
                }
                if (data.getData() == null || data.getData().size() == 0) {
                    emptyView.show("暂无数据", "快去看看其他模块吧");
                    projectAdapter.setEmptyView(emptyView);
                } else {
                    if (fresh) {
                        projects.clear();
                        projects.addAll(data.getData());
                        projectAdapter.setNewData(projects);
                    } else {
                        projectAdapter.loadMoreComplete();
                        projects.addAll(data.getData());
                        projectAdapter.notifyDataSetChanged();
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
                        getProjects(true, cid);
                    }
                });
                projectAdapter.setEmptyView(emptyView);
            }

            @Override
            public void onFinish() {
                missLoadingDialog();
            }
        });

    }

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {
        if (bundle != null) {
            cid = bundle.getInt(CID);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        tv_cate = contentView.findViewById(R.id.tv_cate);
        recyclerView = contentView.findViewById(R.id.rv_detail);
        emptyView = new QMUIEmptyView(mActivity);
        initProjects();

    }

    private void initProjects() {
        projects.clear();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        projectAdapter = new ProjectAdapter(projects);
        recyclerView.setAdapter(projectAdapter);
        recyclerView.addItemDecoration(new RecyclerViewDecoration(mActivity, LinearLayoutManager.VERTICAL));
        projectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(ArticleActivity.BUNDLE_URL, projects.get(position).getLink());
                bundle.putString(ArticleActivity.BUNDLE_TITLE, projects.get(position).getTitle());
                ActivityUtils.startActivity(bundle, ArticleActivity.class);
            }
        });

        projectAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {

                    case R.id.iv_favorite:
                        SingleCall.getInstance().addAction(new CollectAction((ImageView) view, projects.get(position))).addValid(new LoginValid()).doCall();
                        break;
                    default:
                        Bundle bundle = new Bundle();
                        bundle.putString(ArticleActivity.BUNDLE_URL, projects.get(position).getLink());
                        bundle.putString(ArticleActivity.BUNDLE_TITLE, projects.get(position).getTitle());
                        ActivityUtils.startActivity(bundle, ArticleActivity.class);
                }
            }
        });
        projectAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getProjects(false, cid);
            }
        }, recyclerView);

    }

    public DetailFragment newInstance(int courseId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(CID, courseId);
        fragment.setArguments(args);
        return fragment;
    }
}
