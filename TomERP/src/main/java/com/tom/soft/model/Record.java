package com.tom.soft.model;

import cn.bmob.v3.BmobObject;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/19
 * 描述：操作记录
 */
public class Record extends BmobObject {

    private TomUser user;
    private Stock stock;
    private String operation;
    private Integer num;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Record() {
        this.setTableName("record");
    }


    public TomUser getUser() {
        return user;
    }

    public void setUser(TomUser user) {
        this.user = user;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
