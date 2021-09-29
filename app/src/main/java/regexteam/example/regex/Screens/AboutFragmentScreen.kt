package regexteam.example.regex.Screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import regexteam.example.regex.Utils.TextBlink
import kotlinx.android.synthetic.main.fragment_about.*
import regexteam.example.regex.R

class AboutFragmentScreen ():Fragment(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TextBlink().blinkingText(warning)
        aboutbackbutton.setOnClickListener{
            findNavController().navigate(R.id.action_aboutFragment_to_homeScreen)
        }
    }
}