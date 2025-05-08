package com.example.myweather.ui.air_pollution_screen.ui_elements

import androidx.compose.runtime.Composable
import com.example.myweather.data.responses.geocoding.GeoObject
import com.example.myweather.ui.base_screen.geo_item.GeoInfo

@Composable
fun LocationSection(geoObject: GeoObject) {
    if (geoObject!=null) {
        GeoInfo(geoObject,
            isExpanded = false
        )
    }
}