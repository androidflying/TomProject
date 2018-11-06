package com.android.tomflying.ui;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.android.tomflying.R;
import com.android.tomflying.adapter.OpenSourceAdapter;
import com.android.tomflying.base.MyActivity;
import com.android.tomflying.bean.OpenSourceBean;
import com.android.tomflying.bean.OpenSourceSectionBean;
import com.qmuiteam.tom.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.tom.widget.QMUITopBar;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.baselib.utils.ImageUtils;
import com.tom.brvah.BaseQuickAdapter;
import com.tom.brvah.itemdecoration.RecyclerViewDecoration;

import java.util.ArrayList;

public class OpenSourceActivity extends MyActivity {
    private RecyclerView recyclerView;
    private ImageView iv_user_header;

    private QMUICollapsingTopBarLayout mCollapsingTopBarLayout;
    private QMUITopBar mTopBar;

    private OpenSourceAdapter openSourceAdapter;
    private ArrayList<OpenSourceSectionBean> openSources = new ArrayList<>();

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_open_source;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        ImageView iv_open_source = contentView.findViewById(R.id.iv_open_source);
        Bitmap src = ImageUtils.getBitmap(R.drawable.bg_user);
        iv_open_source.setImageBitmap(ImageUtils.fastBlur(src, 0.2f, 10));

        mCollapsingTopBarLayout = findViewById(R.id.collapsing_topbar_layout);
        mTopBar = findViewById(R.id.topbar);

        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishActivity(OpenSourceActivity.class, true);
            }
        });

        mCollapsingTopBarLayout.setTitle("开源是第一生产力");

        recyclerView = findViewById(R.id.recyclerView);
        initGroupList();
    }


    private void initGroupList() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        openSourceAdapter = new OpenSourceAdapter(openSources);
        openSourceAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerView.setAdapter(openSourceAdapter);
        recyclerView.addItemDecoration(new RecyclerViewDecoration(mActivity, LinearLayoutManager.VERTICAL, 2, R.color.qmui_config_color_background));
        openSourceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                OpenSourceSectionBean bean = openSources.get(position);
                if (!bean.isHeader) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ArticleActivity.BUNDLE_URL, openSources.get(position).t.getUrl());
                    bundle.putString(ArticleActivity.BUNDLE_TITLE, openSources.get(position).t.getTitle());
                    ActivityUtils.startActivity(bundle, ArticleActivity.class);
                }
            }
        });
    }

    @Override
    public void doBusiness() {
        getOpenSource();
    }

    private void getOpenSource() {
        openSources.add(new OpenSourceSectionBean(true, "使用到的开源项目"));
        openSources.add(new OpenSourceSectionBean(new OpenSourceBean("QMUI_Android", "腾讯团队出品的快速开发UI框架", "https://github.com/QMUI/QMUI_Android", "1.1.2", "QMUI")));
        openSources.add(new OpenSourceSectionBean(new OpenSourceBean("AgentWeb", "基于Android WebView的极易使用以及功能强大的库", "https://github.com/Justson/AgentWeb", "4.0.2", "Justson")));
        openSources.add(new OpenSourceSectionBean(new OpenSourceBean("AndroidUtilCode", "一个强大易用的安卓工具类库，它合理地封装了安卓开发中常用的函数，可以大大提高开发效率", "https://github.com/Blankj/AndroidUtilCode", "1.15.1", "Blankj")));
        openSources.add(new OpenSourceSectionBean(new OpenSourceBean("greenDAO", "一个轻量级的Android ORM，它将对象映射到SQLite数据库。GRANDOAO对Android高度优化，提供了良好的性能和消耗最少的内存", "https://github.com/greenrobot/greenDAO", "3.2.2", "greenrobot")));
        openSources.add(new OpenSourceSectionBean(new OpenSourceBean("EventBus", "是发布/订阅Android和java事件总线", "https://github.com/greenrobot/EventBus", "3.1.1", "greenrobot")));
        openSources.add(new OpenSourceSectionBean(new OpenSourceBean("okhttp-OkGo", "一个基于okhttp的标准RESTful风格的网络框架", "https://github.com/jeasonlzy/okhttp-OkGo", "3.0.4", "jeasonlzy")));
        openSources.add(new OpenSourceSectionBean(new OpenSourceBean("Banner", "Android图片轮播控件", "https://github.com/youth5201314/banner", "1.4.10", "youth5201314")));
        openSources.add(new OpenSourceSectionBean(new OpenSourceBean("BaseRecyclerViewAdapterHelper", "一个强大的RecyclerAdapter框架，它能节约开发者大量的开发时间，集成了大部分列表常用需求解决方案", "https://github.com/CymChad/BaseRecyclerViewAdapterHelper", "2.9.40", "CymChad")));
        openSources.add(new OpenSourceSectionBean(new OpenSourceBean("glide", "Glide是一个快速高效的Android图片加载库，注重于平滑的滚动。Glide提供了易用的API，高性能、可扩展的图片解码管道（decode pipeline），以及自动的资源池技术", "https://github.com/bumptech/glide", "4.7.1", "bumptech")));
        openSources.add(new OpenSourceSectionBean(new OpenSourceBean("gson", "google开源的json解析器", "https://github.com/google/gson", "2.8.4", "google")));
        openSources.add(new OpenSourceSectionBean(new OpenSourceBean("delayActionDemo", "目标方法前置检验模型设计与实现", "https://github.com/jinyb09017/delayActionDemo", "1.0.1", "jinyb09017")));


        openSources.add(new OpenSourceSectionBean(true, "推荐的开源项目"));
        openSources.add(new OpenSourceSectionBean(new OpenSourceBean("EasyPhotos", "相机相册图片浏览选择器", "https://github.com/HuanTanSheng/EasyPhotos", "2.3.2", "HuanTanSheng")));
        openSources.add(new OpenSourceSectionBean(new OpenSourceBean("BGAQRCode-Android", "QRCode 扫描二维码、扫描条形码、相册获取图片后识别、生成带 Logo 二维码、支持微博微信 QQ 二维码扫描样式", "https://github.com/bingoogolapple/BGAQRCode-Android", "1.2.1", "bingoogolapple")));
        openSources.add(new OpenSourceSectionBean(new OpenSourceBean("PhotoView", "PhotoView是一个常用的图片预览控件，主要用于Android中大图查看", "https://github.com/chrisbanes/PhotoView", "2.1.3", "chrisbanes")));
        openSources.add(new OpenSourceSectionBean(new OpenSourceBean("butterknife", "PhotoView是一个常用的图片预览控件，一个专注于Android系统的View注入框架", "https://github.com/JakeWharton/butterknife", "8.8.1", "JakeWharton")));

        openSources.add(new OpenSourceSectionBean(new OpenSourceBean("JiaoZiVideoPlayer", "一个集成的播放器项目", "https://github.com/lipangit/JiaoZiVideoPlayer", "6.2.10", "lipangit")));
        openSources.add(new OpenSourceSectionBean(true, "未待完续。。。"));

        openSourceAdapter.setNewData(openSources);
    }

    @Override
    public void onWidgetClick(View view) {

    }
}
