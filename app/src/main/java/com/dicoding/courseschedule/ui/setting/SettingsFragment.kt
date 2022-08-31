package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode
import java.util.*

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference

        val theme = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        theme!!.setOnPreferenceChangeListener { _, newValue ->
            updateTheme(NightMode.valueOf(newValue.toString().uppercase(Locale.US)).value)
            true
        }

        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference

        val reminder = DailyReminder()
        val changePref = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))

        changePref!!.setOnPreferenceChangeListener { _, newValue ->

            if(newValue==true){
                reminder.setDailyReminder(requireActivity().applicationContext)
            }else{
                reminder.cancelAlarm(requireActivity().applicationContext)
            }

            true
        }

    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}