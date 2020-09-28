package room106.app.softalarm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import room106.app.softalarm.R
import room106.app.softalarm.Snooze

class AlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
    }

    fun onClickSnooze(v: View) {
        // Set up one-time alarm to fire in 5 minutes
        Snooze().setUp(this)
        finish()
    }

    fun onClickStop(v: View) {
        finish()
    }
}