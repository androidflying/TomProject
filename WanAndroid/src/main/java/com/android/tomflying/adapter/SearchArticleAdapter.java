package com.android.tomflying.adapter;

import androidx.annotation.Nullable;
import android.text.Html;

import com.android.tomflying.R;
import com.android.tomflying.bean.ArticlesBean;
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
public class SearchArticleAdapter extends BaseQuickAdapter<ArticlesBean.Data, BaseViewHolder> {

    public SearchArticleAdapter(@Nullable List<ArticlesBean.Data> data) {
        super(R.layout.item_search_article, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticlesBean.Data item) {
        helper.setText(R.id.tv_date, TimeUtils.getFriendlyTimeSpanByNow(item.getPublishTime()));
        helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle()));
        helper.setText(R.id.tv_author, "作者：" + item.getAuthor());
    }
}
