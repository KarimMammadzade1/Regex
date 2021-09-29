package regexteam.example.regex.Utils

import com.google.android.gms.maps.model.LatLng

object Utils {
    const val BaseUrlWeather = "https://api.openweathermap.org/"
    const val BaseUrlMaps = "https://maps.googleapis.com/maps/"
    val cordBaku = LatLng(40.409264, 49.867092)
    val cordSumqayit = LatLng(40.58972, 49.66861)
    const val titleGps = "GPS Disabled"
    const val messageGps = "Please Enable GPS and Let Us Locate You"
    const val titlePermission = "Location Permission"
    const val messagePermission = "We need to access your exact location in order to help you!"
    const val rationale = ""

    var myWeatherList: MutableList<String>? = null
    var rideInfoList: List<String>? = null
    var calcData:List<Int>? =null


}