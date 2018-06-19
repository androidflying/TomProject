package com.android.tomflying.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/31
 * 描述：
 */
@Entity
public class SearchBean {

    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String name;
    private long time;


    @Generated(hash = 526502625)
    public SearchBean(Long id, String name, long time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    @Generated(hash = 562045751)
    public SearchBean() {
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
