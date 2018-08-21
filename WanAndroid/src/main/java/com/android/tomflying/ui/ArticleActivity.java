package com.android.tomflying.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.android.tomflying.R;
import com.android.tomflying.base.MyActivity;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebSettingsImpl;
import com.just.agentweb.AgentWebUIControllerImplBase;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.IWebLayout;
import com.just.agentweb.MiddlewareWebChromeBase;
import com.just.agentweb.MiddlewareWebClientBase;
import com.just.agentweb.PermissionInterceptor;
import com.qmuiteam.tom.widget.QMUITopBar;
import com.qmuiteam.tom.widget.dialog.QMUIBottomSheet;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.baselib.utils.ToastUtils;

public class ArticleActivity extends MyActivity {


    public static String BUNDLE_URL = "url";
    public static String BUNDLE_TITLE = "title";
    protected AgentWeb mAgentWeb;
    private QMUITopBar topBar;
    private LinearLayout container;
    private String url;
    private String title;

    private AgentWebUIControllerImplBase mAgentWebUIController;
    private MiddlewareWebChromeBase mMiddleWareWebChrome;
    private MiddlewareWebClientBase mMiddleWareWebClient;

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {
        if (bundle != null) {
            url = bundle.getString(BUNDLE_URL);
            title = bundle.getString(BUNDLE_TITLE);

            addBrowseHistory();
        }

    }

    private void addBrowseHistory() {
        //TODO 添加浏览记录数据
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_article;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        topBar = findViewById(R.id.topbar);
        container = findViewById(R.id.container);

        if (!TextUtils.isEmpty(title)) {
            topBar.setTitle(Html.fromHtml(title).toString());
        } else {
            topBar.setTitle(getString(R.string.app_name));
        }
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishActivity(ArticleActivity.class, true);
            }
        });
        topBar.addRightImageButton(R.mipmap.icon_topbar_overflow, R.id.topbar_right_change_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showBottomSheetList();
                    }
                });

        buildAgentWeb();
    }

    private void showBottomSheetList() {
        final int TAG_SHARE_RELOAD = 0;
        final int TAG_SHARE_COPYURL = 1;
        final int TAG_SHARE_WECHAT_FRIEND = 2;
        final int TAG_SHARE_WECHAT_MOMENT = 3;
        final int TAG_SHARE_QQ = 4;
        final int TAG_SHARE_QZONE = 5;
        final int TAG_SHARE_WEIBO = 6;
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(mActivity);
        builder
                .addItem(R.mipmap.jiguang_socialize_reload, "刷新", TAG_SHARE_RELOAD, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.jiguang_socialize_copyurl, "复制文章链接", TAG_SHARE_COPYURL, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.jiguang_socialize_wechat, "分享到微信", TAG_SHARE_WECHAT_FRIEND, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .addItem(R.mipmap.jiguang_socialize_wxcircle, "分享到朋友圈", TAG_SHARE_WECHAT_MOMENT, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .addItem(R.mipmap.jiguang_socialize_qq, "分享到QQ", TAG_SHARE_QQ, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .addItem(R.mipmap.jiguang_socialize_qzone, "分享到空间", TAG_SHARE_QZONE, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .addItem(R.mipmap.jiguang_socialize_sina, "分享到微博", TAG_SHARE_WEIBO, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)

                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView) {
                        dialog.dismiss();
                        int tag = (int) itemView.getTag();
                        switch (tag) {
                            case TAG_SHARE_RELOAD:
                                mAgentWeb.getWebCreator().getWebView().reload();
                                break;
                            case TAG_SHARE_COPYURL:
                                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                // 将文本内容放到系统剪贴板里。

                                cm.setPrimaryClip(ClipData.newPlainText(null, url));
                                ToastUtils.showShort("复制成功");
                                break;
                            case TAG_SHARE_WECHAT_FRIEND:
                                ToastUtils.showShort("分享到微信");
                                break;
                            case TAG_SHARE_WECHAT_MOMENT:
                                ToastUtils.showShort("分享到朋友圈");
                                break;
                            case TAG_SHARE_QQ:
                                ToastUtils.showShort("分享到QQ");
                                break;
                            case TAG_SHARE_QZONE:
                                ToastUtils.showShort("分享到QQ空间");
                                break;
                            case TAG_SHARE_WEIBO:
                                ToastUtils.showShort("分享到微博");
                                break;
                        }
                    }
                }).build().show();

    }


    protected void buildAgentWeb() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);


    }


    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }


    public @Nullable
    IAgentWebSettings getAgentWebSettings() {
        return AgentWebSettingsImpl.getInstance();
    }


    protected @Nullable
    WebChromeClient getWebChromeClient() {
        return null;
    }


    protected @Nullable
    WebViewClient getWebViewClient() {
        return null;
    }


    protected @Nullable
    WebView getWebView() {
        return null;
    }

    protected @Nullable
    IWebLayout getWebLayout() {
        return null;
    }

    protected @Nullable
    PermissionInterceptor getPermissionInterceptor() {
        return null;
    }

    public @Nullable
    AgentWebUIControllerImplBase getAgentWebUIController() {
        return null;
    }

    public @Nullable
    DefaultWebClient.OpenOtherPageWays getOpenOtherAppWay() {
        return null;
    }

    protected @NonNull
    MiddlewareWebChromeBase getMiddleWareWebChrome() {
        return this.mMiddleWareWebChrome = new MiddlewareWebChromeBase() {
        };
    }

    protected @NonNull
    MiddlewareWebClientBase getMiddleWareWebClient() {
        return this.mMiddleWareWebClient = new MiddlewareWebClientBase() {
        };
    }
}
