package fi.tuni.fourcast.weatherview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kotlin.math.roundToInt

// Composable function for showing main weather data
@Composable
fun MainWeatherInfo(
    // Data to be shown
    temperature: Double? = 0.0,
    weather: String? = null,
    icon: String? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Current temperature text
        Text(
            text = "${temperature?.roundToInt() ?: 0}°C",
            fontSize = 75.sp,
            fontWeight = FontWeight.Medium
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Current weather condition icon
            Image(
                // Use forecast icons via url
                painter = rememberAsyncImagePainter(
                    model = "https://openweathermap.org/img/wn/$icon@2x.png"
                ),
                contentDescription = "Weather icon",
                modifier = Modifier.size(40.dp)
            )
            // Current weather condition text
            Text(
                text = weather ?: "No data",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}