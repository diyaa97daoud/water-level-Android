
package com.example.waterlevelmonitoring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.waterlevelmonitoring.data.remote.RetrofitClient
import com.example.waterlevelmonitoring.data.repository.AuthRepository
import com.example.waterlevelmonitoring.data.repository.DeviceRepository
import com.example.waterlevelmonitoring.ui.components.MainTopAppBar
import com.example.waterlevelmonitoring.ui.details.DeviceDetailsScreen
import com.example.waterlevelmonitoring.ui.details.DeviceDetailsViewModel
import com.example.waterlevelmonitoring.ui.details.DeviceDetailsViewModelFactory
import com.example.waterlevelmonitoring.ui.devices.DeviceListScreen
import com.example.waterlevelmonitoring.ui.devices.DeviceListViewModel
import com.example.waterlevelmonitoring.ui.devices.DeviceListViewModelFactory
import com.example.waterlevelmonitoring.ui.login.LoginScreen
import com.example.waterlevelmonitoring.ui.login.LoginViewModel
import com.example.waterlevelmonitoring.ui.login.LoginViewModelFactory
import com.example.waterlevelmonitoring.ui.theme.WaterLevelMonitoringTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaterLevelMonitoringTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    topBar = {
                        if (currentRoute != "login") {
                            MainTopAppBar(
                                navController = navController,
                                title = when (currentRoute) {
                                    "devices" -> "Devices"
                                    "device_details/{deviceId}" -> "Device Details"
                                    else -> "Water Level Monitoring"
                                }
                            )
                        }
                    }
                ) { paddingValues ->
                    // Create repositories once and remember them
                    val authRepository = remember { AuthRepository(RetrofitClient.instance) }
                    val deviceRepository = remember { DeviceRepository(RetrofitClient.instance) }

                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("login") {
                            val factory = remember { LoginViewModelFactory(authRepository) }
                            val loginViewModel: LoginViewModel = viewModel(factory = factory)
                            LoginScreen(
                                onLoginSuccess = { navController.navigate("devices") },
                                loginViewModel = loginViewModel
                            )
                        }
                        composable("devices") {
                            val factory = remember { DeviceListViewModelFactory(deviceRepository) }
                            val deviceListViewModel: DeviceListViewModel = viewModel(factory = factory)
                            DeviceListScreen(
                                deviceListViewModel = deviceListViewModel,
                                onDeviceClick = { device ->
                                    navController.navigate("device_details/${device.id}")
                                }
                            )
                        }
                        composable(
                            route = "device_details/{deviceId}",
                            arguments = listOf(navArgument("deviceId") { type = NavType.LongType })
                        ) {
                            val deviceId = it.arguments?.getLong("deviceId")
                            if (deviceId != null) {
                                val factory = remember { DeviceDetailsViewModelFactory(deviceRepository) }
                                val deviceDetailsViewModel: DeviceDetailsViewModel = viewModel(factory = factory)
                                DeviceDetailsScreen(
                                    deviceId = deviceId,
                                    deviceDetailsViewModel = deviceDetailsViewModel
                                )
                            } else {
                                // Handle error: deviceId not found
                                Text("Error: deviceId not found")
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
fun GreetingPreview() {
    WaterLevelMonitoringTheme {
        Text("Android")
    }
}