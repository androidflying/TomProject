package com.android.tomflying.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;

import com.android.tomflying.R;
import com.android.tomflying.bean.ArticlesBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.tom.baselib.utils.TimeUtils;
import com.tom.brvah.BaseQuickAdapter;
import com.tom.brvah.BaseViewHolder;

import java.util.List;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/11
 * 描述：
 */
public class ProjectAdapter extends BaseQuickAdapter<ArticlesBean.Data, BaseViewHolder> {
    private ImageView iv_pic;

    public ProjectAdapter(@Nullable List<ArticlesBean.Data> datas) {
        super(R.layout.item_project, datas);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticlesBean.Data item) {

        helper.setText(R.id.tv_date, TimeUtils.getFriendlyTimeSpanByNow(item.getPublishTime()));
        helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle()));
        helper.setText(R.id.tv_desc, item.getDesc());
        iv_pic = helper.itemView.findViewById(R.id.iv_pic);
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .error(R.drawable.no_banner)
                .placeholder(R.drawable.no_banner)
                .priority(Priority.NORMAL);
        Glide.with(helper.itemView.getContext())
                .load(item.getEnvelopePic())
                .apply(requestOptions)
                .into(iv_pic);

        if (item.getEnvelopePic().endsWith(".gif")) {
            helper.itemView.findViewById(R.id.tv_gif).setVisibility(View.VISIBLE);
        } else {
            helper.itemView.findViewById(R.id.tv_gif).setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_author, "作者：" + item.getAuthor());

        helper.setImageResource(R.id.iv_favorite, item.isCollect() ? R.mipmap.fav_no : R.mipmap.fav);
        helper.addOnClickListener(R.id.iv_favorite);

    }
}
