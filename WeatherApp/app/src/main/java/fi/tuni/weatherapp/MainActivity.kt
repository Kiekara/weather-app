package fi.tuni.weatherapp

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import fi.tuni.weatherapp.screens.MainScreen
import fi.tuni.weatherapp.ui.theme.WeatherAppTheme

// Use experimental permissions API for permission requests
@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherAppTheme {
                // Set permission state for accurate location
                val permissionState = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
                // UI base surface
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    when (val status = permissionState.status) {
                        // If location permission is granted, show MainScreen
                        PermissionStatus.Granted -> MainScreen(activityContext = this)
                        // If location permission is denied, show a custom request
                        is PermissionStatus.Denied -> {
                            Column(
                                modifier = Modifier.padding(50.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                val textToShow = if (status.shouldShowRationale) {
                                    // If rationale can be shown, ask the user for permission
                                    "Location permission required to use the app. " +
                                            "Please grant the permission."
                                } else {
                                    // Else tempt the user to go to settings to grant the
                                    // permission
                                    "Location permission required to use the app. " +
                                            "Please go to settings to grant the permission."
                                }
                                Text(
                                    text = textToShow,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Button(
                                    onClick = {
                                        if (status.shouldShowRationale)
                                            // If rationale can be shown, launch permission request
                                            permissionState.launchPermissionRequest()
                                        // Else take the user to the application settings
                                        else startActivity(
                                            Intent(
                                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts("package", packageName, null)
                                            )
                                        )
                                    }
                                ) {
                                    Text(
                                        text = if (status.shouldShowRationale) "Request permission"
                                            else "Go to settings"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {}
}