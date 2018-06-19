package com.tom.network.db;

/**
 * ================================================
 * 描    述：表字段的属性
 * ================================================
 */
public class ColumnEntity {
    /**
     * 列的名字
     */
    public String columnName;
    /**
     * 列的类型
     */
    public String columnType;
    /**
     * 复合主键
     */
    public String[] compositePrimaryKey;
    /**
     * 是否是主键
     */
    public boolean isPrimary;
    /**
     * 是否不能为空
     */
    public boolean isNotNull;
    /**
     * AUTOINCREMENT 是否自增
     */
    public boolean isAutoincrement;

    /**
     * @param compositePrimaryKey 复合主键
     */
    public ColumnEntity(String... compositePrimaryKey) {
        this.compositePrimaryKey = compositePrimaryKey;
    }

    /**
     * @param columnName 列名
     * @param columnType 列的数据类型
     */
    public ColumnEntity(String columnName, String columnType) {
        this(columnName, columnType, false, false, false);
    }

    /**
     * @param columnName 列名
     * @param columnType 列的数据类型
     * @param isPrimary  是否为主键
     * @param isNotNull  是否不能为空
     */
    public ColumnEntity(String columnName, String columnType, boolean isPrimary, boolean isNotNull) {
        this(columnName, columnType, isPrimary, isNotNull, false);
    }

    /**
     * @param columnName      列名
     * @param columnType      列的数据类型
     * @param isPrimary       是否为主键
     * @param isNotNull       是否不能为空
     * @param isAutoincrement 是否自增
     */
    public ColumnEntity(String columnName, String columnType, boolean isPrimary, boolean isNotNull, boolean isAutoincrement) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.isPrimary = isPrimary;
        this.isNotNull = isNotNull;
        this.isAutoincrement = isAutoincrement;
    }
}
