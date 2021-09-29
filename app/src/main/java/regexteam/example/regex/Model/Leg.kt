package regexteam.example.regex.Model

import com.google.gson.annotations.SerializedName

data class Leg(
    @SerializedName("distance")
    val distance: regexteam.example.regex.Model.Distance,
    @SerializedName("duration")
    val duration: regexteam.example.regex.Model.Duration,
    @SerializedName("end_address")
    val end_address: String,
    val end_location: regexteam.example.regex.Model.EndLocation,
    @SerializedName("start_address")
    val start_address: String,
    val start_location: regexteam.example.regex.Model.StartLocation,
    val steps: List<regexteam.example.regex.Model.Step>,
    val traffic_speed_entry: List<Any>,
    val via_waypoint: List<Any>
)