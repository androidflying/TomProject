package com.hub.welfare.adapter;

import com.hub.welfare.R;
import com.hub.welfare.model.CategoryEntity;
import com.tom.brvah.BaseQuickAdapter;
import com.tom.brvah.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/7
 * 描述：
 */
public class CategoryAdapter extends BaseQuickAdapter<CategoryEntity, BaseViewHolder> {
    public CategoryAdapter(@Nullable List<CategoryEntity> data) {
        super(R.layout.item_category, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryEntity item) {
        if (item.isChecked()) {
            helper.setText(R.id.tv_cate_name, item.getName() + "xuanze");
        } else {
            helper.setText(R.id.tv_cate_name, item.getName());
        }
    }
}
