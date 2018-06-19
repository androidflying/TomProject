package com.android.tomflying.adapter;

import android.support.annotation.Nullable;
import android.text.Html;

import com.android.tomflying.R;
import com.android.tomflying.bean.CollectArticleBean;
import com.tom.baselib.utils.TimeUtils;
import com.tom.brvah.BaseQuickAdapter;
import com.tom.brvah.BaseViewHolder;

import java.util.List;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/8
 * 描述：
 */
public class CollectArticleAdapter extends BaseQuickAdapter<CollectArticleBean.Data, BaseViewHolder> {

    public CollectArticleAdapter(@Nullable List<CollectArticleBean.Data> data) {
        super(R.layout.item_collect_article, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectArticleBean.Data item) {
        helper.setText(R.id.tv_date, TimeUtils.getFriendlyTimeSpanByNow(item.getPublishTime()));
        helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle()));
        helper.setText(R.id.tv_author, "作者：" + item.getAuthor());

        helper.setText(R.id.tv_cate, item.getChapterName());
    }
}
