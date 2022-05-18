package fi.tuni.weatherapp.weatherview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun WeatherView() {
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

        }
    }
}