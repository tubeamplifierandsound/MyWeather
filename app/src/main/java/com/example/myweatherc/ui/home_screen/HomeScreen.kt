package com.example.myweatherc.ui.theme.home_screen

import com.example.myweatherc.ui.models.WeatherJSONViewModel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myweatherc.navigation.HomeScreenNavigation

@Composable
fun HomeScreen(
    navData: HomeScreenNavigation,

) {
    val viewModel: WeatherJSONViewModel = viewModel()
    val data = viewModel.weatherData.observeAsState().value

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text("Home Screen")
        if (data != null) {
            Text(
                text = data,
//                modifier = modifier
//                    .padding(18.dp)
            )
        }
    }
}
