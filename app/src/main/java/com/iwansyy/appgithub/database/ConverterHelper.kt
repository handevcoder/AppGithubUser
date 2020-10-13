package com.iwansyy.appgithub.database

import android.database.Cursor
import com.iwansyy.appgithub.model.User

object ConverterHelper {
    fun convertCursorToArrayList(cursor: Cursor?): ArrayList<User> {
        val users = arrayListOf<User>()
        cursor?.apply {
            while (moveToNext()) {
                val id = getString(getColumnIndexOrThrow(DBContract.Column.UID))
                val userName = getString(getColumnIndexOrThrow(DBContract.Column.USERNAME))
                val avatarUrl = getString(getColumnIndexOrThrow(DBContract.Column.UAVATAR))
                users.add(User(id = id.toLong(), login = userName, avatar_url = avatarUrl))
            }
        }
        return users
    }

    fun convertCursorToObject(cursor: Cursor?): User {
        var user = User()
        cursor?.apply {
            moveToFirst()
            val id = getString(getColumnIndexOrThrow(DBContract.Column.UID))
            val userName = getString(getColumnIndexOrThrow(DBContract.Column.USERNAME))
            val company = getString(getColumnIndexOrThrow(DBContract.Column.UCOMPANY))
            val location = getString(getColumnIndexOrThrow(DBContract.Column.ULOCATION))
            val followews = getString(getColumnIndexOrThrow(DBContract.Column.UFOLLOWERS))
            val following = getString(getColumnIndexOrThrow(DBContract.Column.UFOLLOWING))
            val avatarUrl = getString(getColumnIndexOrThrow(DBContract.Column.UAVATAR))
            user = User(id = id.toLong(),login = userName, company = company, location = location,
                followers = followews.toInt(), following = following.toInt(), avatar_url = avatarUrl)
        }
        return user
    }
}