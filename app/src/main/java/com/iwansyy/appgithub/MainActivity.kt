package com.iwansyy.appgithub

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.iwansyy.appgithub.adapter.UserAdapter
import com.iwansyy.appgithub.presenter.UserPresenter
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progress_circular

class MainActivity : AppCompatActivity(){
    private lateinit var adapter: UserAdapter

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = UserAdapter(applicationContext)
        adapter.notifyDataSetChanged()

        rv_user.adapter = adapter
        rv_user.setHasFixedSize(true)
        rv_user.layoutManager = LinearLayoutManager(applicationContext)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_user)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    return true
                } else {
                    searchUser(query)
                    progress_circular.visibility = View.VISIBLE
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    private fun searchUser(username: String) {
        val apiPresenter = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserPresenter::class.java)
        apiPresenter.searchUser(username)
        apiPresenter.getListUser().observe(this, Observer {
            adapter.setData(it)
            progress_circular.visibility = View.GONE
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> startActivity(Intent(this, FavoriteActivity::class.java))
            R.id.settings -> startActivity(Intent(this, Settings::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}


