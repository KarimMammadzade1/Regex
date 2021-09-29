package regexteam.example.regex.Model

data class Step(
    val distance: regexteam.example.regex.Model.DistanceX,
    val duration: regexteam.example.regex.Model.DurationX,
    val end_location: regexteam.example.regex.Model.EndLocationX,
    val html_instructions: String,
    val maneuver: String,
    val polyline: regexteam.example.regex.Model.Polyline,
    val start_location: regexteam.example.regex.Model.StartLocationX,
    val travel_mode: String
)