package room106.app.softalarm.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.LinearLayoutCompat
import room106.app.softalarm.R

class HintBoxView : LinearLayoutCompat {

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
        View.inflate(context, R.layout.hint_box_layout, this)

        findViewById<ImageButton>(R.id.close).setOnClickListener {
            // TODO - Implement
        }
    }
}