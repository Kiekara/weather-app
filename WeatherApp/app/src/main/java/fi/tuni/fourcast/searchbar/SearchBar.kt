package fi.tuni.fourcast.searchbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.tuni.fourcast.util.components.CustomTextField

// Composable function for a search bar
@Composable
fun SearchBar(
    // Callback to be invoked on search
    onSearchCallback: (String, (Boolean) -> Unit) -> Unit
) {
    // User input text state
    val text = remember { mutableStateOf(value = "") }
    // Information of the current phone
    val configuration = LocalConfiguration.current

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Customised text field
        CustomTextField(
            text = text.value,
            onTextChange = {
                // Save input to the state
                text.value = it
            },
            onTextClear = {
                // Clear input from the state
                text.value = ""
            },
            onDone = {
                // Invoke onSearchCallback
                onSearchCallback(text.value) {
                    // If search is a success, clear the input from the state
                    if (it) text.value = ""
                }
            },
            padding = 9.dp,
            width = (configuration.screenWidthDp / 2).dp,
            fontSize = 14.sp,
            placeholder = "Search for a location"
        )
        Button(
            onClick = {
                // Invoke onSearchCallback
                onSearchCallback(text.value) {
                    // If search is a success, clear the input from the state
                    if (it) text.value = ""
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant)
        ) {
            Text(text = "Search")
        }
    }
}