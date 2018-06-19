package com.android.tomflying.adapter;

import com.android.tomflying.R;
import com.android.tomflying.bean.SearchBean;
import com.android.tomflying.bean.SearchKeyBean;
import com.android.tomflying.bean.SearchKeySectionBean;
import com.tom.brvah.BaseSectionQuickAdapter;
import com.tom.brvah.BaseViewHolder;

import java.util.List;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/31
 * 描述：
 */
public class SearchKeyAdapter extends BaseSectionQuickAdapter<SearchKeySectionBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public SearchKeyAdapter(List<SearchKeySectionBean> data) {
        super(R.layout.item_search_key, R.layout.item_search_tips, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SearchKeySectionBean item) {
        helper.setText(R.id.tv_tips, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchKeySectionBean item) {
        SearchBean bean = item.t;
        helper.setText(R.id.tv_key, bean.getName());

    }
}
