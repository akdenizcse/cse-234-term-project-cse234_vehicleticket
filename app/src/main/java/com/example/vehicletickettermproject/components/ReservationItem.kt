package com.example.vehicletickettermproject.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vehicletickettermproject.data.BusJourney
import com.example.vehicletickettermproject.data.Reservation


@Composable
fun ReservationItem(reservation: Reservation, journey: BusJourney) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "From: ${journey.fromPlace} To: ${journey.toPlace}")
            Text(text = "Date: ${journey.getFormattedBeginDate()} Time: ${journey.getFormattedBeginTime()}")
            Text(text = "Reserved Seat: ${reservation.seatNumber}")
            Text(text = "Duration: ${journey.duration}")
            Text(text = "Price: ${journey.price} TL")
        }
    }
}