package com.example.vehicletickettermproject.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PlaceDropdownMenu(
    selectedPlace: String?, // if no place is selected, make it null so its easier to filter by nothing (show all busjourneys)
    onPlaceSelected: (String?) -> Unit,
    label: String,
    allPlaces: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = selectedPlace ?: label ,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(16.dp)
                .border(1.dp, Color.Gray)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            (listOf("ANY") + allPlaces).forEach { item ->
                DropdownMenuItem(onClick = {
                    onPlaceSelected(if (item == "ANY") null else item)
                    expanded = false
                }) {
                    Text(text = item)
                }
            }
        }
    }
}