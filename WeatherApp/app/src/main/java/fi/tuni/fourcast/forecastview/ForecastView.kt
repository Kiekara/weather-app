package fi.tuni.fourcast.forecastview

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
import fi.tuni.fourcast.data.WeatherItem
import fi.tuni.fourcast.getDayString

// Composable function for forecast list
@Composable
fun ForecastView(
    // Data list to be iterated
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
                    // Enable scrolling for shorter screens
                    state = rememberScrollState(),
                    enabled = true
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 5-day forecast header
            Text(
                text = "5-DAY FORECAST",
                fontWeight = FontWeight.Medium
            )
            // Iterate forecast list items and add dividers between them
            forecastList.forEach {
                Divider(
                    modifier = Modifier.padding(12.dp),
                    color = Color.Black,
                    thickness = 0.5.dp
                )
                // Forecast item for a single day weather
                ForecastItem(
                    day = getDayString(milliSeconds = it.dt.times(1000)),
                    weather = it.weather?.first()?.main,
                    windSpeed = it.wind?.speed,
                    temperature = it.main?.temp,
                    icon = it.weather?.first()?.icon
                )
            }
        }
    }
}