package com.iwansyy.appgithub.provider

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.iwansyy.appgithub.database.DBContract.AUTH
import com.iwansyy.appgithub.database.DBContract.Column.Companion.TABLE_NAME
import com.iwansyy.appgithub.database.DBContract.Column.Companion.USER_URI
import com.iwansyy.appgithub.database.UserHelper

class UserProvider : ContentProvider() {
    companion object {
        private const val USER = 1
        private const val USERNAME = 2
        private lateinit var userHelper: UserHelper
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTH, TABLE_NAME, USER)
            uriMatcher.addURI(AUTH, "$TABLE_NAME/#", USERNAME)
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (USER) {
            uriMatcher.match(uri) -> userHelper.save(values)
        }
        context?.contentResolver?.notifyChange(USER_URI, null)
        return Uri.parse("$USER_URI/${values?.get("user_id")}")
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when (uriMatcher.match(uri)) {
            USER -> cursor = userHelper.queryAll()
            USERNAME -> cursor = userHelper.queryByUsername(uri.lastPathSegment.toString())
            else -> cursor = null
        }
        return cursor
    }

    override fun onCreate(): Boolean {
        userHelper = UserHelper.getInstance(context as Context)
        userHelper.open()
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val remove: Long = when (USERNAME) {
            uriMatcher.match(uri) -> userHelper.deleteByUsername(uri.lastPathSegment.toString())
            else -> 0
        }.toLong()
        context?.contentResolver?.notifyChange(USER_URI, null)
        return remove.toInt()
    }

    override fun getType(uri: Uri): String? = null
}