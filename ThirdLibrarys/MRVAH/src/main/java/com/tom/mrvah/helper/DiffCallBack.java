package com.tom.mrvah.helper;

import android.support.v7.util.DiffUtil;

import com.tom.mrvah.entity.MultiTypeEntity;

import java.util.List;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/9/25
 * 描述：关于新老数据比较的callback，暂时不提供出去
 *      如果你有自己的比较逻辑，大可自己实现一个，实现接口{@link RecyclerViewAdapterHelper#getDiffCallBack(List, List)}
 */
class DiffCallBack<T extends MultiTypeEntity> extends DiffUtil.Callback {

    private List<T> mOldDatas;
    private List<T> mNewDatas;

    DiffCallBack(List<T> mOldDatas, List<T> mNewDatas) {
        this.mOldDatas = mOldDatas;
        this.mNewDatas = mNewDatas;
    }

    @Override
    public int getOldListSize() {
        return mOldDatas == null ? 0 : mOldDatas.size();
    }

    @Override
    public int getNewListSize() {
        return mNewDatas == null ? 0 : mNewDatas.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        T oldItem = mOldDatas.get(oldItemPosition);
        T newItem = mNewDatas.get(newItemPosition);
        return !(oldItem == null || newItem == null) && oldItem.getItemType() == newItem.getItemType();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T oldItem = mOldDatas.get(oldItemPosition);
        T newItem = mNewDatas.get(newItemPosition);
        return oldItem.getId() == newItem.getId();
    }
}
