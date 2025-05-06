package com.example.myweatherc.ui.geocoding_screen.ui_elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight
//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import java.util.Locale
import kotlin.math.abs

@Composable
fun CoordinateInputs(
    lat: String,
    onLatChange: (String) -> Unit,
    lon: String,
    onLonChange: (String) -> Unit,
    latDirection: String,
    onLatDirectionChange: (String) -> Unit,
    lonDirection: String,
    onLonDirectionChange: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Широта с выбором направления
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = lat,
                onValueChange = onLatChange,
                label = { Text("Latitude") },
                modifier = Modifier.weight(2f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            DirectionDropdown(
                directions = listOf("N", "S"),
                selectedDirection = latDirection,
                onDirectionSelected = onLatDirectionChange,
                modifier = Modifier.weight(1f)
            )
        }

        // Долгота с выбором направления
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = lon,
                onValueChange = onLonChange,
                label = { Text("Longitude") },
                modifier = Modifier.weight(2f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            DirectionDropdown(
                directions = listOf("E", "W"),
                selectedDirection = lonDirection,
                onDirectionSelected = onLonDirectionChange,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectionDropdown(
    directions: List<String>,
    selectedDirection: String,
    onDirectionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            value = selectedDirection,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            directions.forEach { direction ->
                DropdownMenuItem(
                    text = { Text(direction) },
                    onClick = {
                        onDirectionSelected(direction)
                        expanded = false
                    }
                )
            }
        }
    }
}

// Функция для преобразования в числовое значение
fun parseCoordinate(value: String, direction: String): Double? {
    return try {
        val numericValue = value.toDouble()
        when (direction) {
            "S", "W" -> -numericValue
            else -> numericValue
        }
    } catch (e: NumberFormatException) {
        null
    }
}

// Обновленная функция форматирования
private fun formatCoordinate(
    value: Double,
    direction: String,
    isExpanded: Boolean
): String {
    val absValue = abs(value)
    return if (isExpanded) {
        String.format(Locale.getDefault(), "%f° %s", absValue, direction)
    } else {
        String.format(Locale.getDefault(), "%.2f° %s", absValue, direction)
    }
}

//@Composable
//fun CoordinateInputs(
//    lat: String,
//    onLatChange: (String) -> Unit,
//    lon: String,
//    onLonChange: (String) -> Unit
//) {
//    Row(
//        horizontalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        TextField(
//            value = lat,
//            onValueChange = onLatChange,
//            label = { Text("Latitude") },
//            modifier = Modifier.weight(1f),
//            keyboardOptions = KeyboardOptions.Default.copy(
//                keyboardType = KeyboardType.Number
//            )
//        )
//
//        TextField(
//            value = lon,
//            onValueChange = onLonChange,
//            label = { Text("Longitude") },
//            modifier = Modifier.weight(1f),
//            keyboardOptions = KeyboardOptions.Default.copy(
//                keyboardType = KeyboardType.Number
//            )
//        )
//    }
//}