package regexteam.example.regex.SettingsSection

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class FAQFragment:DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?)=AlertDialog.Builder(requireContext())
        .setTitle("Frequently Asked Questions")
        .setMessage("1.How to use?\nFirstly give all permissions Regex needs please.Without permissions we can't help you.\nAfter that move to maps screen,select destination point and tap proceed button.\n2.How do you calculate fares?\nWe calculate taxi fares based on common experimental rides and online web researches.\nFinally we geting together all informations and offer you approximate price ")
        .create()
}