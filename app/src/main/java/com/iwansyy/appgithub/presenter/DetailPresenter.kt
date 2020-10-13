package com.iwansyy.appgithub.presenter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iwansyy.appgithub.BuildConfig
import com.iwansyy.appgithub.api.ApiGit
import com.iwansyy.appgithub.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPresenter: ViewModel() {
    private var userDetail = MutableLiveData<User>()

    fun detailUser(username: String) {

        ApiGit.create().detail(username, BuildConfig.TOKEN).enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("search", t.message.toString())
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        userDetail.postValue(it)
                    }
                }
            }
        })
    }

    fun getDetailUser(): LiveData<User> = userDetail
}