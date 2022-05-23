package fi.tuni.fourcast.forecastview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlin.math.roundToInt

// Composable function for a single forecast list item
@Composable
fun ForecastItem(
    // Data to be shown
    day: String? = null,
    weather: String? = null,
    windSpeed: Double? = 0.0,
    temperature: Double? = 0.0,
    icon: String? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            // Weather condition icon
            Image(
                // Use forecast icons via url
                painter = rememberAsyncImagePainter(
                    model = "https://openweathermap.org/img/wn/$icon@2x.png"
                ),
                contentDescription = "Weather icon",
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            // Day of the week text
            Text(
                text = day ?: "no data",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            // Weather condition text
            Text(
                text = weather ?: "no data",
                fontWeight = FontWeight.Medium
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            // Temperature text
            Text(
                text = "${temperature?.roundToInt() ?: 0}°C",
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(16.dp))
            // Wind speed text
            Text(text = "${windSpeed?.roundToInt() ?: 0} m/s")
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}