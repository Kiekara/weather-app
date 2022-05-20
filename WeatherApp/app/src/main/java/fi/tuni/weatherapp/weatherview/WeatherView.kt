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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import fi.tuni.weatherapp.R
import fi.tuni.weatherapp.searchbar.SearchBar
import fi.tuni.weatherapp.util.components.IconText

// Composable function for grouping all the current weather data
@Composable
fun WeatherView(
    // Data to be shown
    temperature: Double? = 0.0,
    weather: String? = null,
    city: String? = null,
    feelsLike: Double? = 0.0,
    windSpeed: Double? = 0.0,
    humidity: Int? = 0,
    sunrise: Long? = 0,
    sunset: Long? = 0,
    icon: String? = null,
    // Callback to be invoked on search
    onSearchCallback: (String) -> Boolean = { true },
    // Callback to be invoked on reset
    onResetCallback: () -> Unit = {},
    // Callback to be invoked on refresh
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
        // Show background image
        Image(
            // The background image is a royalty-free image from Pixabay
            // link: https://pixabay.com/vectors/world-map-earth-global-continents-306338/
            painter = painterResource(id = R.drawable.world_map),
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
                    // Invoke onSearchCallback with the given input
                    onSearchCallback(it)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Current or searched location text
            IconText(
                text = city ?: "No location",
                icon = Icons.Default.LocationOn,
                iconSize = 25.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            // Main weather data to be shown
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
                // Other weather data to be shown
                OtherWeatherInfo(
                    feelsLike = feelsLike,
                    windSpeed = windSpeed,
                    humidity = humidity,
                    sunrise = sunrise,
                    sunset = sunset
                )
                // Location reset and search refresh icon buttons
                IconButtonGroup(
                    // Callback to be invoked on reset
                    onResetCallback = onResetCallback,
                    // Callback to be invoked on refresh
                    onRefreshCallback = onRefreshCallback
                )
            }
        }
    }
}