package com.example.myweatherc.ui.air_pollution_screen.ui_elements

import androidx.compose.runtime.Composable
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