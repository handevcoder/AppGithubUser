package com.iwansyy.appgithub

import android.content.Intent
import android.content.res.Configuration
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.iwansyy.appgithub.adapter.FavoriteAdapter
import com.iwansyy.appgithub.database.ConverterHelper
import com.iwansyy.appgithub.database.DBContract.Column.Companion.USER_URI
import com.iwansyy.appgithub.model.User
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var adapter: FavoriteAdapter
    companion object {
        const val EXTRA_STATE = "extra_state"
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = FavoriteAdapter(this)
        rv_fav.adapter = adapter
        rv_fav.setHasFixedSize(true)

        rv_fav.layoutManager = LinearLayoutManager(this)
        val handlerFav = HandlerThread("favorite")
        handlerFav.start()
        val handler = Handler(handlerFav.looper)

        val observer = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadUsers()
            }
        }

        contentResolver.registerContentObserver(USER_URI, true, observer)

        if (savedInstanceState == null) {
            loadUsers()
        } else {
            val list = savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)
            if (!list.isNullOrEmpty()) {
                adapter.setData(list)
                Log.d("msg", "$list")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadUsers() {
        GlobalScope.launch(Dispatchers.Main) {
            val db = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(USER_URI, null, null, null, null)
                ConverterHelper.convertCursorToArrayList(cursor)
            }
            val users = db.await()
            if (users.size > 0) {
                adapter.setData(users)
            }
        }
    }



}
