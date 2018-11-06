package com.android.tomflying.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.tomflying.ApiConstant;
import com.android.tomflying.R;
import com.android.tomflying.adapter.GuideAdapter;
import com.android.tomflying.base.MyFragment;
import com.android.tomflying.bean.GuideBean;
import com.android.tomflying.bean.LzyResponse;
import com.android.tomflying.ui.SearchActivity;
import com.android.tomflying.util.JsonCallback;
import com.android.tomflying.util.OkHttpUtil;
import com.qmuiteam.tom.widget.QMUIEmptyView;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.brvah.entity.MultiItemEntity;
import com.tom.network.OkGo;
import com.tom.network.model.Response;
import com.tom.network.request.base.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/7
 * 描述：
 */
public class GuideFragment extends MyFragment {

    ArrayList<MultiItemEntity> datas = new ArrayList<>();
    private RecyclerView recyclerView;
    private GuideAdapter adapter;
    private QMUIEmptyView emptyView;

    @Override
    protected void onFirstUserVisible() {
        getGuide();
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

    private void getGuide() {
        OkHttpUtil.getRequest(ApiConstant.Guide.TREE_URL, this, null, new JsonCallback<LzyResponse<List<GuideBean>>>() {

            @Override
            public void onStart(Request<LzyResponse<List<GuideBean>>, ? extends Request> request) {
                showLoadingDialog();
            }

            @Override
            public void onSuccess(Response<LzyResponse<List<GuideBean>>> response) {
                setDatas(response.body().data);
                adapter.setNewData(datas);
            }

            @Override
            public void onError(Response<LzyResponse<List<GuideBean>>> response) {
                emptyView.setTitleText("网络错误");
                if (response != null) {
                    emptyView.setDetailText(response.message());
                }
                emptyView.setButton("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getGuide();
                    }
                });
                adapter.notifyDataSetChanged();
                adapter.setEmptyView(emptyView);
            }

            @Override
            public void onFinish() {
                missLoadingDialog();
            }
        });
    }

    private void setDatas(List<GuideBean> guides) {
        for (int i = 0; i < guides.size(); i++) {
            GuideBean guide = guides.get(i);
            if (guide.getVisible() == 1) {
                for (int j = 0; j < guide.getChildren().size(); j++) {
                    GuideBean.Children children = guide.getChildren().get(j);
                    guide.addSubItem(children);
                }
                datas.add(guide);
            }
        }
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
        return R.layout.fragment_guide;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        recyclerView = contentView.findViewById(R.id.rv_guide);
        emptyView = new QMUIEmptyView(mActivity);

        initAdapter();
        addHeader();
    }

    private void addHeader() {
        View view = getLayoutInflater().inflate(R.layout.head_view, (ViewGroup) recyclerView.getParent(), false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(SearchActivity.class);
            }
        });
        adapter.addHeaderView(view);
    }

    private void initAdapter() {
        adapter = new GuideAdapter(datas);

        final GridLayoutManager manager = new GridLayoutManager(mActivity, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getItemViewType(position) == GuideAdapter.TYPE_LEVEL_1 ? 1 : manager.getSpanCount();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public void onWidgetClick(View view) {

    }
}
