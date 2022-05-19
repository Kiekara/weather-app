package fi.tuni.weatherapp.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class WeatherJsonObject(
    var weather: MutableList<Weather>? = null,
    var main: Temperature? = null,
    var wind: Wind? = null,
    var sys: DayTime? = null,
    var name: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Weather(
    var main: String? = null,
    var description: String? = null,
    var icon: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Temperature(
    var temp: Double = 0.0,
    var feels_like: Double = 0.0,
    var humidity: Int = 0
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Wind(
    var speed: Double = 0.0
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class DayTime(
    var sunrise: Long = 0,
    var sunset: Long = 0
)