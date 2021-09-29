package regexteam.example.regex.System

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import regexteam.example.regex.Utils.Utils

class GpsScreenDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?) = AlertDialog.Builder(requireContext())
        .setMessage(regexteam.example.regex.Utils.Utils.messageGps)
        .setTitle(regexteam.example.regex.Utils.Utils.titleGps)
        .setPositiveButton("Enable") { _, _ -> regexteam.example.regex.System.CheckingSystem().openGpsScreen(requireContext()) }
        .create()


}
