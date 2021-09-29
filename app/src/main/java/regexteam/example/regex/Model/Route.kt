package regexteam.example.regex.Model

import com.google.gson.annotations.SerializedName

data class Route(
    @SerializedName("bounds")
    val bounds: regexteam.example.regex.Model.Bounds,
    val copyrights: String,
    val legs: List<regexteam.example.regex.Model.Leg>,
    @SerializedName("overview_polyline")
    val overview_polyline: regexteam.example.regex.Model.OverviewPolyline,
    val summary: String,
    val warnings: List<Any>,
    val waypoint_order: List<Any>
)