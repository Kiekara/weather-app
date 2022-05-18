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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import fi.tuni.weatherapp.checkLocation
import fi.tuni.weatherapp.constructWeatherAndForecastUrls
import fi.tuni.weatherapp.data.ForecastJsonObject
import fi.tuni.weatherapp.data.WeatherJsonObject
import fi.tuni.weatherapp.fetchDataAsync
import fi.tuni.weatherapp.forecastview.ForecastPreview
import fi.tuni.weatherapp.parseWeatherOrForecastJson
import fi.tuni.weatherapp.weatherview.WeatherView
import java.net.URL

@Composable
fun MainScreen(activityContext: ComponentActivity) {
    val weatherObj = remember { mutableStateOf(value = WeatherJsonObject()) }
    val forecastObj = remember { mutableStateOf(value = ForecastJsonObject()) }
    val locationNotFound = remember { mutableStateOf(value = false) }
    val focusManager = LocalFocusManager.current

    val onFetchCallback: (Pair<String, Boolean>, url: URL) -> Unit = { response, url ->
        val (data, isSuccessful) = response
        val path = url.path.toString().split("/").last()

        if (isSuccessful) {
            val result = data.parseWeatherOrForecastJson(searchKey = path)

            when (path) {
                "weather" ->
                    weatherObj.value = result as WeatherJsonObject
                "forecast" ->
                    forecastObj.value = result as ForecastJsonObject
            }

            println(result)
        } else {
            locationNotFound.value = !isSuccessful
        }
    }

    checkLocation(activityContext = activityContext) { lat, lon, source ->
        constructWeatherAndForecastUrls(lat = lat, lon = lon).forEach { url ->
            url!!.fetchDataAsync { response ->
                onFetchCallback(response, url)
            }
        }
        source.cancel()
    }

    Card(
        modifier = Modifier
            .padding(12.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherView(
                temperature = weatherObj.value.main?.temp,
                weather = weatherObj.value.weather?.first()?.main,
                city = weatherObj.value.name,
                windSpeed = weatherObj.value.wind?.speed,
                feelsLike = weatherObj.value.main?.feels_like,
                humidity = weatherObj.value.main?.humidity,
                sunrise = weatherObj.value.sys?.sunrise,
                sunset = weatherObj.value.sys?.sunset,
                onSearchCallback = {
                    locationNotFound.value = false
                    constructWeatherAndForecastUrls(city = it).forEach { url ->
                        url!!.fetchDataAsync { response ->
                            onFetchCallback(response, url)
                        }
                    }
                }
            )
            if (locationNotFound.value) {
                Toast.makeText(
                    LocalContext.current,
                    "Location not found",
                    Toast.LENGTH_SHORT
                ).show()
            }
            ForecastPreview()
        }
    }
}