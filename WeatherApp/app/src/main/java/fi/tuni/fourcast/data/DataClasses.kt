package fi.tuni.fourcast.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

// Data class for parsing current weather data into an object
// Ignore unknown and not wanted properties
@JsonIgnoreProperties(ignoreUnknown = true)
data class WeatherJsonObject(
    var weather: MutableList<Weather>? = null,
    var main: Temperature? = null,
    var wind: Wind? = null,
    var sys: DayTime? = null,
    var name: String? = null
)

// Data class for parsing forecast data into an object
@JsonIgnoreProperties(ignoreUnknown = true)
data class ForecastJsonObject(
    var list: MutableList<WeatherItem>? = null,
    var city: City? = null
)

// Data class for a single weather item in forecast list
@JsonIgnoreProperties(ignoreUnknown = true)
data class WeatherItem(
    var dt: Long = 0,
    var main: Temperature? = null,
    var weather: MutableList<Weather>? = null,
    var wind: Wind? = null,
    var dt_txt: String? = null
)

// Data class for weather condition and icon data
@JsonIgnoreProperties(ignoreUnknown = true)
data class Weather(
    var main: String? = null,
    var description: String? = null,
    var icon: String? = null
)

// Data class for temperature and humidity data
@JsonIgnoreProperties(ignoreUnknown = true)
data class Temperature(
    var temp: Double = 0.0,
    var feels_like: Double = 0.0,
    var humidity: Int = 0
)

// Data class for wind speed data
@JsonIgnoreProperties(ignoreUnknown = true)
data class Wind(
    var speed: Double = 0.0
)

// Data class for sunrise and sunset data
@JsonIgnoreProperties(ignoreUnknown = true)
data class DayTime(
    var sunrise: Long = 0,
    var sunset: Long = 0
)

// Data class for location name, sunrise and sunset data
@JsonIgnoreProperties(ignoreUnknown = true)
data class City(
    var name: String? = null,
    var country: String? = null,
    var sunrise: Long = 0,
    var sunset: Long = 0
)