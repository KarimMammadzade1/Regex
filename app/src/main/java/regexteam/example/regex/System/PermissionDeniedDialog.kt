package regexteam.example.regex.System

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import android.content.Intent
import android.net.Uri
import regexteam.example.regex.Utils.Utils

class PermissionDeniedDialog:DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?):Dialog=
        AlertDialog.Builder(requireContext())
            .setMessage(regexteam.example.regex.Utils.Utils.messagePermission)
            .setTitle(regexteam.example.regex.Utils.Utils.titlePermission)
            .setPositiveButton("Enable") { _,_ ->openSettings(requireContext()) }
            .setNegativeButton("Disable") { _,_ -> activity?.finishAffinity() }
            .create()

    private fun openSettings(context: Context) {
        val intent=Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
        val uri= Uri.fromParts("package","com.example.ridaper",null)
        intent.data = uri
        context.startActivity(intent)

    }
}


