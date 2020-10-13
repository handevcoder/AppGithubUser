package com.iwansyy.appfav.utils

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import java.util.*

object Utils {

    fun loadImageProfile(context: Context, avatar: String?): RequestBuilder<Drawable> {
        return Glide.with(context)
            .load(avatar)
            .apply(RequestOptions.circleCropTransform().override(240, 240))
    }
}