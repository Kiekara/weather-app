package fi.tuni.weatherapp

import java.net.URL

fun constructWeatherOrForecastUrl(
    searchKey: String = "weather",
    city: String? = null,
    lat: String? = null,
    lon: String? = null
): URL? {
    val cityUrl =
        "https://api.openweathermap.org/data/2.5/" +
                "${searchKey}?q=${city}&units=metric&appid=4b52908b9d1865a06ce33358d32b89a6"
    val locationUrl =
        "https://api.openweathermap.org/data/2.5/" +
                "${searchKey}?lat=${lat}&lon=${lon}&units=metric&appid=4b52908b9d1865a06ce33358d32b89a6"

    return if (city != null) URL(cityUrl) else if (lat != null && lon != null) URL(locationUrl) else null
}