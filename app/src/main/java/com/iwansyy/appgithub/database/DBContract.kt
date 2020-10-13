package com.iwansyy.appgithub.database

import android.net.Uri
import android.provider.BaseColumns

object DBContract {
    const val AUTH = "com.iwansyy.appgithub"
    const val SCHEME = "content"

    class Column: BaseColumns{
        companion object{
            const val TABLE_NAME = "user_github"
            const val UID = "id"
            const val USERNAME = "username"
            const val UCOMPANY = "company"
            const val ULOCATION = "location"
            const val UFOLLOWERS = "followers"
            const val UFOLLOWING = "following"
            const val UAVATAR = "avatar_url"

            val USER_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTH)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}