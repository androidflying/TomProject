package com.tom.im.ui;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tom.common.RouterHub;
import com.tom.common.base.CommonActivity;
import com.tom.im.R;

import androidx.annotation.NonNull;


/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 描述：会话列表页面
 */
@Route(path = RouterHub.IM_CHAT_LIST)
public class ChatListActivity extends CommonActivity {

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
        return R.layout.activity_chat_list;
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
