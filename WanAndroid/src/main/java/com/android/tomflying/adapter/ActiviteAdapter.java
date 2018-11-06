package com.android.tomflying.adapter;

import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.android.tomflying.R;
import com.tom.brvah.BaseQuickAdapter;
import com.tom.brvah.BaseViewHolder;

import java.util.List;
import java.util.Map;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/7/13
 * 描述：
 */
public class ActiviteAdapter extends BaseQuickAdapter<Map<String, String>, BaseViewHolder> {
    public ActiviteAdapter(@Nullable List<Map<String, String>> data) {
        super(R.layout.item_activite, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, String> item) {

        helper.setText(R.id.tv_thing, "● " + item.get("thing"));
        if (TextUtils.isEmpty(item.get("result"))) {
            helper.itemView.findViewById(R.id.tv_result).setVisibility(View.GONE);
        } else {
            helper.itemView.findViewById(R.id.tv_result).setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.tv_result, item.get("result"));

    }
}
