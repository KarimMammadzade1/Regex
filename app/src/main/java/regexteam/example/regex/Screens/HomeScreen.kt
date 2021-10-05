package regexteam.example.regex.Screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import regexteam.example.regex.MyViewModel
import regexteam.example.regex.R
import regexteam.example.regex.Utils.Utils.calcData
import regexteam.example.regex.Utils.Utils.myWeatherList
import regexteam.example.regex.Utils.Utils.rideInfoList
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import kotlinx.android.synthetic.main.fragment_home_screen.*
import java.text.DecimalFormat
import java.util.ArrayList

import android.widget.LinearLayout

import kotlin.math.roundToInt

import com.google.android.material.bottomsheet.BottomSheetDialog
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.lifecycleScope
import regexteam.example.regex.System.CheckingSystem
import regexteam.example.regex.System.GpsScreenDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt


class HomeScreen() : Fragment() {
    private lateinit var mViewModel: MyViewModel
    private var backCount = 0
    private lateinit var sharedPreferences:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this)[MyViewModel::class.java]
        sharedPreferences=requireActivity().getSharedPreferences("mypref",Context.MODE_PRIVATE)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(requireContext(),"Press once again in order to exit",Toast.LENGTH_LONG).show()
            backCount += 1
            if (backCount == 2) activity?.finish()
        }

    }
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        checkGps()
        if (myWeatherList != null) showWeatherInfo()
        if (rideInfoList != null) showRideInfo()
        if (calcData != null) showFinalPriceInfo()
        goMapsButton.setOnClickListener { findNavController().navigate(R.id.fromHometoMaps) }
        settingsbutton.setOnClickListener { showBottomSheet() }

    boltEco.setOnClickListener {
        LeavingAppFragmentDialog().show(childFragmentManager,"LA")
        LeavingAppFragmentDialog.onClickListener = {
            if(it){ regexteam.example.regex.Utils.OpenApp().openBoltApp(requireContext()) } } }

    boltXl.setOnClickListener {
        LeavingAppFragmentDialog().show(childFragmentManager,"LA")
        LeavingAppFragmentDialog.onClickListener={
            if (it){ regexteam.example.regex.Utils.OpenApp().openBoltApp(requireContext())} } }

    ekonomTaksi.setOnClickListener {
        LeavingAppFragmentDialog().show(childFragmentManager,"LA")
LeavingAppFragmentDialog.onClickListener={if (it){regexteam.example.regex.Utils.OpenApp().openEkonomApp(requireContext())} }                                                              }

    maksimTaksi.setOnClickListener {
        LeavingAppFragmentDialog().show(childFragmentManager,"LA")
        LeavingAppFragmentDialog.onClickListener={if (it){regexteam.example.regex.Utils.OpenApp().openMakimTaksiApp(requireContext()) } } }

    yuzTaksi.setOnClickListener {
        LeavingAppFragmentDialog().show(childFragmentManager,"LA")
          LeavingAppFragmentDialog.onClickListener={if (it){ regexteam.example.regex.Utils.OpenApp().open189TaksiApp(requireContext())}}}

    uberX.setOnClickListener {
        LeavingAppFragmentDialog().show(childFragmentManager,"LA")
     LeavingAppFragmentDialog.onClickListener={if (it){  regexteam.example.regex.Utils.OpenApp().openUberApp(requireContext())} }
    }

    inCorrectEstimates.setOnClickListener { regexteam.example.regex.Utils.OpenApp().openMail(requireContext()) }
    inCorrectEstimates.setOnTouchListener(regexteam.example.regex.Utils.TouchListener())

           }

    private  fun showButtonInfo() {
        sharedPreferences.edit().putBoolean("entered",true).apply()
        MaterialTapTargetPrompt.Builder(this).setTarget(R.id.goMapsButton).setPrimaryText("Map Button").setSecondaryText("Tap to move to Maps!").showFor(3000)
        Handler(Looper.myLooper()!!).postDelayed({
        MaterialTapTargetPrompt.Builder(this).setTarget(R.id.settingsbutton).setPrimaryText("Settings Button").setSecondaryText("Tap to open Settings!").showFor(4000)
        },3000)
    }


    private fun checkGps() {
        if (!CheckingSystem().checkGps(requireContext())) {
            GpsScreenDialog().show(childFragmentManager, "TT")
        }
    }

    private fun checkPermission() {
        Permissions.check(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION,
            null,
            object : PermissionHandler() {
                override fun onGranted() {
                   // Toast.makeText(context, "Granted", Toast.LENGTH_LONG).show()
                    if (!sharedPreferences.getBoolean("entered",false)){showButtonInfo()}

                    lifecycleScope.launch (Dispatchers.Main){
                        hintText?.let {hintText->
                            delay(2000)
                            hintText.visibility=View.VISIBLE
                            regexteam.example.regex.Utils.TextBlink().blinkingText(hintText)
                            delay(4000)
                            hintText.visibility=View.GONE  }
                    }

                }

                override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
                    super.onDenied(context, deniedPermissions)
                    regexteam.example.regex.System.PermissionDeniedDialog()
                        .show(childFragmentManager, "T")
                   // Toast.makeText(context, "Denied", Toast.LENGTH_LONG).show()
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun showWeatherInfo() {

  val temp:Int=myWeatherList!![0].toDouble().roundToInt()
        weatherAdviceText.text =
            "It's " + temp + "\u2103 outside\nAnd magic word about weather is:" + myWeatherList!![2]
    weatherFinal.visibility=View.VISIBLE
        if (myWeatherList!![2] == "Rain"|| myWeatherList!![2]=="Drizzle") {weatherFinal.text="Don't Forget To Take Your Umbrella."}
        if(myWeatherList!![2] == "Thunderstorm") {weatherFinal.text="You Should Stay Home."}
        if(myWeatherList!![2] == "Snow"||myWeatherList!![2] == "Fog") {weatherFinal.text="Take Your Scarf"}
        if(myWeatherList!![2] == "Thunderstorm") {weatherFinal.text="You Should Stay Home."}
        if(myWeatherList!![2] == "Clouds"||myWeatherList!![2] == "Clear") {weatherFinal.text="Go Out and Enjoy The Weather!"}
    }

    private fun showRideInfo() {
        homeStartX.isSelected=true
        homeEndX.isSelected=true
        val distance = rideInfoList!![0]
        homeDistanceX.text = distance
        val duration = rideInfoList!![1]
        homeDurationX.text = duration
        val startAdres = rideInfoList!![2]
        homeStartX.text = startAdres
       // Log.d("data", startAdres)
        val endAdres = rideInfoList!![3]
        homeEndX.text = endAdres
       // Log.d("data", endAdres)
    }

    private fun showFinalPriceInfo() {
        object : CountDownTimer(5000, 25) {
            override fun onTick(millisUntilFinished: Long) {
                myScroll.scrollTo((2500 - millisUntilFinished).toInt(), 0) }
            override fun onFinish() {} }.start()
        val finalDistance = calcData!![0] / 1000
        val finalDuration = calcData!![1] / 60
        val df = DecimalFormat("0.00")

        val yuzTaksi = regexteam.example.regex.Utils.RidePrices()
            .get189TaxiPrice(finalDistance, finalDuration)
        val boltEco = regexteam.example.regex.Utils.RidePrices()
            .getBoltEcoPrice(finalDistance, finalDuration)
        val boltXl = regexteam.example.regex.Utils.RidePrices()
            .getBoltXlPrice(finalDistance, finalDuration)
        val uber = regexteam.example.regex.Utils.RidePrices()
            .getUberXPrice(finalDistance, finalDuration)
        val ekonom = regexteam.example.regex.Utils.RidePrices()
            .getEconomTaxiPrice(finalDistance, finalDuration)
        val maksim = regexteam.example.regex.Utils.RidePrices()
            .getMaximTaxiPrice(finalDistance, finalDuration)
        yuztaksiPrice.text = df.format(yuzTaksi) + " ₼"
        boltPrice.text = df.format(boltEco) + " ₼"
        boltXlPrice.text=df.format(boltXl)+" ₼"
        uberPrice.text=df.format(uber)+" ₼"
        ekonomPrice.text=df.format(ekonom)+" ₼"
        maximPrice.text=df.format(maksim)+" ₼"

        inCorrectEstimates.visibility=View.VISIBLE
        }

    private fun showBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.layoutmodalbottom)

        val contactUsM = bottomSheetDialog.findViewById<LinearLayout>(R.id.contactUsB)
        val shareUsM = bottomSheetDialog.findViewById<LinearLayout>(R.id.shareUsB)
        val faqM = bottomSheetDialog.findViewById<LinearLayout>(R.id.faqB)
        val changeLangM = bottomSheetDialog.findViewById<LinearLayout>(R.id.changeLanguageB)
        val aboutUsM = bottomSheetDialog.findViewById<LinearLayout>(R.id.aboutUsB)

        bottomSheetDialog.show()

        contactUsM!!.setOnClickListener {
            regexteam.example.regex.Utils.OpenApp().openMail(requireContext())
            bottomSheetDialog.dismiss()
        }

        shareUsM!!.setOnClickListener {
            //Toast.makeText(requireContext(), "Share is Clicked", Toast.LENGTH_LONG).show()
            regexteam.example.regex.SettingsSection.ShareUsButton().openPlayStore(requireContext())
            bottomSheetDialog.dismiss()
        }

        faqM!!.setOnClickListener {
           // Toast.makeText(requireContext(), " ", Toast.LENGTH_LONG).show()
            regexteam.example.regex.SettingsSection.FAQFragment().show(childFragmentManager,"FA")
            bottomSheetDialog.dismiss()
        }

        changeLangM!!.setOnClickListener {
            Toast.makeText(requireContext(), "Not Available Yet!", Toast.LENGTH_LONG).show()
            //bottomSheetDialog.dismiss()
        }

        aboutUsM!!.setOnClickListener {
           findNavController().navigate(R.id.action_homeScreen_to_aboutFragment)
            bottomSheetDialog.dismiss()
        }
    }

    }



