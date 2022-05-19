package fi.tuni.weatherapp.util.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun LabeledText(
    text: String,
    label: String,
    fontSize: TextUnit = 18.sp,
    labelSize: TextUnit = 14.sp,
    fontColor: Color = Color.Black,
    labelColor: Color = Color.Gray
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            fontSize = labelSize,
            color = labelColor
        )
        Text(
            text = text,
            fontSize = fontSize,
            color = fontColor
        )
    }
}