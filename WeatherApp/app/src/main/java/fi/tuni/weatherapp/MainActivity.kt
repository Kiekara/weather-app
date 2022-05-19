package fi.tuni.weatherapp

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import fi.tuni.weatherapp.screens.MainScreen
import fi.tuni.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    private val activityContext = this
    private val requestPermission = registerForActivityResult(
        RequestPermission()
    ) {
        if (it) showUI()
    }

    private val showUI: () -> Unit = {
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    MainScreen(activityContext = activityContext)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {}
}