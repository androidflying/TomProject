package com.android.tomflying.adapter;

import android.support.annotation.Nullable;

import com.android.tomflying.R;
import com.android.tomflying.bean.OpenSourceBean;
import com.android.tomflying.bean.OpenSourceSectionBean;
import com.tom.brvah.BaseSectionQuickAdapter;
import com.tom.brvah.BaseViewHolder;

import java.util.List;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/8
 * 描述：
 */
public class OpenSourceAdapter extends BaseSectionQuickAdapter<OpenSourceSectionBean, BaseViewHolder> {

    public OpenSourceAdapter(@Nullable List<OpenSourceSectionBean> data) {
        super(R.layout.item_open_source, R.layout.item_head, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, OpenSourceSectionBean item) {
        helper.setText(R.id.tv_head, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, OpenSourceSectionBean item) {
        OpenSourceBean bean = item.t;
        helper.setText(R.id.tv_title, bean.getTitle());
        helper.setText(R.id.tv_desc, bean.getDesc());
        helper.setText(R.id.tv_author, bean.getAuthor());
        helper.setText(R.id.tv_version, bean.getVersion());
    }
}
