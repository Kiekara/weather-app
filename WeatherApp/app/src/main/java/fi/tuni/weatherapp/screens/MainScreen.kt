package fi.tuni.weatherapp.screens

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import fi.tuni.weatherapp.checkLocation
import fi.tuni.weatherapp.constructWeatherAndForecastUrls
import fi.tuni.weatherapp.data.ForecastJsonObject
import fi.tuni.weatherapp.data.WeatherItem
import fi.tuni.weatherapp.data.WeatherJsonObject
import fi.tuni.weatherapp.fetchDataAsync
import fi.tuni.weatherapp.forecastview.ForecastView
import fi.tuni.weatherapp.parseWeatherOrForecastJson
import fi.tuni.weatherapp.weatherview.WeatherView
import java.net.URL

// Composable function for main screen of the application
@Composable
fun MainScreen(activityContext: ComponentActivity) {
    // State for current weather data
    val weatherObj = remember { mutableStateOf(value = WeatherJsonObject()) }
    // State for 5-day forecast data
    val forecastObj = remember { mutableStateOf(value = ForecastJsonObject()) }
    // State for filtered 5-day forecast data
    val forecastList = remember { mutableStateOf(value = listOf(WeatherItem())) }
    // State for location search errors
    val locationNotFound = remember { mutableStateOf(value = false) }
    // State for the last city searched
    val city = remember { mutableStateOf(value = "") }
    // Focus manager for clearing focus at certain actions
    val focusManager = LocalFocusManager.current

    // Callback to be invoked on fetch
    val onFetchCallback: (Pair<String, Boolean>, URL) -> Unit = { response, url ->
        // Switches state value to false if it happens to be true
        if (locationNotFound.value) locationNotFound.value = false

        // Destruct incoming response into String and Boolean variables
        val (data, isSuccessful) = response
        // Get the path value of "weather" or "forecast"
        val path = url.path.toString().split("/").last()

        // If the fetch was successful
        if (isSuccessful) {
            // Clear focus, which closes the keyboard if it's still active
            focusManager.clearFocus()
            // Parse the data string with the given path value
            val result = data.parseWeatherOrForecastJson(searchKey = path)

            when (path) {
                // When the path is "weather" save the parsing result as WeatherJsonObject
                "weather" -> {
                    weatherObj.value = result as WeatherJsonObject
                    // Save the fetched location name
                    city.value = weatherObj.value.name ?: ""
                }
                // When the path is "forecast" save the parsing result as ForecastJsonObject
                "forecast" -> {
                    forecastObj.value = result as ForecastJsonObject
                    // Filter weather when it's high noon
                    forecastList.value = forecastObj.value.list!!.filter {
                        it.dt_txt!!.contains("12:00:00")
                    }
                }
            }

            println(result)
        } else {
            // If the fetch was a failure, save true
            locationNotFound.value = !isSuccessful
        }
    }

    // Check current location when the application opens
    checkLocation(activityContext = activityContext) { lat, lon, source ->
        // Construct urls with coordinates and iterate them
        constructWeatherAndForecastUrls(lat = lat, lon = lon).forEach { url ->
            // Fetch the data with the given url
            url!!.fetchDataAsync { response ->
                // Invoke onFetchCallback with the response and the url
                onFetchCallback(response, url)
            }
        }
        // Close the location source after data fetching
        source.cancel()
    }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .pointerInput(Unit) {
                // Clear focus by clicking the UI, which also closes the active keyboard
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 4.dp,
        backgroundColor = Color.Black
    ) {
        // If location search was a failure, display a short toast to the user
        if (locationNotFound.value) {
            Toast.makeText(
                LocalContext.current,
                "Location not found",
                Toast.LENGTH_SHORT
            ).show()
        }

        Column(
            modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherView(
                // Data to be shown
                temperature = weatherObj.value.main?.temp,
                weather = weatherObj.value.weather?.first()?.main,
                city = weatherObj.value.name,
                windSpeed = weatherObj.value.wind?.speed,
                feelsLike = weatherObj.value.main?.feels_like,
                humidity = weatherObj.value.main?.humidity,
                sunrise = weatherObj.value.sys?.sunrise,
                sunset = weatherObj.value.sys?.sunset,
                icon = weatherObj.value.weather?.first()?.icon,
                onSearchCallback = {
                    // Construct urls with the given input
                    constructWeatherAndForecastUrls(city = it).forEach { url ->
                        // Fetch the data with the given url
                        url!!.fetchDataAsync { response ->
                            // Invoke onFetchCallback with the response and the url
                            onFetchCallback(response, url)
                        }
                    }
                    // Return a boolean value of whether the search was successful or not
                    locationNotFound.value
                },
                onResetCallback = {
                    // Check the current location or reset any search results
                    checkLocation(activityContext = activityContext) { lat, lon, source ->
                        constructWeatherAndForecastUrls(lat = lat, lon = lon).forEach { url ->
                            url!!.fetchDataAsync { response ->
                                onFetchCallback(response, url)
                            }
                        }
                        source.cancel()
                    }
                },
                onRefreshCallback = {
                    // Refresh the results with the last known location
                    constructWeatherAndForecastUrls(city = city.value).forEach { url ->
                        url!!.fetchDataAsync { response ->
                            onFetchCallback(response, url)
                        }
                    }
                }
            )
            ForecastView(
                // Filtered forecast list to be iterated
                forecastList = forecastList.value
            )
        }
    }
}