package fi.tuni.weatherapp.weatherview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Composable function for a fixed set of icon buttons
@Composable
fun IconButtonGroup(
    // Callback to be invoked on reset
    onResetCallback: () -> Unit = {},
    // Callback to be invoked on refresh
    onRefreshCallback: () -> Unit = {}
) {
    // Modifier for IconButton
    val getModifier: (Color, Dp) -> Modifier = { color, size ->
        Modifier.background(
            color = color,
            shape = RoundedCornerShape(size = size)
        ).border(
            width = 5.dp,
            brush = Brush.radialGradient(listOf(Color.Black, color)),
            shape = RoundedCornerShape(size = size)
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(
            onClick = {
                // Invoke onResetCallback
                onResetCallback()
            },
            modifier = getModifier(Color.Green, 25.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Location icon"
            )
        }
        IconButton(
            onClick = {
                // Invoke onRefreshCallback
                onRefreshCallback()
            },
            modifier = getModifier(Color.Cyan, 25.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "Refresh icon"
            )
        }
    }
}