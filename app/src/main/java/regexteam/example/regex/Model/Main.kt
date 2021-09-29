package regexteam.example.regex.Model

import com.google.gson.annotations.SerializedName

data class Main(
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    @SerializedName("temp")
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)