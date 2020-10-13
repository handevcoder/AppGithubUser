package com.iwansyy.appgithub.fragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.iwansyy.appgithub.R
import com.iwansyy.appgithub.utils.Notifications
import com.iwansyy.appgithub.utils.Notifications.Companion.NOTIF_TYPE

import java.util.*


@Suppress("SameParameterValue")
class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var dailyPreferences: SwitchPreferenceCompat
    private lateinit var releasePreferences: SwitchPreferenceCompat
    private lateinit var appContext: Context
    private lateinit var dailyNotify: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appContext = context
    }

    val dailyBroadcastID = 12

    fun getCalendar(hour: Int, minute: Int, second: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, second)
        return calendar
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        dailyNotify = appContext.resources.getString(R.string.daily_key)
        dailyPreferences = findPreference<Preference>(dailyNotify) as SwitchPreferenceCompat
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when(key){
            dailyNotify -> {
                if (dailyPreferences.isChecked){
                    val calendar = getCalendar(9,0,0)
                    enableNotif(dailyNotify, dailyBroadcastID, calendar)
                }else{
                    disableNotify(dailyBroadcastID)
                }
            }
        }
    }

    private fun disableNotify(broadcastID: Int) {
        val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(appContext, Notifications::class.java)
        val pendingIntent = PendingIntent.getBroadcast(appContext, broadcastID, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    private fun enableNotif(type: String, broadcastID: Int, time: Calendar) {
        val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(appContext, Notifications::class.java)
        intent.putExtra(NOTIF_TYPE, type)
        val pendingIntent = PendingIntent.getBroadcast(appContext, broadcastID, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

}