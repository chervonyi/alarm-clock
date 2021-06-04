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
import room106.app.softalarm.views.SoftAwakeButton

class MainActivity : AppCompatActivity() {

    // Views
    private lateinit var timeView:              TextView
    private lateinit var timeLabelView:         TextView
    private lateinit var alarmSwitch:           SwitchCompat
    private lateinit var softAwakeButton: SoftAwakeButton

    private var clock: Clock? = null
    private var alarm: Alarm? = null
    private var softAwake: SoftAwake? = null
    private var pref: SharedPreferencesManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timeView = findViewById(R.id.time)
        timeLabelView = findViewById(R.id.time_label)
        alarmSwitch = findViewById(R.id.alarm_switch)
        softAwakeButton = findViewById(R.id.soft_awake_button)

        findViewById<ImageButton>(R.id.time_controller_minus)
            .setOnClickListener(timeControllerListener)
        findViewById<ImageButton>(R.id.time_controller_plus)
            .setOnClickListener(timeControllerListener)

        pref = SharedPreferencesManager(this)
        clock = Clock(this)
        alarm = Alarm()
        softAwake = SoftAwake()
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
        if (isChecked) {
            setUpAlarm()
        } else {
            alarm?.cancel(this)
            onClickSoftAwakeButton(null)
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

        // If alarm is on -> update current AlarmManager
        if (alarmSwitch.isChecked) {
            setUpAlarm()
        }
    }

    private fun updateTimeView() {
        timeView.text = clock?.time
        timeLabelView.text = clock?.label
    }

    private fun setUpAlarm() {
        if (clock != null) {
            alarm?.setUp(this, clock!!.getCalendar())
        }
    }

    fun onClickSoftAwakeButton(v: View?) {
        softAwakeButton.isCheckedSoftAwake = !softAwakeButton.isCheckedSoftAwake

        // If SoftAwake is on -> turn the main alarm too
        if (softAwakeButton.isCheckedSoftAwake) {
            alarmSwitch.isChecked = true
        }

        // Set up or Cancel SoftAwake
        if(softAwakeButton.isCheckedSoftAwake) {
            softAwake?.setUp(this, clock!!.getCalendar())
        } else {
            softAwake?.cancel(this)
        }
    }
}