package regexteam.example.regex.Screens

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class LeavingAppFragmentDialog():DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Leaving Regex")
            .setNegativeButton("Stay here") { _, _ -> onClickListener?.invoke(false) }
            .setPositiveButton("Let's Go") { _, _ -> onClickListener?.invoke(true) }
            .setMessage("You are about to open relevant Taxi App or it's Play Store Page ")
            .create()
    }

    companion object{
        var onClickListener: ((Boolean)->(Unit))? = null
    }
}

