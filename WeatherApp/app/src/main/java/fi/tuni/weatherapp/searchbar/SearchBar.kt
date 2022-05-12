package fi.tuni.weatherapp.searchbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    onSearch: (String) -> Unit
) {
    val text = remember { mutableStateOf(value = "") }

    Row {
        TextField(
            value = text.value,
            onValueChange = {
                text.value = it
            },
            modifier = Modifier.width(200.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                onSearch(text.value)
            }
        ) {
            Text(text = "Search")
        }
    }
}