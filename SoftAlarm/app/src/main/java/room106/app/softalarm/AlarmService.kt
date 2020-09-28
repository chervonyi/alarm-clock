package room106.app.softalarm

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder


class AlarmService: Service() {

    private var broadcastReceiver: BroadcastReceiver? = null

    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        registerScreenOffReceiver()
    }

    override fun onDestroy() {
        unregisterReceiver(broadcastReceiver)
    }

    private fun registerScreenOffReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                // do something, e.g. send Intent to main app
            }
        }
        val filter = IntentFilter(Intent.ACTION_SCREEN_OFF)
        registerReceiver(broadcastReceiver, filter)
    }
}