package regexteam.example.regex.Model

import com.google.gson.annotations.SerializedName

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    @SerializedName("main")
    val main: String
)