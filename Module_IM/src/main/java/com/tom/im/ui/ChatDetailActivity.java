package com.tom.im.ui;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.tom.im.R;
import com.tom.common.base.CommonActivity;
import com.tom.common.RouterHub;


/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 描述：聊天页面
 */
@Route(path = RouterHub.IM_CHAT_DETAIL)
public class ChatDetailActivity extends CommonActivity {

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

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_chat_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        ARouter.getInstance().inject(this);

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

}
