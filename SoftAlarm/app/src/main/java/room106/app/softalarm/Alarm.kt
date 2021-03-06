package room106.app.softalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*

class Alarm {

    private val requestCode = 0
    private val minTimeToSetUpAlarm = 1000 * 60 * 10 // One hour
    private val interval: Long = 1000 * 60 * 60

    fun setUp(context: Context, alarmTime: Calendar) {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Fire alarm at: ??:??:00
        alarmTime.set(Calendar.SECOND, 0)

        if (alarmTime.timeInMillis - minTimeToSetUpAlarm <= System.currentTimeMillis()) {
            // Add one day to fire alarm tomorrow
            // Now time is 14:00, and alarm set to 12:00
            Log.d("Alarm", "AlarmManager +1 DAY")
            alarmTime.add(Calendar.DAY_OF_MONTH, 1)
        }

        Log.d("Alarm", "SetUp AlarmManager at: ${alarmTime.time}")

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,        // Alarm type
            alarmTime.timeInMillis,         // Time
            interval,                       // Interval
            alarmIntent)                    // Pending Intent
    }

    fun cancel(context: Context) {
        Log.d("Alarm", "Canceling AlarmManager..")
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE)

        if (alarmIntent != null) {
            Log.d("Alarm", "CANCELED!")
            alarmManager.cancel(alarmIntent)
        }
    }
}