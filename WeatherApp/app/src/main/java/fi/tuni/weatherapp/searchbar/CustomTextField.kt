package fi.tuni.weatherapp.searchbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    text: String,
    onTextChange: (String) -> Unit,
    onTextClear: () -> Unit,
    padding: Dp,
    width: Dp,
    fontSize: TextUnit,
    placeholder: String
) {
    Box {
        BasicTextField(
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .background(Color.LightGray, RoundedCornerShape(percent = 12))
                        .width(width)
                        .padding(padding)
                ) {
                    innerTextField()
                }
            },
            textStyle = TextStyle(fontSize = fontSize),
            singleLine = true
        )
        if (text.isEmpty()) {
            Text(
                modifier = Modifier
                    .padding(padding)
                    .alpha(ContentAlpha.medium),
                fontSize = fontSize,
                text = placeholder
            )
        } else {
            Row {
                Spacer(modifier = Modifier.width(width - 40.dp))
                IconButton(
                    onClick = {
                        onTextClear()
                    },
                    modifier = Modifier.size(38.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Icon close",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Red
                    )
                }
            }
        }
    }
}