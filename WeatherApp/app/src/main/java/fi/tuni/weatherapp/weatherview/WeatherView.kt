package fi.tuni.weatherapp.weatherview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import fi.tuni.weatherapp.searchbar.SearchBar
import fi.tuni.weatherapp.util.components.IconText

@Composable
fun WeatherView(
    temperature: Double? = 0.0,
    weather: String? = null,
    city: String? = null,
    feelsLike: Double? = 0.0,
    windSpeed: Double? = 0.0,
    humidity: Int? = 0,
    sunrise: Long? = 0,
    sunset: Long? = 0,
    icon: String? = null,
    onSearchCallback: (String) -> Boolean = { true },
    onResetCallback: () -> Unit = {},
    onRefreshCallback: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .requiredHeight(412.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        elevation = 4.dp
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = "https://cdn.pixabay.com/photo/2014/04/02/14/09/world-map-306338_960_720.png"
            ),
            contentDescription = "Background image",
            modifier = Modifier.padding(12.dp),
            alpha = 0.35f
        )
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(
                onSearchCallback = {
                    onSearchCallback(it)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            IconText(
                text = city ?: "No location",
                icon = Icons.Default.LocationOn,
                iconSize = 25.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            MainWeatherInfo(
                temperature = temperature,
                weather = weather,
                icon = icon
            )
            Divider(
                modifier = Modifier.padding(12.dp),
                color = Color.Black,
                thickness = 0.5.dp
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OtherWeatherInfo(
                    feelsLike = feelsLike,
                    windSpeed = windSpeed,
                    humidity = humidity,
                    sunrise = sunrise,
                    sunset = sunset
                )
                IconButtonGroup(
                    onResetCallback = onResetCallback,
                    onRefreshCallback = onRefreshCallback
                )
            }
        }
    }
}