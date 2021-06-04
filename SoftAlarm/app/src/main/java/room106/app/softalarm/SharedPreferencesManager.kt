package room106.app.softalarm

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {

    private val sharedPrefName = "Soft Alarm Preferences"
    enum class Key(name: String) {
        HOURS("hours"),
        MINUTES("minutes"),
        SWITCHER("switcher"),
        SOFT_AWAKE_ROUNDS("soft_awake_rounds")
    }

    private var pref: SharedPreferences

    init {
        pref = context.getSharedPreferences(sharedPrefName, 0)
    }

    fun setValue(key: Key, value: Int) {
        val editor = pref.edit()
        editor.putInt(key.name, value)
        editor.apply()
    }

    fun getIntValue(key: Key): Int {
        return pref.getInt(key.name, -1)
    }

    var isAlarmChecked: Boolean
        get() {
            return pref.getBoolean(Key.SWITCHER.name, false)
        }
        set(newValue) {
            val editor = pref.edit()
            editor.putBoolean(Key.SWITCHER.name, newValue)
            editor.apply()
        }
}