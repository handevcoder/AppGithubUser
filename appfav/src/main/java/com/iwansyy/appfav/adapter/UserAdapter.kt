package com.iwansyy.appfav.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iwansyy.appfav.R
import com.iwansyy.appfav.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(val context: Context) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var mData = arrayListOf<User>()

    fun setUsers(users: ArrayList<User>?) {
        mData.clear()
        users?.let { mData.addAll(it) }
        notifyDataSetChanged()
    }

    fun getUser() = mData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
        .inflate(R.layout.item_user, parent, false)
    )

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.item_username.text = user.login
            with(itemView) {
                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .apply(RequestOptions.circleCropTransform().override(100,100))
                    .into(item_image)
            }
        }
    }
}