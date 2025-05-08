package com.example.myweather.ui.geocoding_screen.ui_elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.myweather.ui.geocoding_screen.Hemisphere

@Composable
fun CoordinateInputs(
    lat: String,
    onLatChange: (String) -> Unit,
    lon: String,
    onLonChange: (String) -> Unit,
    latHemisphere: Hemisphere,
    onLatHemisphereChange: (Hemisphere) -> Unit,
    lonHemisphere: Hemisphere,
    onLonHemisphereChange: (Hemisphere) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = lat,
            onValueChange = onLatChange,
            label = { Text("Latitude°") },
            modifier = Modifier.weight(55f),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        CustomDropDownMenu(
            dropDownItems = listOf(Hemisphere.NORTH, Hemisphere.SOUTH),
            onItemClicked = onLatHemisphereChange,
            value = latHemisphere,
            label = "Hemisphere",
            modifier = Modifier.weight(45f)
        )
    }


    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = lon,
            onValueChange = onLonChange,
            label = { Text("Longitude°") },
            modifier = Modifier.weight(55f),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        CustomDropDownMenu(
            dropDownItems = listOf(Hemisphere.EAST, Hemisphere.WEST),
            onItemClicked = onLonHemisphereChange,
            value = lonHemisphere,
            label = "Hemisphere",
            modifier = Modifier.weight(45f)
        )
    }
}


fun parseCoordinate(value: String, hemisphere: Hemisphere): Double? {
    return try {
        val numericValue = value.toDouble()
        when (hemisphere) {
            Hemisphere.SOUTH, Hemisphere.WEST -> -numericValue
            else -> numericValue
        }
    } catch (e: NumberFormatException) {
        null
    }
}

