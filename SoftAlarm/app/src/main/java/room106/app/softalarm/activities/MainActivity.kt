package room106.app.softalarm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import room106.app.softalarm.*

class MainActivity : AppCompatActivity() {


    // Views
    private lateinit var timeView:              TextView
    private lateinit var timeLabelView:         TextView
    private lateinit var alarmSwitch:           SwitchCompat

    private var clock: Clock? = null
    private var alarm: Alarm? = null
    private var pref: SharedPreferencesManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timeView = findViewById(R.id.time)
        timeLabelView = findViewById(R.id.time_label)
        alarmSwitch = findViewById(R.id.alarm_switch)

        findViewById<ImageButton>(R.id.time_controller_minus)
            .setOnClickListener(timeControllerListener)
        findViewById<ImageButton>(R.id.time_controller_plus)
            .setOnClickListener(timeControllerListener)

        pref = SharedPreferencesManager(this)
        clock = Clock(this)
        alarm = Alarm()
        updateTimeView()


        readSharedPreferences()
        alarmSwitch.setOnCheckedChangeListener(switchChangeListener)
    }

    private fun readSharedPreferences() {
        if (pref != null) {
            alarmSwitch.isChecked = pref!!.isAlarmChecked
        }
    }

    private val switchChangeListener = CompoundButton.OnCheckedChangeListener { v, isChecked ->
        Log.d("Alarm", "Switcher is: $isChecked")
        if (isChecked) {
            if (clock != null) {
                setUpAlarm()
            }
        } else {
            alarm?.cancel(this)
        }

        if (pref != null) {
           pref!!.isAlarmChecked = isChecked
        }
    }

    private val timeControllerListener = View.OnClickListener {
        if (it.id == R.id.time_controller_plus) {
            incrementTime(true)
        } else if (it.id == R.id.time_controller_minus) {
            incrementTime(false)
        }
    }

    private fun incrementTime(forward: Boolean) {
        clock?.incrementTime(forward)
        clock?.saveClock()

        updateTimeView()

        if (alarmSwitch.isChecked) {
            setUpAlarm()
        }
    }

    private fun updateTimeView() {
        timeView.text = clock?.time
        timeLabelView.text = clock?.label
    }

    private fun setUpAlarm() {
        alarm?.setUp(this, clock!!.getCalendar())
    }
}