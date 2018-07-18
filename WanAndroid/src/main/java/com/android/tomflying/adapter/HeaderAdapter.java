package com.android.tomflying.adapter;

import android.support.annotation.Nullable;

import com.android.tomflying.R;
import com.android.tomflying.bean.TypeBean;
import com.tom.brvah.BaseQuickAdapter;
import com.tom.brvah.BaseViewHolder;

import java.util.List;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/7/12
 * 描述：
 */
public class HeaderAdapter extends BaseQuickAdapter<TypeBean, BaseViewHolder> {

    public HeaderAdapter(@Nullable List<TypeBean> data) {
        super(R.layout.item_main_head, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, TypeBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setBackgroundRes(R.id.icon, item.getResId());
    }

}
