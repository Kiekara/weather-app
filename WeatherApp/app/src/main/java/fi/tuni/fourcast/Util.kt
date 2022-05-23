package fi.tuni.fourcast

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import fi.tuni.fourcast.data.ForecastJsonObject
import fi.tuni.fourcast.data.WeatherJsonObject
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

// Stays quiet about permissions
// Function for checking the current location
@SuppressLint("MissingPermission")
fun checkLocation(
    activityContext: ComponentActivity,
    // Callback to be invoked on a successful check
    onSuccess: (String, String, CancellationTokenSource) -> Unit
) {
    // Create a client instance with the given activityContext
    val client = LocationServices.getFusedLocationProviderClient(activityContext)
    // Token source
    val source = CancellationTokenSource()

    // Request current location with high accuracy
    client.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, source.token)
        .addOnSuccessListener {
            val lat = it.latitude.toString()
            val lon = it.longitude.toString()

            // Invoke onSuccess callback with given coordinates and the token
            onSuccess(lat, lon, source)
        }.addOnCanceledListener {
            println("Source cancelled")
        }
}

// Function for constructing urls from city name or coordinate values
fun constructWeatherOrForecastUrl(
    // Sets search key to "weather" by default
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

    // Returns city url if city is not null
    // Returns location url if neither of coordinates are not null
    // Otherwise returns null
    return if (city != null) URL(cityUrl) else if (lat != null && lon != null) URL(locationUrl) else null
}

// Function for constructing url with both search keys
fun constructWeatherAndForecastUrls(
    city: String? = null,
    lat: String? = null,
    lon: String? = null
): List<URL?> {
    return listOf(
        // Construct url with default search key
        constructWeatherOrForecastUrl(city = city, lat = lat, lon = lon),
        // Construct url with "forecast" search key
        constructWeatherOrForecastUrl(searchKey = "forecast", city = city, lat = lat, lon = lon)
    )
}

// Function for fetching data with given url
fun fetchData(url: URL): Pair<String, Boolean> {
    // Create a OkHttp client instance
    val client = OkHttpClient()

    // Build a request with the given url
    val request = Request.Builder()
        .url(url = url)
        .build()

    // Execute a new call with the given request
    client.newCall(request = request).execute().use {
        // Return response body and isSuccessful value
        return Pair(
            it.body.string(),
            it.isSuccessful
        )
    }
}

// URL extension function for fetching data asynchronously
fun URL.fetchDataAsync(onFetch: (Pair<String, Boolean>) -> Unit) {
    thread {
        // Invoke onFetch callback with the response from fetchData
        onFetch(fetchData(url = this))
    }
}

// Function for parsing json
fun parseJson(json: String, target: Class<*>?): Any {
    // Create an ObjectMapper instance
    val mp = ObjectMapper()
    // Return an object from given json string and target data class
    return mp.readValue(json, target)
}

// String extension function for parsing explicitly weather or forecast json
fun String.parseWeatherOrForecastJson(searchKey: String): Any {
    val target = when (searchKey) {
        // When the given search key is "weather", target data class is WeatherJsonObject
        "weather" -> WeatherJsonObject::class.java
        // When the given search key is "forecast", target data class is ForecastJsonObject
        "forecast" -> ForecastJsonObject::class.java
        // Else target is null
        else -> null
    }

    // Return result object from parseJson
    return parseJson(json = this, target = target)
}

// Function for converting milliseconds to date string
fun getDateString(milliSeconds: Long, dateFormat: String): String {
    // Create a SimpleDateFormat instance
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    // Get Calendar instance
    val calendar = Calendar.getInstance()

    // Set the given milliseconds as calendar time in milliseconds
    calendar.timeInMillis = milliSeconds
    // Return formatted date time
    return formatter.format(calendar.time)
}

// String extension function for splitting time string apart from date string
fun String.getTime() = this.split(" ").last()

// Function for converting milliseconds into the day of the week
fun getDayString(milliSeconds: Long): String {
    // Create a GregorianCalendar instance
    val calendar = GregorianCalendar()
    // Set the given milliseconds in Date object as calendar time
    calendar.time = Date(milliSeconds)

    // Return the matching day of the week
    return when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.MONDAY -> "Monday"
        Calendar.TUESDAY -> "Tuesday"
        Calendar.WEDNESDAY -> "Wednesday"
        Calendar.THURSDAY -> "Thursday"
        Calendar.FRIDAY -> "Friday"
        Calendar.SATURDAY -> "Saturday"
        Calendar.SUNDAY -> "Sunday"
        else -> "unknown"
    }
}