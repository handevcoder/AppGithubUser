package com.iwansyy.appgithub

import android.content.ContentValues
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iwansyy.appgithub.adapter.SectionsPagerAdapter
import com.iwansyy.appgithub.database.ConverterHelper
import com.iwansyy.appgithub.database.DBContract.Column.Companion.UAVATAR
import com.iwansyy.appgithub.database.DBContract.Column.Companion.UCOMPANY
import com.iwansyy.appgithub.database.DBContract.Column.Companion.UFOLLOWERS
import com.iwansyy.appgithub.database.DBContract.Column.Companion.UFOLLOWING
import com.iwansyy.appgithub.database.DBContract.Column.Companion.UID
import com.iwansyy.appgithub.database.DBContract.Column.Companion.ULOCATION
import com.iwansyy.appgithub.database.DBContract.Column.Companion.USERNAME
import com.iwansyy.appgithub.database.DBContract.Column.Companion.USER_URI
import com.iwansyy.appgithub.model.User
import com.iwansyy.appgithub.presenter.DetailPresenter
import com.iwansyy.appgithub.utils.Utils
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    private var isFavorite: Boolean = false
    private lateinit var uri: Uri
    private lateinit var tab: Array<String>

    companion object {
        const val EXTRA_USER = "extra_user"
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val userProfile = intent.getParcelableExtra<User>(EXTRA_USER) as User
        loadDetail(userProfile.login)

        tab = resources.getStringArray(R.array.tab_follow)

        isFavorite = isFavorites(userProfile.id)
        Log.d("msg", "${userProfile.id}")
        Log.d("msg", "${isFavorite}")
        setFavorite(isFavorite)

        val adapter = SectionsPagerAdapter(tab, userProfile.login, supportFragmentManager)
        view_pager.adapter = adapter
        tab_follow.setupWithViewPager(view_pager)

        uri = Uri.parse("$USER_URI")

        btn_fav.setOnClickListener {
            isFavorite = !isFavorite
            setFavorite(isFavorite)
            if (isFavorite) {
                addToFav(userProfile)
                Toast.makeText(applicationContext, "Sucsess Add Favorite", Toast.LENGTH_SHORT).show()
            } else {
                removeFromFav(userProfile.id)
                Toast.makeText(applicationContext, "Sucsess Remove", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setFavorite(favorite: Boolean) {
        var drawableImage = R.drawable.ic_star_border_blue_24dp
        if (favorite) {
            drawableImage = R.drawable.ic_star_blue_24dp
        }
        btn_fav.setBackgroundResource(drawableImage)
    }


    private fun loadDetail(username: String) {
        showLoading()
        val presenter = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailPresenter::class.java)
        presenter.detailUser(username)
        presenter.getDetailUser().observe(this, Observer {
            tv_username.text = it.name
            tv_company.text = it.company
            tv_follower.text = it.followers.toString()
            tv_following.text = it.following.toString()
            tv_location.text = it.location
            val glide = Utils.loadImageProfile(this, it.avatar_url)
            glide.into(image_user)
            hideLoading()
        })

    }

    private fun removeFromFav(uId: Long) {
        val uri = Uri.parse("$USER_URI/$uId")
        contentResolver.delete(uri, null, null)
    }

    private fun addToFav(user: User) {
        val values = ContentValues()
        values.put(UID, user.id)
        values.put(USERNAME, user.login)
        values.put(UCOMPANY, user.company)
        values.put(ULOCATION, user.location)
        values.put(UFOLLOWERS, user.followers)
        values.put(UFOLLOWING, user.following)
        values.put(UAVATAR, user.avatar_url)
        contentResolver.insert(USER_URI, values)
    }

    private fun isFavorites(uId: Long): Boolean {
        val uri = Uri.parse("$USER_URI/$uId")
        val cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor != null && cursor.count != 0) {
            ConverterHelper.convertCursorToObject(cursor)
            cursor.close()
            return true
        }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLoading() {
        progress_circular.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progress_circular.visibility = View.GONE
    }

}
