package com.example.myweather.ui.air_pollution_screen.ui_elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AqiSection(aqi: Int) {
    val (color, description) = getAqiInfo(aqi)

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Air Quality Index",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$aqi $description",
                style = MaterialTheme.typography.displayMedium,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun getAqiInfo(aqi: Int): Pair<Color, String> {
    return when (aqi) {
        1 -> Color(0xFF4CAF50) to "Good"
        2 -> Color(0xFFF1BE23) to "Fair"
        3 -> Color(0xFFD78A20) to "Moderate"
        4 -> Color(0xFFA31717) to "Poor"
        5 -> Color(0xFFFF0000) to "Very Poor"
        else -> Color.Gray to "Unknown air quality"
    }
}