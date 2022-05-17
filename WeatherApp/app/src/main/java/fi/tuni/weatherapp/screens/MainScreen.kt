package fi.tuni.weatherapp.screens

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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import fi.tuni.weatherapp.constructWeatherAndForecastUrls
import fi.tuni.weatherapp.data.ForecastJsonObject
import fi.tuni.weatherapp.data.WeatherJsonObject
import fi.tuni.weatherapp.fetchDataAsync
import fi.tuni.weatherapp.parseWeatherOrForecastJson
import fi.tuni.weatherapp.searchbar.SearchBar

@Composable
fun MainScreen() {
    val city = remember { mutableStateOf(value = "") }
    val weatherObj = remember { mutableStateOf(value = WeatherJsonObject()) }
    val forecastObj = remember { mutableStateOf(value = ForecastJsonObject()) }
    val focusManager = LocalFocusManager.current

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
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(
                onSearch = {
                    city.value = it

                    constructWeatherAndForecastUrls(city = it).forEach { url ->
                        url!!.fetchDataAsync { response ->
                            val path = url.path.toString().split("/").last()

                            when (path) {
                                "weather" ->
                                    weatherObj.value = response.parseWeatherOrForecastJson(searchKey = path)
                                            as WeatherJsonObject
                                "forecast" ->
                                    forecastObj.value = response.parseWeatherOrForecastJson(searchKey = path)
                                            as ForecastJsonObject
                            }

                            println(response)
                        }
                    }
                }
            )
        }
    }
}