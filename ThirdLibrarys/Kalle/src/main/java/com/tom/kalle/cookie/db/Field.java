package com.tom.kalle.cookie.db;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
public interface Field {
    String TABLE_NAME = "COOKIES_TABLE";

    String ID = "_ID";
    String URL = "URL";
    String NAME = "NAME";
    String VALUE = "VALUE";
    String COMMENT = "COMMENT";
    String COMMENT_URL = "COMMENT_URL";
    String DISCARD = "DISCARD";
    String DOMAIN = "DOMAIN";
    String EXPIRY = "EXPIRY";
    String PATH = "PATH";
    String PORT_LIST = "PORT_LIST";
    String SECURE = "SECURE";
    String VERSION = "VERSION";
}