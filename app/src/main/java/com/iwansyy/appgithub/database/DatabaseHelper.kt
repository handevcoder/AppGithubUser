package com.iwansyy.appgithub.database


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(applicationContext: Context) :
    SQLiteOpenHelper(applicationContext, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "usergithub"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE =
            "CREATE TABLE ${DBContract.Column.TABLE_NAME}" +
                    "(${DBContract.Column.UID} INTEGER, " +
                    "${DBContract.Column.USERNAME} TEXT," +
                    "${DBContract.Column.UCOMPANY} TEXT," +
                    "${DBContract.Column.ULOCATION} TEXT, " +
                    "${DBContract.Column.UFOLLOWERS} INTEGER, " +
                    "${DBContract.Column.UFOLLOWING} INTEGER, " +
                    "${DBContract.Column.UAVATAR} TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DBContract.Column.TABLE_NAME}")
        onCreate(db)
    }
}