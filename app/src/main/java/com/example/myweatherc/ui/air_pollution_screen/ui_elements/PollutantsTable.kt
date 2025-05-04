package com.example.myweatherc.ui.air_pollution_screen.ui_elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myweatherc.data.responses.air_pollution.objects.Components

@Composable
fun PollutantsTable(components: Components) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Pollutant Concentrations",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            PollutantRow(
                name = "Carbon monoxide (CO)",
                value = components.co,
                unit = "μg/m³"
            )
            PollutantRow(
                name = "Nitrogen monoxide (NO)",
                value = components.no,
                unit = "μg/m³"
            )
            PollutantRow(
                name = "Nitrogen dioxide (NO₂)",
                value = components.no2,
                unit = "μg/m³"
            )
            PollutantRow(
                name = "Ozone (O₃)",
                value = components.o3,
                unit = "μg/m³"
            )
            PollutantRow(
                name = "Sulphur dioxide (SO₂)",
                value = components.so2,
                unit = "μg/m³"
            )
            PollutantRow(
                name = "Fine particles (PM2.5)",
                value = components.pm2_5,
                unit = "μg/m³"
            )
            PollutantRow(
                name = "Coarse particles (PM10)",
                value = components.pm10,
                unit = "μg/m³"
            )
            PollutantRow(
                name = "Ammonia (NH₃)",
                value = components.nh3,
                unit = "μg/m³"
            )
        }
    }
}

@Composable
private fun PollutantRow(name: String, value: Double, unit: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "${"%.1f".format(value)} $unit",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}