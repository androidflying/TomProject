package com.tom.mrvah.entity;

import java.util.List;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/9/25
 * 描述：存储单个level数据实体，尽量不要在这里修改数据
 */
public final class LevelData<T> {

    private List<T> data;
    private T header;
    private T footer;

    public LevelData(List<T> data, T header, T footer) {
        this.data = data;
        this.header = header;
        this.footer = footer;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public T getHeader() {
        return header;
    }

    public void removeHeader() {
        header = null;
    }

    public void setHeader(T newHeader) {
        header = newHeader;
    }

    public T getFooter() {
        return footer;
    }

    public void removeFooter() {
        footer = null;
    }

    public void setFooter(T newFooter) {
        footer = newFooter;
    }
}
