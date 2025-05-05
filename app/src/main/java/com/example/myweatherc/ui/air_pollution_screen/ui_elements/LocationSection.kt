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
import com.example.myweatherc.ui.base_screen.geo_item.GeoInfo

@Composable
fun LocationSection(geoObject: GeoObject) {
    if (geoObject!=null) {
        GeoInfo(geoObject,
            isExpanded = false
        )
    }
}