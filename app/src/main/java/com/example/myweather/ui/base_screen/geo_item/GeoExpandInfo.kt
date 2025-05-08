package com.example.myweather.ui.base_screen.geo_item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myweather.data.responses.geocoding.GeoObject

@Composable
fun ExpandInfo(
    geoObject: GeoObject

) {
    Column(){
        Spacer(modifier = Modifier.height(12.dp))
        Divider(modifier = Modifier.padding(vertical = 8.dp))

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            geoObject.state?.let {
                InfoRow(
                    icon = Icons.Filled.Map,
                    title = "State",
                    value = it
                )
            }

            geoObject.zip?.let {
                InfoRow(
                    icon = Icons.Filled.Tag,
                    title = "Zip Code",
                    value = it
                )
            }

            geoObject.local_names?.let { names ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Local Names:",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                names.en?.let {
                    InfoRow(
                        icon = Icons.Filled.Language,
                        title = "English",
                        value = it
                    )
                }
                names.ru?.let {
                    InfoRow(
                        icon = Icons.Filled.Language,
                        title = "Russian",
                        value = it
                    )
                }
                names.be?.let {
                    InfoRow(
                        icon = Icons.Filled.Language,
                        title = "Belarusian",
                        value = it
                    )
                }
            }
        }
    }
}