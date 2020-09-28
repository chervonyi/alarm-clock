package room106.app.softalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log

class Snooze {

    private val snoozeTimeMinutes = 5 * 60 * 1000

    fun setUp(context: Context) {
        val intent = Intent(context, SnoozeBroadcastReceiver::class.java)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Fire one-time alarm in 5 minutes
        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + snoozeTimeMinutes,
            alarmIntent
        )
        Log.d("Alarm", "Set up Snooze for 5 minutes")
    }
}