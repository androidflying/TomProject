package com.android.tomflying.bean;

import com.tom.brvah.entity.SectionEntity;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/17
 * 描述：
 */
public class SearchKeySectionBean extends SectionEntity<SearchBean> {

    public SearchKeySectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SearchKeySectionBean(SearchBean searchKeyBean) {
        super(searchKeyBean);
    }
}
