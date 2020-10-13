package com.iwansyy.appgithub.presenter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iwansyy.appgithub.BuildConfig
import com.iwansyy.appgithub.BuildConfig.TOKEN
import com.iwansyy.appgithub.api.ApiGit
import com.iwansyy.appgithub.api.GitServices
import com.iwansyy.appgithub.model.ResponseUser
import com.iwansyy.appgithub.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserPresenter : ViewModel() {
    private var userList = MutableLiveData<ArrayList<User>>()
    private var usersLoad = MutableLiveData<Boolean>()

    fun searchUser(username: String) {
        ApiGit.create().search(username, BuildConfig.TOKEN).enqueue(object :
            Callback<ResponseUser> {
            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Log.e("search", t.message.toString())
            }

            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if (response.isSuccessful) {
                    executeUsers(response.body())
                }
            }
        })
    }

    fun followersUser(username: String) {
        ApiGit.create().followers(username, TOKEN).enqueue(object : Callback<ArrayList<User>> {
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.e("search", t.message.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<User>>, response: Response<ArrayList<User>>
            ) {
                executeFollow(response.body())
            }
        })
    }

    fun followingUser(username: String) {

        ApiGit.create().following(username, TOKEN).enqueue(object : Callback<ArrayList<User>> {
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.e("search", t.message.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<User>>, response: Response<ArrayList<User>>
            ) {
                executeFollow(response.body())
            }
        })
    }

    private fun executeUsers(users: ResponseUser?) {
        users?.let { userList.postValue(it.items) }
    }

    private fun executeFollow(users: ArrayList<User>?) {
        users?.let {
            userList.postValue(it)
            usersLoad.postValue(true)
        }
    }

    fun getListUser(): LiveData<ArrayList<User>> = userList
    fun usersLoad(): LiveData<Boolean> = usersLoad

}