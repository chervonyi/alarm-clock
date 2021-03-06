package room106.app.softalarm.views

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import room106.app.softalarm.R

class SoftAwakeButton: androidx.appcompat.widget.AppCompatImageButton {

    //region Constructors
    constructor(context: Context) : super(context) {
        initializeView(context)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        initializeView(context)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        initializeView(context)
    }
    //endregion

    private fun initializeView(context: Context) {
        setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_feather))
        scaleType = ScaleType.FIT_CENTER

        val padding = resources.getDimensionPixelOffset(R.dimen.soft_awake_button_padding)
        setPadding(padding, padding, padding, padding)

        background = ContextCompat.getDrawable(context, R.drawable.soft_awake_button)

        isCheckedSoftAwake = false
    }

    private var _isChecked = false
    var isCheckedSoftAwake: Boolean
        get() = _isChecked
        set(newValue) {
            _isChecked = newValue
            isSelected = newValue
        }
}