package regexteam.example.regex.Utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import java.lang.Exception


class OpenApp {
    fun openMail(context: Context) {
        try {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "teamregexx@gmail.com"))
            startActivity(context, intent, null)
        } catch (e: Exception) {
            Toast.makeText(context, "Sorry...You don't have any mail app", Toast.LENGTH_SHORT)
                .show()
            e.printStackTrace()
        }

    }

    fun openBoltApp(context: Context) {
        try {
            val launchIntent = context.packageManager.getLaunchIntentForPackage("ee.mtakso.client")
            startActivity(context, launchIntent!!, null)
        } catch (e: Exception) {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=ee.mtakso.client"))
            startActivity(context, intent, null)
        }

    }

    fun openUberApp(context: Context) {
        try {
            val launchIntent = context.packageManager.getLaunchIntentForPackage("com.ubercab")
            startActivity(context, launchIntent!!, null)
        } catch (e: Exception) {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.ubercab"))
            startActivity(context, intent, null)
        }

    }

    fun open189TaksiApp(context: Context) {
        try {
            val launchIntent = context.packageManager.getLaunchIntentForPackage("az.baku189taxi")
            startActivity(context, launchIntent!!, null)
        } catch (e: Exception) {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=az.baku189taxi"))
            startActivity(context, intent, null)
        }
    }

    fun openEkonomApp(context: Context) {
        try {
            val launchIntent = context.packageManager.getLaunchIntentForPackage("com.ekonom.taxi")
            startActivity(context, launchIntent!!, null)
        } catch (e: Exception) {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.ekonom.taxi"))
            startActivity(context, intent, null)
        }
    }

    fun openMakimTaksiApp(context: Context) {
        try {
            val launchIntent = context.packageManager.getLaunchIntentForPackage("com.taxsee.taxsee")
            startActivity(context, launchIntent!!, null)
        } catch (e: Exception) {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.taxsee.taxsee"))
            startActivity(context, intent, null)
        }
    }
}