package regexteam.example.regex.SettingsSection

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity


class ShareUsButton(){
    fun openPlayStore(context: Context){

        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=regexteam.example.regex"))
            startActivity(context,intent,null)
        } catch (ex: Exception) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps"))
            startActivity(context,intent,null)
        }

    }

}