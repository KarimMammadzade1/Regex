package regexteam.example.regex

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import regexteam.regex.Networking.NetworkService
import regexteam.regex.Networking.ServiceBuilder.retrofitPoly
import regexteam.regex.Networking.ServiceBuilder.retrofitWeather
import retrofit2.Call
import retrofit2.Response

class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val _weatherInfo = MutableLiveData<MutableList<String>>()
    val weatherInfo: LiveData<MutableList<String>> = _weatherInfo

    fun getWeatherInfo(latidude: String, longitude: String) {
        val service = retrofitWeather.create(NetworkService::class.java)
        val call = service.getWeatherInfo(latidude, longitude)
        call.enqueue(object : retrofit2.Callback<regexteam.example.regex.Model.WeatherModel> {
            override fun onResponse(call: Call<regexteam.example.regex.Model.WeatherModel>, response: Response<regexteam.example.regex.Model.WeatherModel>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    val temp = responseData?.main?.temp.toString()
                    val desc = responseData?.weather?.get(0)?.description.toString()
                    val mainDesc = responseData?.weather?.get(0)?.main.toString()
                    val list = mutableListOf(temp, desc, mainDesc)
                    _weatherInfo.value = list
                   // Log.d("RESPONSE", list.toString())
                }
            }

            override fun onFailure(call: Call<regexteam.example.regex.Model.WeatherModel>, t: Throwable) {
                Log.d("Fail Message", t.message.toString())
            }

        })


    }


    private val _userLocation = MutableLiveData<LatLng>()
    val userLocation: LiveData<LatLng> = _userLocation

    @SuppressLint("MissingPermission")
    fun getUserLocation(context: Context) {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        val mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setSmallestDisplacement(10F) //getnumupdates
            .setInterval(1000)
            .setFastestInterval(10000)
        lateinit var locationCallback: LocationCallback

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val userLoc = LatLng(
                    locationResult.lastLocation.latitude,
                    locationResult.lastLocation.longitude
                )
                _userLocation.value = userLoc
               // Log.d("PROBLEM", "VALUE HAS SET")
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(
            mLocationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
        //fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }


    private val _polyDirectionsInfo = MutableLiveData<MutableList<String>>()
    val polyDirectionsInfo: LiveData<MutableList<String>> = _polyDirectionsInfo

    fun getPolyDirections(oLoc: LatLng, dLoc: LatLng) {
        val oLocation = oLoc.latitude.toString() + "," + oLoc.longitude.toString()
        val dLocation = dLoc.latitude.toString() + "," + dLoc.longitude.toString()

        val service = retrofitPoly.create(NetworkService::class.java)
        val call = service.getPolyDirections(oLocation, dLocation)
        call.enqueue(object : retrofit2.Callback<regexteam.example.regex.Model.PolyDirections> {
            override fun onResponse(call: Call<regexteam.example.regex.Model.PolyDirections>, response: Response<regexteam.example.regex.Model.PolyDirections>) {
                val responseData = response.body()
            responseData.let {
                val polyline=responseData!!.routes[0].overview_polyline.points
                val distance= responseData.routes[0].legs[0].distance.text
                val distanceP= responseData.routes[0].legs[0].distance.value
                val duration= responseData.routes[0].legs[0].duration.text
                val durationP= responseData.routes[0].legs[0].duration.value
                val startAdress=responseData.routes[0].legs[0].start_address
                val endAdress=responseData.routes[0].legs[0].end_address
                val boundNorth=responseData.routes[0].bounds.northeast.toString()
                val boundSouth=responseData.routes[0].bounds.southwest.toString()
                val infoList= mutableListOf(polyline,distance,duration,startAdress,endAdress,boundNorth,boundSouth)
                _polyDirectionsInfo.value=infoList
              regexteam.example.regex.Utils.Utils.calcData= listOf(distanceP,durationP)
            }



            }

            override fun onFailure(call: Call<regexteam.example.regex.Model.PolyDirections>, t: Throwable) {
                Log.d("ERROR", t.toString())
            }

        })
    }


}


