package room106.app.softalarm

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import room106.app.softalarm.activities.AlarmActivity

class SoftAwakeBroadcastReceiver: BroadcastReceiver()  {

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d("Alarm", "onReceive - SoftAwake")
        val alarmIntent = Intent(context, AlarmActivity::class.java)
        alarmIntent.putExtra(AlarmActivity.PURPOSE_NAME, AlarmActivity.SOFT_AWAKE_PURPOSE)
        context?.startActivity(alarmIntent)
    }
}