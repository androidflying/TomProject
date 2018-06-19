package com.android.tomflying.dao;

import android.database.sqlite.SQLiteDatabase;

import com.tom.baselib.utils.Utils;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/31
 * 描述：
 */
public class DBHelper {
    private static DBHelper helper;
    private DaoSession session;
    private SQLiteDatabase db;

    private DBHelper(String dbName) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(Utils.getApp(), dbName, null);
        db = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        session = daoMaster.newSession();
    }

    public static DBHelper getDao() {
        if (helper == null) {
            helper = new DBHelper("tomflying");
        }
        return helper;
    }

    public DaoSession getSession() {
        return session;
    }
}
