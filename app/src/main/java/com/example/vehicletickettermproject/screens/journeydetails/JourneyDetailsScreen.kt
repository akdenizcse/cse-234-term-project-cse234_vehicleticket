package com.example.vehicletickettermproject.screens.journeydetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vehicletickettermproject.VehicleTicketScreens
import com.example.vehicletickettermproject.screens.home.HomeViewModel

@Composable
fun JourneyDetailsScreen(navController: NavController, journeyId: String, homeViewModel: HomeViewModel = viewModel()) {
    val busJourneys by homeViewModel.busJourneys.collectAsState()
    val busJourney = busJourneys.find { it.id == journeyId }

    busJourney?.let { journey ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Journey Details", style = MaterialTheme.typography.headlineSmall)
            Text(text = "From: ${journey.fromPlace}")
            Text(text = "To: ${journey.toPlace}")
            Text(text = "Date: ${journey.getFormattedBeginDate()}")
            Text(text = "Time: ${journey.getFormattedBeginTime()}")
            Text(text = "Duration: ${journey.duration}")
            Text(text = "Price: ${journey.price} TL")

            Text(text = "Select one of the available seats", style = MaterialTheme.typography.headlineSmall)
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(journey.availableSeats.filter { it.value == null }.keys.toList()) { seatNumber ->
                    Text(text = "Seat $seatNumber", modifier = Modifier.padding(8.dp).
                    clickable{
                        navController.navigate("${VehicleTicketScreens.payment.name}/$journeyId/$seatNumber")
                    })
                }
            }
        }
    }
}