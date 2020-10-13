package com.iwansyy.appgithub.api

import com.iwansyy.appgithub.model.ResponseUser
import com.iwansyy.appgithub.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GitServices {

    @GET("search/users")
    fun search(
        @Query("q") username: String,
        @Header("Authorization") auth: String
    ): Call<ResponseUser>

    @GET("users/{username}")
    fun detail(
        @Path("username") username: String,
        @Header("Authorization") auth: String
    ): Call<User>


    @GET("users/{username}/followers")
    fun followers(
        @Path("username") username: String,
        @Header("Authorization") auth: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun following(
        @Path("username") username: String,
        @Header("Authorization") auth: String
    ): Call<ArrayList<User>>

}