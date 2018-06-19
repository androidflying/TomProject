package com.android.tomflying.bean;

import com.tom.brvah.entity.SectionEntity;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/17
 * 描述：
 */
public class OpenSourceSectionBean extends SectionEntity<OpenSourceBean> {

    public OpenSourceSectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public OpenSourceSectionBean(OpenSourceBean openSourceBean) {
        super(openSourceBean);
    }
}
