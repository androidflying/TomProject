package com.tom.mrvah.adapter;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/9/25
 * 描述：加载实体类的适配器
 */
public interface LoadingEntityAdapter<T> {

    T createLoadingEntity(int type, int level);

    T createLoadingHeaderEntity(int type, int level);

    /**
     * loadingEntity是复用的
     *
     * @param loadingEntity 复用entity
     * @param position      所在索引，头的索引为-1
     */
    void bindLoadingEntity(T loadingEntity, int position);
}
