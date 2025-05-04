package com.example.myweatherc.ui.geocoding_screen.ui_elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myweatherc.data.responses.geocoding.GeoObject

@Composable
fun GeoObjectItem(
    geoObject: GeoObject,
    onItemSelected: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onItemSelected
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = geoObject.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Text("Country: ${geoObject.country}")
            geoObject.state?.let { Text("State: $it") }
            Text("Coordinates: (${geoObject.lat}, ${geoObject.lon})")
            geoObject.zip?.let { Text("Zip Code: $it") }
            geoObject.local_names?.let { names ->
                Spacer(modifier = Modifier.height(8.dp))
                Text("Local Names:", fontWeight = FontWeight.Medium)
                names.en?.let { Text("English: $it") }
                names.ru?.let { Text("Russian: $it") }
                names.be?.let { Text("Belarusian: $it") }
            }
        }
    }
}