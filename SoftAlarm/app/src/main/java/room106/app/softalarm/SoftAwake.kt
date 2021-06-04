package room106.app.softalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*

class SoftAwake {

    private val requestCode = 2

    private val softAwakeRounds = 3
    private val timeIntervalMin = 2
    private val minTimeToSetUpAlarm = 1000 * 60 * 6 // One hour

    fun setUp(context: Context, alarmTime: Calendar) {
        val intent = Intent(context, SoftAwakeBroadcastReceiver::class.java)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Subtract 30 Min
        alarmTime.add(Calendar.MINUTE, -(softAwakeRounds * timeIntervalMin))

        // Fire alarm at: ??:??:00
        alarmTime.set(Calendar.SECOND, 0)

        if (alarmTime.timeInMillis - minTimeToSetUpAlarm <= System.currentTimeMillis()) {
            // Add one day to fire alarm tomorrow
            // Now time is 14:00, and alarm set to 12:00
            alarmTime.add(Calendar.DAY_OF_MONTH, 1)
            Log.d("Alarm", "SoftAwakeManager +1 DAY")
        }

        // Set up alarm with 10 minutes interval
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,                    // Alarm type
            alarmTime.timeInMillis,                     // Time
            (1000 * 60 * timeIntervalMin).toLong(),     // Interval
            alarmIntent)

        Log.d("Alarm", "SetUp SoftAwakeManager")
        SharedPreferencesManager(context)
            .setValue(SharedPreferencesManager.Key.SOFT_AWAKE_ROUNDS, softAwakeRounds)
    }

    fun cancel(context: Context) {
        Log.d("Alarm", "Canceling SoftAwake..")
        val intent = Intent(context, SoftAwakeBroadcastReceiver::class.java)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE)

        if (alarmIntent != null) {
            Log.d("Alarm", "CANCELED!")
            alarmManager.cancel(alarmIntent)
        }
    }
}