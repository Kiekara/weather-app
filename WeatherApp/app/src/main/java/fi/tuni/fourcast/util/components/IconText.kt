package fi.tuni.fourcast.util.components

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

// Composable function for a text with leading icon
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
    // Annotated string with inline content id
    val annotatedText = buildAnnotatedString {
        appendInlineContent(id = id, alternateText = "[icon]")
        append(text)
    }

    // Inline content with id
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
                // Icon to be appended
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