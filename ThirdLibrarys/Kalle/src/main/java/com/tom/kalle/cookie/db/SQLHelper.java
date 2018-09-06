package com.tom.kalle.cookie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/9/6
 * 描述：
 */
final class SQLHelper extends SQLiteOpenHelper implements Field {

    private static final String DB_COOKIE_NAME = "_kalle_cookies_db.db";
    private static final int DB_COOKIE_VERSION = 3;

    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " " + URL + " TEXT, " + NAME + " TEXT, " + VALUE + " TEXT, " + COMMENT + " TEXT, " + COMMENT_URL + " TEXT, " + DISCARD + " TEXT," +
            " " + DOMAIN + " TEXT, " + EXPIRY + " INTEGER, " + PATH + " TEXT, " + PORT_LIST + " TEXT, " + SECURE + " TEXT, " + VERSION + " INTEGER)";
    private static final String SQL_CREATE_UNIQUE_INDEX = "CREATE UNIQUE INDEX COOKIE_UNIQUE_INDEX ON COOKIES_TABLE(\"" + NAME + "\", \"" + DOMAIN + "\", \"" + PATH + "\")";
    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    SQLHelper(Context context) {
        super(context.getApplicationContext(), DB_COOKIE_NAME, null, DB_COOKIE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(SQL_CREATE_TABLE);
            db.execSQL(SQL_CREATE_UNIQUE_INDEX);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            db.beginTransaction();
            try {
                db.execSQL(SQL_DELETE_TABLE);
                db.execSQL(SQL_CREATE_TABLE);
                db.execSQL(SQL_CREATE_UNIQUE_INDEX);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }
}
