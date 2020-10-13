package com.iwansyy.appgithub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iwansyy.appgithub.R
import com.iwansyy.appgithub.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class FollowAdapter(val context: Context): RecyclerView.Adapter<FollowAdapter.ViewHolder>(){
    private var mData = arrayListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    fun setData(items: ArrayList<User>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(usr: User) {
            itemView.item_username.text = usr.login
            with(itemView){
                Glide.with(itemView.context)
                    .load(usr.avatar_url)
                    .apply(RequestOptions.circleCropTransform().override(100,100))
                    .into(item_image)
            }
        }

    }
}