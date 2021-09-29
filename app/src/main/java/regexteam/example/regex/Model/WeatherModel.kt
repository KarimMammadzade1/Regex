package regexteam.example.regex.Model

data class WeatherModel(
    val base: String,
    val clouds: regexteam.example.regex.Model.Clouds,
    val cod: Int,
    val coord: regexteam.example.regex.Model.Coord,
    val dt: Int,
    val id: Int,
    val main: regexteam.example.regex.Model.Main,
    val name: String,
    val sys: regexteam.example.regex.Model.Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<regexteam.example.regex.Model.Weather>,
    val wind: regexteam.example.regex.Model.Wind
)