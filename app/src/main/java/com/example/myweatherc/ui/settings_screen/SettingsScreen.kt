package com.example.myweatherc.ui.settings_screen

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweatherc.app_settings.Metrics
import com.example.myweatherc.app_settings.SettingsManager
import com.example.myweatherc.client.APISettings
import com.example.myweatherc.client.RetrofitClient
import com.example.myweatherc.data.responses.geocoding.GeoObject
import com.example.myweatherc.navigation.SettingsScreenNavigation
import com.example.myweatherc.ui.forecast_screen.ForcastType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    geoObject: MutableState<GeoObject?>,
    location : Location?
) {
    var expanded by remember { mutableStateOf(false) }
    var detectLocation by remember { mutableStateOf(SettingsManager.detectLocation) }

    val couroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            color = Color(0xFFEEEEEE),
            shadowElevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "System of measures",
                    color = Color.DarkGray,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        readOnly = true,
                        value = SettingsManager.metricsType.label,
                        onValueChange = {},
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        }
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        Metrics.values().forEach { metric ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = metric.label,
                                        color = Color.DarkGray
                                    )
                                },
                                onClick = {
                                    SettingsManager.saveMetricType(metric)
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = detectLocation,
                        onCheckedChange = { newVal ->
                            detectLocation = newVal
                            SettingsManager.saveDetectLocation(newVal)
                                          if(newVal == true){
                                              couroutineScope.launch {
                                                  geoObject.value = RetrofitClient.weatherAPIService.getGeoObjectByCoords(
                                                      latitude = location!!.latitude,
                                                      longitude = location!!.longitude,
                                                      apiKey = APISettings.API_KEY
                                                  )[0]
                                              }
                                          }},
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Gray,
                            uncheckedColor = Color.LightGray
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Determine current location",
                        color = Color.DarkGray,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

