package fi.tuni.weatherapp.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
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

// Composable function for a single customised text field
@Composable
fun CustomTextField(
    text: String,
    // Callback to be invoked on text change
    onTextChange: (String) -> Unit,
    // Callback to be invoked on text clear
    onTextClear: () -> Unit,
    // Callback to be invoked on keyboard action done
    onDone: () -> Unit,
    padding: Dp,
    width: Dp,
    fontSize: TextUnit,
    placeholder: String
) {
    Box {
        BasicTextField(
            value = text,
            onValueChange = {
                // Invoke onTextChange callback with current input value
                onTextChange(it)
            },
            decorationBox = { innerTextField ->
                // Modify text field skeleton
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
            // Prevent input from multi lining
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    // Invoke onDone callback
                    onDone()
                }
            )
        )
        if (text.isEmpty()) {
            // When there's no input, show placeholder text
            Text(
                modifier = Modifier
                    .padding(padding)
                    .alpha(ContentAlpha.medium),
                fontSize = fontSize,
                text = placeholder
            )
        } else {
            // Else show IconButton for text field clearance
            Row {
                Spacer(modifier = Modifier.width(width - 40.dp))
                IconButton(
                    onClick = {
                        // Invoke onTextClear callback
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