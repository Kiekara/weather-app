package fi.tuni.weatherapp.weatherview

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import fi.tuni.weatherapp.getDateString
import fi.tuni.weatherapp.getTime
import fi.tuni.weatherapp.util.components.LabeledText
import kotlin.math.roundToInt

@Composable
fun OtherWeatherInfo(
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
            LabeledText(
                text = "${feelsLike?.roundToInt() ?: 0}°C",
                label = "feels like"
            )
            LabeledText(
                text = "${windSpeed?.roundToInt() ?: 0} m/s",
                label = "wind speed"
            )
            LabeledText(
                text = "${humidity ?: 0}%",
                label = "humidity"
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LabeledText(
                text = "${
                    getDateString(
                        milliSeconds = sunrise?.times(1000) ?: 0,
                        dateFormat = "dd/MM/yyyy hh:mm:ss"
                    ).getTime().slice(0..4)
                } am",
                label = "sunrise"
            )
            LabeledText(
                text = "${
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