package com.example.myweather.ui.geocoding_screen.ui_elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.myweather.ui.geocoding_screen.SearchType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTypeSelector(
    selectedType: SearchType,
    onTypeSelected: (SearchType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val searchTypes = SearchType.values()

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it}
    ) {
        TextField(
            readOnly = true,
            value = selectedType.name.replace("_", " "),
            onValueChange = {},
            label = { Text("Search by", color = Color.DarkGray) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.LightGray,
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = Color.DarkGray
        )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            searchTypes.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type.name.replace("_", " ")) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}
