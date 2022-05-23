package fi.tuni.fourcast.weatherview

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import fi.tuni.fourcast.getDateString
import fi.tuni.fourcast.getTime
import fi.tuni.fourcast.util.components.LabeledText
import kotlin.math.roundToInt

// Composable function for showing other than main weather data
@Composable
fun OtherWeatherInfo(
    // Data to be shown
    feelsLike: Double? = 0.0,
    windSpeed: Double? = 0.0,
    humidity: Int? = 0,
    sunrise: Long? = 0,
    sunset: Long? = 0
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Current feels like temperature text
            LabeledText(
                text = "${feelsLike?.roundToInt() ?: 0}°C",
                label = "feels like"
            )
            // Current wind speed text
            LabeledText(
                text = "${windSpeed?.roundToInt() ?: 0} m/s",
                label = "wind speed"
            )
            // Current humidity text
            LabeledText(
                text = "${humidity ?: 0}%",
                label = "humidity"
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Sunrise time for the current day
            LabeledText(
                text = "${
                    // Slice only hours and minutes from time
                    getDateString(
                        milliSeconds = sunrise?.times(1000) ?: 0,
                        dateFormat = "dd/MM/yyyy hh:mm:ss"
                    ).getTime().slice(0..4)
                } am",
                label = "sunrise"
            )
            // Sunset time for the current day
            LabeledText(
                text = "${
                    // Slice only hours and minutes from time
                    getDateString(
                        milliSeconds = sunset?.times(1000) ?: 0,
                        dateFormat = "dd/MM/yyyy hh:mm:ss"
                    ).getTime().slice(0..4)
                } pm",
                label = "sunset"
            )
        }
    }
}