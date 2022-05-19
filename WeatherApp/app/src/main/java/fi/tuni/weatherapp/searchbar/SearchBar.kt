package fi.tuni.weatherapp.searchbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.tuni.weatherapp.util.components.CustomTextField

@Composable
fun SearchBar(
    onSearch: (String) -> Unit
) {
    val text = remember { mutableStateOf(value = "") }
    val configuration = LocalConfiguration.current

    Row {
        CustomTextField(
            text = text.value,
            onTextChange = {
                text.value = it
            },
            onTextClear = {
                text.value = ""
            },
            onDone = {
                onSearch(text.value)
            },
            padding = 9.dp,
            width = (configuration.screenWidthDp / 2).dp,
            fontSize = 14.sp,
            placeholder = "Search for a location"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                onSearch(text.value)
                text.value = ""
            }
        ) {
            Text(text = "Search")
        }
    }
}