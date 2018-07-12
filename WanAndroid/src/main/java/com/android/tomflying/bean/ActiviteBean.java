package com.android.tomflying.bean;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/7/12
 * 描述：
 */
public class ActiviteBean {
    private String thing;
    private String good;
    private String bad;
    private boolean isWeek;

    public String getThing() {
        return thing;
    }

    public void setThing(String thing) {
        this.thing = thing;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public String getBad() {
        return bad;
    }

    public void setBad(String bad) {
        this.bad = bad;
    }

    public boolean isWeek() {
        return isWeek;
    }

    public void setWeek(boolean week) {
        isWeek = week;
    }
}
