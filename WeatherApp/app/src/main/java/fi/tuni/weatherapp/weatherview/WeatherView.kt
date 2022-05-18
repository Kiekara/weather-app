package fi.tuni.weatherapp.weatherview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeatherView(
    temperature: Double? = 0.0,
    weather: String? = null,
    city: String? = null,
    feelsLike: Double? = 0.0,
    windSpeed: Double? = 0.0,
    humidity: Int? = 0,
    sunrise: Long? = 0,
    sunset: Long? = 0
) {
    val configuration = LocalConfiguration.current

    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .height((configuration.screenHeightDp / 2).dp),
            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconText(
                    text = city ?: "No location",
                    icon = Icons.Default.LocationOn,
                    iconSize = 25.sp
                )
                Spacer(modifier = Modifier.height((configuration.screenHeightDp * 0.12).dp))
                MainWeatherInfo(
                    temperature = temperature,
                    weather = weather
                )
                Divider(
                    modifier = Modifier.padding(12.dp),
                    color = Color.Black,
                    thickness = 0.5.dp
                )
                OtherWeatherInfo(
                    feelsLike = feelsLike,
                    windSpeed = windSpeed,
                    humidity = humidity,
                    sunrise = sunrise,
                    sunset = sunset
                )
            }
        }
    }
}