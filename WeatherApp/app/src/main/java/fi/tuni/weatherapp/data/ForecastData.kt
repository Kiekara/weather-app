package fi.tuni.weatherapp.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ForecastJsonObject(
    var list: MutableList<WeatherItem>? = null,
    var city: City? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class WeatherItem(
    var dt: Long = 0,
    var main: Temperature? = null,
    var weather: MutableList<Weather>? = null,
    var wind: Wind? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class City(
    var name: String? = null,
    var country: String? = null,
    var sunrise: Long = 0,
    var sunset: Long = 0
)