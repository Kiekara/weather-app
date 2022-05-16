package fi.tuni.weatherapp.data

data class WeatherJsonObject(
    var weather: MutableList<Weather>? = null,
    var main: Temperature? = null,
    var wind: Wind? = null,
    var name: String? = null
)

data class Weather(
    var main: String? = null,
    var description: String? = null
)

data class Temperature(
    var temp: Double = 0.0,
    var feels_like: Double = 0.0
)

data class Wind(
    var speed: Double = 0.0
)