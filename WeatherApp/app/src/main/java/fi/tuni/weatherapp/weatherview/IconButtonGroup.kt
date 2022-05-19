package fi.tuni.weatherapp.weatherview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun IconButtonGroup(
    onResetCallback: () -> Unit = {},
    onRefreshCallback: () -> Unit = {}
) {
    Column() {
        IconButton(
            onClick = {
                onResetCallback()
            },
            modifier = Modifier.background(
                color = Color.Green,
                shape = RoundedCornerShape(size = 25.dp)
            ).border(
                width = 5.dp,
                brush = Brush.radialGradient(listOf(Color.Black, Color.Green)),
                shape = RoundedCornerShape(size = 25.dp)
            )
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Location icon"
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        IconButton(
            onClick = {
                onRefreshCallback()
            },
            modifier = Modifier.background(
                color = Color.Cyan,
                shape = RoundedCornerShape(size = 25.dp)
            ).border(
                width = 5.dp,
                brush = Brush.radialGradient(listOf(Color.Black, Color.Cyan)),
                shape = RoundedCornerShape(size = 25.dp)
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "Refresh icon"
            )
        }
    }
}