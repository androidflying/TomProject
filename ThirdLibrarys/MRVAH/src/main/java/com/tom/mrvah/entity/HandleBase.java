package com.tom.mrvah.entity;

import java.util.List;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/9/25
 * 描述：给异步AdapterHelper封装实体类
 */
public final class HandleBase<T> {
    private List<T> newData;
    private T header;
    private T footer;
    private int refreshType;
    private int level;

    public HandleBase(List<T> newData, T header, T footer, int level, int refreshType) {
        this.newData = newData;
        this.header = header;
        this.footer = footer;
        this.level = level;
        this.refreshType = refreshType;
    }

    public List<T> getNewData() {
        return newData;
    }

    public T getNewHeader() {
        return header;
    }

    public T getNewFooter() {
        return footer;
    }

    public int getLevel() {
        return level;
    }

    public int getRefreshType() {
        return refreshType;
    }
}
