package com.hub.welfare.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/11/7
 * 描述：
 */
@Entity
public class CategoryEntity {
    private String value;
    private String name;
    private String type;
    private boolean isChecked;

    @Generated(hash = 451528419)
    public CategoryEntity(String value, String name, String type,
                          boolean isChecked) {
        this.value = value;
        this.name = name;
        this.type = type;
        this.isChecked = isChecked;
    }

    @Generated(hash = 725894750)
    public CategoryEntity() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getIsChecked() {
        return this.isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
