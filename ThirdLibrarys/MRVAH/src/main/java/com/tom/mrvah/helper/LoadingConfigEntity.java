package com.tom.mrvah.helper;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/9/25
 * 描述：loading的全局刷新配置实体
 */
class LoadingConfigEntity {

    int count;
    boolean isHaveHeader;

    LoadingConfigEntity(boolean isHaveHeader) {
        this(0, isHaveHeader);
    }

    LoadingConfigEntity(int count) {
        this(count, false);
    }

    LoadingConfigEntity(int count, boolean isHaveHeader) {
        this.count = count;
        this.isHaveHeader = isHaveHeader;
    }
}