package com.example.myweatherc.ui.air_pollution_screen.ui_elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myweatherc.data.responses.geocoding.GeoObject

@Composable
fun LocationSection(geoObject: GeoObject) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = geoObject.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(geoObject.country)
            geoObject.state?.let { Text(it) }
            Text(
                text = "(${"%.4f".format(geoObject.lat)}, ${"%.4f".format(geoObject.lon)})",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}