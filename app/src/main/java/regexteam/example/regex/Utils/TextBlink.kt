package regexteam.example.regex.Utils

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

class TextBlink {
     fun blinkingText(view: View){
        val animation= AlphaAnimation(0.0f,1.0f)
        animation.duration=500
        animation.startOffset=20
        animation.repeatMode= Animation.REVERSE
        animation.repeatCount= Animation.INFINITE
        view.startAnimation(animation)

    }
}