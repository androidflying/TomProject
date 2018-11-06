package com.hub.welfare.adapter;

import android.widget.TextView;

import com.hub.welfare.R;
import com.hub.welfare.model.JokeBean;
import com.tom.brvah.BaseQuickAdapter;
import com.tom.brvah.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/2
 * 描述：
 */
public class JokeAdapter extends BaseQuickAdapter<JokeBean.Result.Data, BaseViewHolder> {
    public JokeAdapter(@Nullable List<JokeBean.Result.Data> data) {
        super(R.layout.item_joke, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokeBean.Result.Data item) {
        helper.setText(R.id.tv_update, "更新时间：" + item.getUpdatetime());
        ((TextView) helper.itemView.findViewById(R.id.tv_content)).setText(HtmlCompat.fromHtml(item.getContent(), HtmlCompat.FROM_HTML_MODE_LEGACY));
    }
}
