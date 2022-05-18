package fi.tuni.weatherapp.weatherview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeatherView(
    city: String? = null
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
            IconText(
                text = city ?: "No location",
                icon = Icons.Default.LocationOn,
                iconSize = 25.sp
            )
        }
    }
}