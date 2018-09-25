package com.tom.mrvah.entity;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/9/25
 * 描述： 多类型实体类接口
 */
public interface MultiTypeEntity {

    /**
     * itemType标志数据的类型
     * @return int
     */
    int getItemType();

    /**
     * id标志不同数据项(唯一标识)
     * @return long
     */
    long getId();
}