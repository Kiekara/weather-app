package fi.tuni.weatherapp

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.URL
import kotlin.concurrent.thread

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

fun constructWeatherAndForecastUrls(
    city: String? = null,
    lat: String? = null,
    lon: String? = null
): List<URL?> {
    return listOf(
        constructWeatherOrForecastUrl(city = city, lat = lat, lon = lon),
        constructWeatherOrForecastUrl(searchKey = "forecast", city = city, lat = lat, lon = lon)
    )
}

fun fetchData(url: URL): String {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url(url = url)
        .build()

    client.newCall(request = request).execute().use {
        if (!it.isSuccessful) throw IOException("Unexpected code $it")

        for ((name, value) in it.headers) println("$name: $value")

        return it.body.string()
    }
}

fun URL.fetchDataAsync(onFetch: (String) -> Unit) {
    thread {
        onFetch(fetchData(url = this))
    }
}