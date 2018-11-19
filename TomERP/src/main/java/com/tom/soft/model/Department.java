package com.tom.soft.model;

import cn.bmob.v3.BmobObject;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/19
 * 描述：
 */
public class Department extends BmobObject {

    public Department() {
        this.setTableName("department");
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
