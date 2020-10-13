package com.iwansyy.appgithub.api


import com.iwansyy.appgithub.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiGit {
    var BASE_URL = BuildConfig.BASE_URL

    private fun getInterceptor(): OkHttpClient {
        val mClient = HttpLoggingInterceptor()
        mClient.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().addInterceptor(mClient)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }
    fun create(): GitServices{
        val retrofit = Retrofit.Builder()
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(GitServices::class.java)
    }

}