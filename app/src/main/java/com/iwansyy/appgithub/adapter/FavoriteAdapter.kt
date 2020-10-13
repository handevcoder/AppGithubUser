package com.iwansyy.appgithub.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iwansyy.appgithub.DetailsActivity
import com.iwansyy.appgithub.DetailsFavoActivity
import com.iwansyy.appgithub.R
import com.iwansyy.appgithub.model.User
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.item_user.view.*

class FavoriteAdapter(val context: Context) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private var mData = arrayListOf<User>()

    fun setData(users: ArrayList<User>?) {
        mData.clear()
        users?.let { mData.addAll(it) }
        notifyDataSetChanged()
    }

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
                    .apply(RequestOptions.circleCropTransform().override(100, 100))
                    .into(item_image)

                itemView.setOnClickListener {
                    val intent = Intent(context, DetailsFavoActivity::class.java)
                    intent.putExtra(DetailsFavoActivity.EXTRA_USER, user)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                    Log.d("msg", "${user}")
                }
            }
        }
    }
}