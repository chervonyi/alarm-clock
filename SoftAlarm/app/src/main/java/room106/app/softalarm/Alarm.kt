package room106.app.softalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*

class Alarm {

    private val minTimeToFireAlarm = 1000 * 60 * 2

    fun setUp(context: Context, alarmTime: Calendar) {

        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Fire alarm at: ??:??:00
        alarmTime.set(Calendar.SECOND, 0)

        if (alarmTime.timeInMillis - minTimeToFireAlarm <= System.currentTimeMillis()) {
            // Add one day to fire alarm tomorrow
            // Now time is 14:00, and alarm set to 12:00
//            alarmTime.add(Calendar.DAY_OF_MONTH, 1)

            // TODO - Remove later FOR TEST:
            alarmTime.add(Calendar.MINUTE, 5) // 5 minutes like 1 day

            Log.d("Alarm", "Fire alarm tomorrow")
        }

        Log.d("Alarm", "Alarm time: ${alarmTime.time}")

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,        // Alarm type
            alarmTime.timeInMillis,          // Time
            1000 * 60 * 60,      // Interval
            alarmIntent)                    // Pending Intent
    }

    fun cancel(context: Context) {
        Log.d("Alarm", "Cancel alarm!")
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE)

        if (alarmIntent != null) {
            Log.d("Alarm", "CANCELED!")
            alarmManager.cancel(alarmIntent)
        }
    }
}