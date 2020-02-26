package com.azhara.proyekakhirdicoding.setting

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.azhara.proyekakhirdicoding.BuildConfig
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.setting.dialy.DialyReceiver
import com.azhara.proyekakhirdicoding.setting.release.ReleaseReceiver
import com.azhara.proyekakhirdicoding.ui.movies.model.MoviesModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var KEY_REMINDER: String
    private lateinit var KEY_RELEASE: String

    private lateinit var dialyReminder: SwitchPreference
    private lateinit var releaseReminder: SwitchPreference

    private lateinit var dialyReceiver: DialyReceiver
    private lateinit var releaseReceiver: ReleaseReceiver



    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        setSummaries()
        dialyReceiver = DialyReceiver()
        releaseReceiver =
            ReleaseReceiver()
    }

    private fun init() {
        KEY_REMINDER = resources.getString(R.string.key_reminder)
        KEY_RELEASE = resources.getString(R.string.key_release_movie)

        dialyReminder = findPreference<SwitchPreference>(KEY_REMINDER) as SwitchPreference
        releaseReminder = findPreference<SwitchPreference>(KEY_RELEASE) as SwitchPreference
    }

    private fun setSummaries() {
        val preferences = preferenceManager.sharedPreferences
        dialyReminder.isChecked = preferences.getBoolean(KEY_REMINDER, false)
        releaseReminder.isChecked = preferences.getBoolean(KEY_RELEASE, false)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key.equals(KEY_RELEASE)) {
            releaseReminder.isChecked = sharedPreferences.getBoolean(KEY_RELEASE, false)
            if (releaseReminder.isChecked) {
                activity?.let { releaseReceiver.setRepeatingAlarmRelease(it) }
            } else {
                activity?.let { releaseReceiver.cancelAlarm(it) }
            }
        }

        if (key.equals(KEY_REMINDER)) {
            dialyReminder.isChecked = sharedPreferences.getBoolean(KEY_REMINDER, false)
            if (dialyReminder.isChecked) {
                activity?.let { dialyReceiver.setRepetingAlarm(it) }
            } else {
                activity?.let { dialyReceiver.cancelAlarm(it) }
            }
        }
    }

}