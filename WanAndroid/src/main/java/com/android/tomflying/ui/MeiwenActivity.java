package com.android.tomflying.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tomflying.ApiConstant;
import com.android.tomflying.R;
import com.android.tomflying.base.MyActivity;
import com.android.tomflying.bean.MeiwenBean;
import com.android.tomflying.util.OkHttpUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.qmuiteam.tom.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.tom.widget.QMUITopBar;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.baselib.utils.SpanUtils;
import com.tom.network.callback.StringCallback;
import com.tom.network.model.Response;
import com.tom.network.request.base.Request;

import java.util.Random;

public class MeiwenActivity extends MyActivity {
    private QMUICollapsingTopBarLayout mCollapsingTopBarLayout;
    private QMUITopBar mTopBar;
    private ImageView iv_meitu;
    private TextView tv_author;
    private TextView tv_content;

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_meiwen;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        mCollapsingTopBarLayout = findViewById(R.id.collapsing_topbar_layout);
        mTopBar = findViewById(R.id.topbar);
        iv_meitu = findViewById(R.id.iv_meitu);

        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishActivity(MeiwenActivity.class, true);
            }
        });


        tv_author = findViewById(R.id.tv_author);
        tv_content = findViewById(R.id.tv_content);
    }

    @Override
    public void doBusiness() {
        setImage();
        getMeiwen();
    }

    private void setImage() {
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .error(R.drawable.no_banner)
                .placeholder(R.drawable.no_banner)
                .priority(Priority.NORMAL);
        Glide.with(mActivity)
                .load(ApiConstant.Others.MEITU_URL + (new Random().nextInt(97) + 1) + ".jpg")
                .apply(requestOptions)
                .into(iv_meitu);
    }

    private void getMeiwen() {
        OkHttpUtil.getRequets(ApiConstant.Others.MEIWEN_URL, this, null, new StringCallback() {
            @Override
            public void onStart(Request<String, ? extends Request> request) {
                showLoadingDialog();
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }

            @Override
            public void onFinish() {
                missLoadingDialog();
            }

            @Override
            public void onSuccess(Response<String> response) {
                MeiwenBean bean = new Gson().fromJson(response.body(), MeiwenBean.class);
                mCollapsingTopBarLayout.setTitle(bean.getData().getTitle());
                tv_author.setText("作者：" + bean.getData().getAuthor() + "\n\n包含字数：" + bean.getData().getWc() + "字");

                tv_content.setText(new SpanUtils().appendLine(Html.fromHtml(bean.getData().getContent())).setLeadingMargin((int) tv_content.getTextSize() * 2, 10).create());
            }
        });
    }

    @Override
    public void onWidgetClick(View view) {

    }
}
