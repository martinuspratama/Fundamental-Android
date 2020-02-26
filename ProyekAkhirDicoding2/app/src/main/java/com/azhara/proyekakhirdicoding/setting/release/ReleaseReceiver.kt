package com.azhara.proyekakhirdicoding.setting.release

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.azhara.proyekakhirdicoding.BuildConfig
import com.azhara.proyekakhirdicoding.MainActivity
import com.azhara.proyekakhirdicoding.R
import com.azhara.proyekakhirdicoding.setting.SettingFragment
import com.azhara.proyekakhirdicoding.ui.movies.model.MoviesModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ReleaseReceiver : BroadcastReceiver() {
    var idNotification = 0

    companion object {
        private const val API_KEY = BuildConfig.MOVIEDB_API_KEY
    }

    override fun onReceive(context: Context, intent: Intent) {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date()
        val RELEASE_NOW = simpleDateFormat.format(date)

        getReleaseMovies(RELEASE_NOW, context)

    }

    private fun notification(context: Context, title: String, message: String, notifId: Int) {
        val CHANNEL_ID = "channel_id"
        val CHANNEL_NAME = "channel_name"

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setContentIntent(pendingIntent)
            .setSound(alarmSound)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setAutoCancel(true)


        /*
        Untuk android Oreo ke atas perlu menambahkan notification channel
        Materi ini akan dibahas lebih lanjut di modul extended
         */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(CHANNEL_ID)

            notificationManager.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManager.notify(notifId, notification)
    }

    fun setRepeatingAlarmRelease(context: Context) {

        var delay = 0


        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, ReleaseReceiver::class.java)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                idNotification, intent, 0
            )
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            delay + calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toast.makeText(context, "Dialy release movie on", Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReleaseReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            idNotification, intent, 0
        )
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Dialy release movie off", Toast.LENGTH_SHORT).show()
    }

    private fun getReleaseMovies(date: String, context: Context) {
        val messageTitle = context.resources.getString(R.string.title_release)
        val listItems = ArrayList<MoviesModel>()
        val TAG = ReleaseReceiver::class.java.simpleName
        val client = AsyncHttpClient()
        val url =
            "https://api.themoviedb.org/3/discover/movie?api_key=${API_KEY}&primary_release_date.gte=$date&primary_release_date.lte=$date"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                val responseObject = JSONObject(result)
                val listRelease = responseObject.getJSONArray("results")
                for (i in 0 until listRelease.length()) {
                    val releaseMovies = listRelease.getJSONObject(i)
                    val moviesItems = MoviesModel()

                    moviesItems.title = releaseMovies.getString("title")

                    listItems.add(moviesItems)
                }
                for (movies in listItems){
                    val title = movies.title.toString()
                    idNotification++
                    notification(context, title, "$title $messageTitle", idNotification)
                }



            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d(TAG, "onFailure: $error")
            }

        })
    }
}
