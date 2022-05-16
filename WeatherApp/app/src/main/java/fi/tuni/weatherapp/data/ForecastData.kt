package fi.tuni.weatherapp.data

data class ForecastJsonObject(
    var list: MutableList<WeatherItem>? = null,
    var city: City? = null
)

data class WeatherItem(
    var dt: Long = 0,
    var main: Temperature? = null,
    var weather: MutableList<Weather>? = null,
    var wind: Wind? = null
)

data class City(
    var name: String? = null,
    var country: String? = null,
    var sunrise: Long = 0,
    var sunset: Long = 0
)