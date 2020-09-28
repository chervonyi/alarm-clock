package room106.app.softalarm

import android.annotation.SuppressLint
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

class Clock(context: Context) {

    private val calendar = Calendar.getInstance()
    private var pref = SharedPreferencesManager(context)
    private val timeStep = 1

    private val hoursByDefault = 10
    private val minutesByDefault = 30

    init {
        // Read time from SharedPreferences
        var hours = pref.getIntValue(SharedPreferencesManager.Key.HOURS)
        var minutes = pref.getIntValue(SharedPreferencesManager.Key.MINUTES)

        if (hours == -1) {
            hours = hoursByDefault
        }
        if (minutes == -1) {
            minutes = minutesByDefault
        }
        calendar.set(Calendar.HOUR_OF_DAY, hours)
        calendar.set(Calendar.MINUTE, minutes)
    }

    var time: String
        @SuppressLint("SimpleDateFormat")
        get() {
            val format = SimpleDateFormat("h:mm")
            return format.format(calendar.time)
        }
        private set(value) {}

    var label: String
        get() {
            return if (calendar.get(Calendar.AM_PM) == Calendar.AM) {
                "AM"
            } else {
                "PM"
            }
        }
        private set(value) {}

    fun incrementTime(forward: Boolean) {
        if (forward) {
            calendar.add(Calendar.MINUTE, timeStep)
        } else {
            calendar.add(Calendar.MINUTE, -timeStep)
        }
    }

    fun saveClock() {
        pref.setValue(SharedPreferencesManager.Key.HOURS, calendar.get(Calendar.HOUR_OF_DAY))
        pref.setValue(SharedPreferencesManager.Key.MINUTES, calendar.get(Calendar.MINUTE))
    }

    fun getCalendar(): Calendar {
        return calendar
    }
}