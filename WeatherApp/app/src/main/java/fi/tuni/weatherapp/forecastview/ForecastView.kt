package fi.tuni.weatherapp.forecastview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fi.tuni.weatherapp.data.WeatherItem
import fi.tuni.weatherapp.getDayString

@Composable
fun ForecastView(
    forecastList: List<WeatherItem>
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        elevation = 4.dp,
        backgroundColor = Color.LightGray
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .verticalScroll(
                    state = rememberScrollState(),
                    enabled = true
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "5-DAY FORECAST",
                fontWeight = FontWeight.Medium
            )
            forecastList.forEach {
                Divider(
                    modifier = Modifier.padding(12.dp),
                    color = Color.Black,
                    thickness = 0.5.dp
                )
                ForecastItem(
                    day = getDayString(milliSeconds = it.dt * 1000),
                    weather = it.weather?.first()?.main,
                    windSpeed = it.wind?.speed,
                    temperature = it.main?.temp,
                    icon = it.weather?.first()?.icon
                )
            }
        }
    }
}