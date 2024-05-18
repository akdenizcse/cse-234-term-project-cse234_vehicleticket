package com.example.vehicletickettermproject.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vehicletickettermproject.data.BusJourney


@Composable
fun BusJourneyItem(journey: BusJourney) {
    val cardColor = if (journey.isFull) Color.Gray else Color.White

    Card(
        backgroundColor = cardColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "From: ${journey.fromPlace} To: ${journey.toPlace}")
            Text(text = "Date: ${journey.getFormattedBeginDate()}")
            Text(text = "Time: ${journey.getFormattedBeginTime()}")
            Text(text = "Duration: ${journey.duration}")
            Text(text = "Price: ${journey.price} TL")
            Text(text = "Total Seats: ${journey.totalSeats}")
            Text(text = "Available Seats: ${journey.availableSeats.count { it.value }}")
        }
    }
}