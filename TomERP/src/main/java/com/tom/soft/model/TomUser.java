package com.tom.soft.model;

import cn.bmob.v3.BmobUser;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/19
 * 描述：
 */
public class TomUser extends BmobUser {
    private String realName;
    private String workId;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
