package com.android.tomflying.valid;

import com.android.tomflying.ui.ArticleCollectedActivity;
import com.tom.baselib.delay.Action;
import com.tom.baselib.utils.ActivityUtils;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/17
 * 描述：
 */
public class ArticleAction implements Action {
    @Override
    public void call() {
        ActivityUtils.startActivity(ArticleCollectedActivity.class);
    }
}
