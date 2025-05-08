package com.example.myweather.ui.map_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.R
import coil3.compose.AsyncImage

enum class MapLayer(val apiName: String, val label: String) {
    TEMPERATURE("temp", "Temperature"),
    CLOUDS("clouds", "Clouds"),
    PRECIPITATION("precipitation", "Precipitation"),
    PRESSURE("pressure", "Pressure"),
    WIND("wind", "Wind")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen() {
    var expanded by remember { mutableStateOf(false) }
    var selectedLayer by remember { mutableStateOf(MapLayer.TEMPERATURE) }

    val mapUrl = remember(selectedLayer) {
        "https://tile.openweathermap.org/map/${selectedLayer.apiName}_new/0/0/0.png?appid=e7fb106ce17fa922a2b57b9a58d67a04"
    }

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
                    text = "Map Layer",
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
                        value = selectedLayer.label,
                        onValueChange = {},
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        MapLayer.values().forEach { layer ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = layer.label,
                                        color = Color.DarkGray
                                    )
                                },
                                onClick = {
                                    selectedLayer = layer
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 4.dp
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.world),
                    contentDescription = "World map background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                AsyncImage(
                    model = mapUrl,
                    contentDescription = "Temperature overlay",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { alpha = 0.98f }
                )
            }
        }

    }
}