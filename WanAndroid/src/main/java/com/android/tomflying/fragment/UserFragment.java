package com.android.tomflying.fragment;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tomflying.ConstantValues;
import com.android.tomflying.GlobalParams;
import com.android.tomflying.R;
import com.android.tomflying.base.MyFragment;
import com.android.tomflying.event.LoginEvent;
import com.android.tomflying.ui.AboutActivity;
import com.android.tomflying.ui.LoginActivity;
import com.android.tomflying.ui.OpenSourceActivity;
import com.android.tomflying.util.DataCleanManager;
import com.android.tomflying.valid.ArticleAction;
import com.android.tomflying.valid.LoginValid;
import com.qmuiteam.tom.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.tom.widget.QMUITopBar;
import com.qmuiteam.tom.widget.dialog.QMUIDialog;
import com.qmuiteam.tom.widget.dialog.QMUIDialogAction;
import com.qmuiteam.tom.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.tom.widget.grouplist.QMUIGroupListView;
import com.tencent.bugly.beta.Beta;
import com.tom.baselib.delay.SingleCall;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.baselib.utils.AppUtils;
import com.tom.baselib.utils.CacheDiskUtils;
import com.tom.baselib.utils.CacheDoubleUtils;
import com.tom.baselib.utils.CacheMemoryUtils;
import com.tom.baselib.utils.ImageUtils;
import com.tom.baselib.utils.SPUtils;
import com.tom.baselib.utils.ToastUtils;
import com.tom.network.OkGo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/7
 * 描述：
 */
public class UserFragment extends MyFragment {


    private QMUIGroupListView mGroupListView;
    private ImageView iv_user_header;

    private QMUICollapsingTopBarLayout mCollapsingTopBarLayout;
    private QMUITopBar mTopBar;

    private QMUIDialog.CheckBoxMessageDialogBuilder checkBoxMessageDialogBuilder;
    private TextView tv_name;

    private QMUICommonListItemView clean;

    @Override
    public boolean isNeedRegister() {
        return true;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_user;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        ImageView fragment_user_header = contentView.findViewById(R.id.fragment_user_header);
        Bitmap src = ImageUtils.getBitmap(R.drawable.bg_user);
        fragment_user_header.setImageBitmap(ImageUtils.fastBlur(src, 0.2f, 10));

        iv_user_header = contentView.findViewById(R.id.iv_user_header);
        tv_name = contentView.findViewById(R.id.tv_name);
        iv_user_header.setOnClickListener(this);

        mCollapsingTopBarLayout = contentView.findViewById(R.id.collapsing_topbar_layout);
        mTopBar = contentView.findViewById(R.id.topbar);

        if (GlobalParams.isLogin()) {
            tv_name.setText(SPUtils.getInstance().getString(ConstantValues.NIKE_NAME));
        } else {
            tv_name.setText("点击头像进行登录");
        }

        mCollapsingTopBarLayout.setScrimUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (((int) animation.getAnimatedValue()) == 255) {

                    if (GlobalParams.isLogin()) {
                        mTopBar.setTitle(SPUtils.getInstance().getString(ConstantValues.NIKE_NAME));
                        mCollapsingTopBarLayout.setTitle(SPUtils.getInstance().getString(ConstantValues.NIKE_NAME));
                    } else {
                        mTopBar.setTitle("请登录");
                        mCollapsingTopBarLayout.setTitle("请登录");
                    }

                } else {
                    mTopBar.setTitle("");
                    mCollapsingTopBarLayout.setTitle("");
                }
            }
        });

        mGroupListView = contentView.findViewById(R.id.groupListView);
        initGroupList();
    }

    private void initGroupList() {
        QMUICommonListItemView article = mGroupListView.createItemView("我的收藏");
        article.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView share = mGroupListView.createItemView("分享记录");
        share.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView history = mGroupListView.createItemView("浏览记录");
        history.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUIGroupListView.newSection(getContext())
                .setTitle("我的")
                .addItemView(article, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SingleCall.getInstance().addAction(new ArticleAction()).addValid(new LoginValid()).doCall();
                    }
                })
                .addItemView(history, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShort("打开浏览记录（待开发）");
                    }
                })
                .addItemView(share, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShort("打开分享记录（待开发）");
                    }
                })
                .addTo(mGroupListView);


        QMUICommonListItemView update = mGroupListView.createItemView("检查更新");
        update.setDetailText(AppUtils.getAppVersionName());

        clean = mGroupListView.createItemView("清除缓存");

        QMUICommonListItemView about = mGroupListView.createItemView("关于我们");
        about.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView openSource = mGroupListView.createItemView("开源技术");
        openSource.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUIGroupListView.newSection(getContext())
                .setTitle("设置")
                .setDescription(" ")
                .addItemView(update, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Beta.checkUpgrade(true, false);
                    }
                })
                .addItemView(clean, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        showCacheDialog();
                    }
                })
                .addItemView(about, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityUtils.startActivity(AboutActivity.class);
                    }
                })
                .addItemView(openSource, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityUtils.startActivity(OpenSourceActivity.class);
                    }
                })
                .addTo(mGroupListView);
    }

    private void showCacheDialog() {
        QMUIDialog.MessageDialogBuilder builder = new QMUIDialog.MessageDialogBuilder(mActivity);

        builder.setTitle("您确定要清除缓存吗?")
                .setMessage("缓存大小: " + getCache())
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        DataCleanManager.clearAllCache(mActivity);
                        clean.setDetailText(getCache());
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_header:
                if (GlobalParams.isLogin()) {
                    showLogout();
                } else {
                    ActivityUtils.startActivity(LoginActivity.class);
                }
                break;
        }
    }

    private void showLogout() {

        checkBoxMessageDialogBuilder = new QMUIDialog.CheckBoxMessageDialogBuilder(mActivity);

        checkBoxMessageDialogBuilder.setTitle("亲爱的 " + SPUtils.getInstance().getString(ConstantValues.NIKE_NAME) + " 您要退出登录吗?")
                .setMessage("删除账号信息")
                .setChecked(false)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "退出 ", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        GlobalParams.setIsLogin(false);
                        if (checkBoxMessageDialogBuilder.isChecked()) {
                            SPUtils.getInstance().put(ConstantValues.NIKE_NAME, "");
                        }
                        SPUtils.getInstance().put(ConstantValues.PASSWORD, "");
                        EventBus.getDefault().post(new LoginEvent());
                        OkGo.getInstance().getCookieJar().getCookieStore().removeAllCookie();
                        dialog.dismiss();
                    }
                })
                .create().show();

    }

    @Override
    protected void onFirstUserVisible() {
        clean.setDetailText(getCache());
    }

    @Override
    protected void onUserVisible() {
        clean.setDetailText(getCache());
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void destroyViewAndThing() {
        OkGo.getInstance().cancelTag(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        if (GlobalParams.isLogin()) {
            tv_name.setText(SPUtils.getInstance().getString(ConstantValues.NIKE_NAME));
        } else {
            tv_name.setText("点击头像进行登录");
        }
    }

    public String getCache() {
        try {
            return DataCleanManager.getTotalCacheSize(mActivity);
        } catch (Exception e) {
            e.printStackTrace();
            return "0 KB";
        }
    }
}
