package regexteam.example.regex.Utils

import android.graphics.Color
import android.widget.TextView

import android.view.MotionEvent
import android.view.View


class TouchListener : View.OnTouchListener {
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> (view as TextView).setTextColor(-0x1) // white
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> (view as TextView).setTextColor(
                Color.parseColor("#FFD600")
            )
        }
        return false
    }
}