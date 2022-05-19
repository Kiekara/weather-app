package fi.tuni.weatherapp.weatherview

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun MainWeatherInfo(
    temperature: Double? = 0.0,
    weather: String? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${temperature?.roundToInt() ?: 0}°C",
            fontSize = 75.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = weather ?: "No data",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    }
}