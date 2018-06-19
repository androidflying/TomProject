package com.tom.brvah.provider;

import android.content.Context;

import com.tom.brvah.BaseViewHolder;

import java.util.List;

public abstract class BaseItemProvider<T, V extends BaseViewHolder> {

    public Context mContext;
    public List<T> mData;

    //子类须重写该方法返回viewType

    public abstract int viewType();

    //子类须重写该方法返回layout

    public abstract int layout();

    public abstract void convert(V helper, T data, int position);

    //子类若想实现条目点击事件则重写该方法

    public void onClick(V helper, T data, int position) {
    }

    //子类若想实现条目长按事件则重写该方法

    public boolean onLongClick(V helper, T data, int position) {
        return false;
    }
}
