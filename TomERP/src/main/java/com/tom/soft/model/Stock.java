package com.tom.soft.model;

import cn.bmob.v3.BmobObject;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/19
 * 描述：原料表
 */
public class Stock extends BmobObject {
    private String unit;
    private String minData;
    private Integer balance;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stock() {
        this.setTableName("stock");
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMinData() {
        return minData;
    }

    public void setMinData(String minData) {
        this.minData = minData;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
