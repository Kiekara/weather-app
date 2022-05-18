package fi.tuni.weatherapp

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import fi.tuni.weatherapp.data.ForecastJsonObject
import fi.tuni.weatherapp.data.WeatherJsonObject
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

@SuppressLint("MissingPermission")
fun checkLocation(
    activityContext: ComponentActivity,
    onSuccess: (String, String, CancellationTokenSource) -> Unit
) {
    val client = LocationServices.getFusedLocationProviderClient(activityContext)
    val source = CancellationTokenSource()

    client.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, source.token)
        .addOnSuccessListener {
            val lat = it.latitude.toString()
            val lon = it.longitude.toString()

            onSuccess(lat, lon, source)
        }.addOnCanceledListener {
            println("Source cancelled")
        }
}

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

fun fetchData(url: URL): Pair<String, Boolean> {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url(url = url)
        .build()

    client.newCall(request = request).execute().use {
        for ((name, value) in it.headers) println("$name: $value")

        return Pair(
            it.body.string(),
            it.isSuccessful
        )
    }
}

fun URL.fetchDataAsync(onFetch: (Pair<String, Boolean>) -> Unit) {
    thread {
        onFetch(fetchData(url = this))
    }
}

fun parseJson(json: String, target: Class<*>?): Any {
    val mp = ObjectMapper()
    return mp.readValue(json, target)
}

fun String.parseWeatherOrForecastJson(searchKey: String): Any {
    val target = when (searchKey) {
        "weather" -> WeatherJsonObject::class.java
        "forecast" -> ForecastJsonObject::class.java
        else -> null
    }

    return parseJson(json = this, target = target)
}

fun getDateString(milliSeconds: Long, dateFormat: String): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    val calendar = Calendar.getInstance()

    calendar.timeInMillis = milliSeconds
    return formatter.format(calendar.time)
}

fun String.getTime() = this.split(" ").last()