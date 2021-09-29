package regexteam.regex.Networking

import regexteam.example.regex.BuildConfig.MAPS_API_KEY
import regexteam.example.regex.BuildConfig.WEATHER_API_KEY
import regexteam.example.regex.Model.PolyDirections
import regexteam.example.regex.Model.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NetworkService {

    @GET("data/2.5/weather?units=metric")
    fun getWeatherInfo(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") key: String = WEATHER_API_KEY
    ): Call<WeatherModel>

    @GET("api/directions/json")
    fun getPolyDirections(
        @Query("origin",encoded = true)oLocation: String,
        @Query("destination",encoded = true)dLocation: String,
        @Query("mode",)mode:String="driving",
        @Query("key")key:String= MAPS_API_KEY

    ):Call<PolyDirections>

}
