package com.android.tomflying.adapter;

import android.os.Bundle;
import android.view.View;

import com.android.tomflying.R;
import com.android.tomflying.bean.GuideBean;
import com.android.tomflying.ui.CategoryActivity;
import com.qmuiteam.tom.widget.roundwidget.QMUIRoundButton;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.brvah.BaseMultiItemQuickAdapter;
import com.tom.brvah.BaseViewHolder;
import com.tom.brvah.entity.MultiItemEntity;

import java.util.List;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/9
 * 描述：
 */
public class GuideAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public GuideAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_guide_level0);
        addItemType(TYPE_LEVEL_1, R.layout.item_guide_level1);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                final GuideBean level_0 = (GuideBean) item;
                helper.setText(R.id.tv_name, level_0.getName())
                        .setImageResource(R.id.iv, level_0.isExpanded() ? R.mipmap.icon_up : R.mipmap.icon_down);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (level_0.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                if (helper.getAdapterPosition() > 1) {
                    helper.itemView.findViewById(R.id.view_top).setVisibility(View.VISIBLE);
                } else {
                    helper.itemView.findViewById(R.id.view_top).setVisibility(View.GONE);
                }

                break;
            case TYPE_LEVEL_1:
                final GuideBean.Children level_1 = (GuideBean.Children) item;
                ((QMUIRoundButton) helper.itemView.findViewById(R.id.btn_children)).setText(level_1.getName());

                helper.itemView.findViewById(R.id.btn_children).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(CategoryActivity.CNAME, level_1.getName());
                        bundle.putInt(CategoryActivity.CID, level_1.getId());
                        ActivityUtils.startActivity(bundle, CategoryActivity.class);
                    }
                });
                break;
            default:
        }
    }
}
