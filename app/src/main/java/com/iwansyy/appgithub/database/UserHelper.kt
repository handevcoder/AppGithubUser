package com.iwansyy.appgithub.database


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.iwansyy.appgithub.database.DBContract.Column.Companion.TABLE_NAME
import com.iwansyy.appgithub.database.DBContract.Column.Companion.UID

class UserHelper(applicationContext: Context) {
    private val dbHelper = DatabaseHelper(applicationContext)
    private lateinit var db: SQLiteDatabase

    companion object {
        private const val DB_TABLE = TABLE_NAME
        private var INSTANCE: UserHelper? = null

        fun getInstance(context: Context): UserHelper = INSTANCE ?: synchronized(this) {
            INSTANCE ?: UserHelper(context)
        }
    }

    fun open() {
        db = dbHelper.writableDatabase
    }

    fun queryAll(): Cursor = db.query(DB_TABLE, null, null, null, null, null, null, null)
    fun queryByUsername(uId: String): Cursor =
        db.query(DB_TABLE, null, "$UID = ?", arrayOf(uId), null, null, null)

    fun save(values: ContentValues?): Long = db.insert(DB_TABLE, null, values)
    fun deleteByUsername(uId: String): Int = db.delete(DB_TABLE, "$UID = $uId", null)

}