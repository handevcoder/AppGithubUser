package com.iwansyy.appgithub.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.iwansyy.appgithub.MainActivity
import com.iwansyy.appgithub.R
import java.util.*

class Notifications: BroadcastReceiver() {
    private val CHANNEL_ID = "appgithub"
    private val CHANNEL_NAME = "user_github"

    companion object{
        const val NOTIF_TYPE = "notif_type"
    }
    override fun onReceive(context: Context, intent: Intent) {
        val type =  intent.getStringExtra(NOTIF_TYPE)
        val dailyType = context.resources.getString(R.string.daily_key)
        if (type == dailyType){
            dailyNotify(context)
        }
    }

    val dailyBroadcastID = 12

    private fun dailyNotify(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)


        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_event_note_black_24dp)
            .setContentTitle(context.resources.getString(R.string.app_name))
            .setContentText(context.resources.getString(R.string.daily_notif_text))
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManager.notify(dailyBroadcastID, notification)
    }
}