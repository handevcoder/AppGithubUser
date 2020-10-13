package com.iwansyy.appfav

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.iwansyy.appfav.adapter.UserAdapter
import com.iwansyy.appfav.database.ConverterHelper
import com.iwansyy.appfav.database.DBContract.Column.Companion.USER_URI
import com.iwansyy.appfav.model.User
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var adapter: UserAdapter

    companion object {
        const val EXTRA_STATE = "extra_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        adapter = UserAdapter(this)
        rv_fav.adapter = adapter
        rv_fav.setHasFixedSize(true)

        rv_fav.layoutManager = LinearLayoutManager(this)
        val handlerFav = HandlerThread("favorite")
        handlerFav.start()
        val handler = Handler(handlerFav.looper)

        val observer = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) { loadUsers() }
        }

        contentResolver.registerContentObserver(USER_URI, true, observer)

        if (savedInstanceState == null){
            loadUsers()
        }else{
            val list = savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)
            if (!list.isNullOrEmpty()){
                adapter.setUsers(list)
            }
        }
    }

    private fun loadUsers() {
        GlobalScope.launch(Dispatchers.Main){
            val db = async(Dispatchers.IO){
                val cursor = contentResolver?.query(USER_URI, null, null, null,null)
                ConverterHelper.convertCursorToArrayList(cursor)
            }
            val users = db.await()
            if (users.size > 0){
                adapter.setUsers(users)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getUser())
    }

}
