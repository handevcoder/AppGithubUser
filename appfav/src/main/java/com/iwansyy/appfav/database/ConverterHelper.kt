package com.iwansyy.appfav.database

import android.database.Cursor
import com.iwansyy.appfav.model.User

object ConverterHelper {
    fun convertCursorToArrayList(cursor: Cursor?): ArrayList<User> {
        val users = arrayListOf<User>()
        cursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(DBContract.Column.USERNAME))
                val avatar_url = getString(getColumnIndexOrThrow(DBContract.Column.UAVATAR))
                users.add(User(login = username, avatar_url = avatar_url))
            }
        }
        return users
    }

    fun convertCursorToObject(cursor: Cursor?): User {
        var user = User()
        cursor?.apply {
            moveToFirst()
            val id = getString(getColumnIndexOrThrow(DBContract.Column.UID))
            val username = getString(getColumnIndexOrThrow(DBContract.Column.USERNAME))
            val company = getString(getColumnIndexOrThrow(DBContract.Column.UCOMPANY))
            val location = getString(getColumnIndexOrThrow(DBContract.Column.ULOCATION))
            val followews = getString(getColumnIndexOrThrow(DBContract.Column.UFOLLOWERS))
            val following = getString(getColumnIndexOrThrow(DBContract.Column.UFOLLOWING))
            val avatar_url = getString(getColumnIndexOrThrow(DBContract.Column.UAVATAR))
            user = User(id = id.toLong(),login = username, company = company, location = location,
                followers = followews.toInt(), following = following.toInt(), avatar_url = avatar_url)
        }
        return user
    }
}