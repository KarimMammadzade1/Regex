package regexteam.example.regex.Model

data class PolyDirections(
    val geocoded_waypoints: List<regexteam.example.regex.Model.GeocodedWaypoint>,
    val routes: List<regexteam.example.regex.Model.Route>,
    val status: String
)