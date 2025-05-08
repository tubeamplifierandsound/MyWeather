package com.example.myweatherc.ui.base_screen.geo_item

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myweatherc.data.responses.geocoding.GeoObject
import java.util.Locale
import kotlin.math.abs
import androidx.compose.ui.unit.dp as dp1

@Composable
fun GeoInfo(
    geoObject: GeoObject,
    isExpanded: Boolean = false,
    onItemSelected: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val latText = formatCoordinate(geoObject.lat, isLatitude = true, isExpanded)
    val lonText = formatCoordinate(geoObject.lon, isLatitude = false,  isExpanded)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp1)
            .clickable(onClick = onItemSelected),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp1,
            pressedElevation = 4.dp1
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp1)
                .animateContentSize()
        ) {
            GeoExpandInfo(geoObject, latText, lonText)
            if (isExpanded) {
                ExpandInfo(geoObject)
            }
        }
    }
}

private fun formatCoordinate(value: Double, isLatitude: Boolean, isExpanded: Boolean): String {
    val direction = if (isLatitude) {
        if (value >= 0) "N" else "S"
    } else {
        if (value >= 0) "E" else "W"
    }
    if(isExpanded){
        return String.format(
            Locale.getDefault(),
            "%f° %s",
            abs(value),
            direction
        )
    }else{
        return String.format(
            Locale.getDefault(),
            "%.2f° %s",
            abs(value),
            direction
        )
    }

}
