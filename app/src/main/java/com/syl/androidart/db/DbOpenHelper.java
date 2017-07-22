package com.syl.androidart.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bright on 2017/7/22.
 *
 * @Describe 数据库帮助类
 * @Called
 */

public class DbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "book_provider.db";
    public static final String BOOK_TABLE_NAME = "book";
    public static final String USER_TABLE_NAME = "user";
    private static final int DB_VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //"create table if not exists "临近后面的引号的地方记得要空一格,否则会报异常,数据库表创建失败
        String CREATE_BOOK_TABLE = "create table if not exists " + BOOK_TABLE_NAME + "(_id integer primary key," + "name text)";
        db.execSQL(CREATE_BOOK_TABLE);
        String CREATE_USER_TABLE = "create table if not exists " + USER_TABLE_NAME + "(_id integer primary key," + "name text," + "sex int)";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
