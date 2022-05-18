package fi.tuni.weatherapp.weatherview

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun IconText(
    text: String,
    icon: ImageVector,
    fontSize: TextUnit = 20.sp,
    fontStyle: FontStyle = FontStyle.Normal,
    fontWeight: FontWeight = FontWeight.Normal,
    iconSize: TextUnit = 20.sp
) {
    val id = "inlineContent"
    val annotatedText = buildAnnotatedString {
        appendInlineContent(id = id, alternateText = "[icon]")
        append(text)
    }

    val inlineContent = mapOf(
        Pair(
            id,
            InlineTextContent(
                Placeholder(
                    width = iconSize,
                    height = iconSize,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                )
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon"
                )
            }
        )
    )

    Text(
        text = annotatedText,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        inlineContent = inlineContent
    )
}