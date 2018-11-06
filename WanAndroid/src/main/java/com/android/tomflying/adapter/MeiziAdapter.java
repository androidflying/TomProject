package com.android.tomflying.adapter;

import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.android.tomflying.R;
import com.android.tomflying.bean.MeiziBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
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
public class MeiziAdapter extends BaseQuickAdapter<MeiziBean.Results, BaseViewHolder> {
    public MeiziAdapter(@Nullable List<MeiziBean.Results> data) {
        super(R.layout.item_meizi, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeiziBean.Results item) {
        ImageView iv_meizi = helper.itemView.findViewById(R.id.iv_meizi);

        RequestOptions requestOptions = new RequestOptions()
                .centerInside()
                .error(R.drawable.no_banner)
                .placeholder(R.drawable.no_banner)
                .priority(Priority.NORMAL);
        Glide.with(helper.itemView.getContext())
                .load(item.getUrl())
                .apply(requestOptions)
                .into(iv_meizi);
    }

}
