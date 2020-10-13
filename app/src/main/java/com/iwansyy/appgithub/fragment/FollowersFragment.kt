package com.iwansyy.appgithub.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.iwansyy.appgithub.R
import com.iwansyy.appgithub.adapter.FollowAdapter
import com.iwansyy.appgithub.presenter.UserPresenter
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment : Fragment() {
    private lateinit var appContext: Context
    lateinit var adapter: FollowAdapter
    var followers: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appContext = context
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FollowAdapter(appContext)
        adapter.notifyDataSetChanged()
        followers?.let { loadFollowers(it) }
        rv_user.adapter = adapter
        rv_user.setHasFixedSize(true)
        rv_user.addItemDecoration(DividerItemDecoration(rv_user.context, DividerItemDecoration.VERTICAL))
        rv_user.layoutManager = LinearLayoutManager(appContext)

    }

    private fun loadFollowers(username: String) {
        showLoading()
        val presenter = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserPresenter::class.java)
        presenter.followersUser(username)
        presenter.getListUser().observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
            hideLoading()
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    fun showLoading(){
        progress_circular.visibility = View.VISIBLE
    }
    fun hideLoading(){
        progress_circular.visibility = View.GONE
    }

}
