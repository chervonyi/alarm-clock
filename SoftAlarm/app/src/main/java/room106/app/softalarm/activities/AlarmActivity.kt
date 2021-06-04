package room106.app.softalarm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import room106.app.softalarm.*

class AlarmActivity : AppCompatActivity() {

    private var prefs: SharedPreferencesManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        prefs = SharedPreferencesManager(this)

        if (intent.getIntExtra(PURPOSE_NAME, ALARM_PURPOSE) == SOFT_AWAKE_PURPOSE) {
            findViewById<TextView>(R.id.alarm_title).text = getString(R.string.soft_awake)
            findViewById<TextView>(R.id.snooze_button).visibility = View.GONE

            Log.d("Alarm", "It's Soft Awake purpose!")

            // Decrement Soft Awake rounds count
            var softAwakeRounds = prefs?.getIntValue(SharedPreferencesManager.Key.SOFT_AWAKE_ROUNDS)
            if (softAwakeRounds != null && softAwakeRounds > 0) {
                softAwakeRounds -= 1

                Log.d("Alarm", "softAwakeRounds: $softAwakeRounds")
                prefs?.setValue(SharedPreferencesManager.Key.SOFT_AWAKE_ROUNDS, softAwakeRounds)

                if (softAwakeRounds == 0) {
                    // Cancel SoftAwake Alarm
                    SoftAwake().cancel(this)
                }
            }
        }
    }

    fun onClickSnooze(v: View) {
        // Set up one-time alarm to fire in 5 minutes
        Snooze().setUp(this)
        finish()
    }

    fun onClickStop(v: View) {
        if (intent.getIntExtra(PURPOSE_NAME, ALARM_PURPOSE) == SOFT_AWAKE_PURPOSE) {
            // TODO - Maybe I should add cancellation before???

            val clock = Clock(this)
            Alarm().setUp(this, clock.getCalendar())
            SoftAwake().setUp(this, clock.getCalendar())
        }

        finish()
    }

    companion object {
        const val SOFT_AWAKE_PURPOSE = 1001
        const val ALARM_PURPOSE = 1002
        const val PURPOSE_NAME = "purpose"
    }
}