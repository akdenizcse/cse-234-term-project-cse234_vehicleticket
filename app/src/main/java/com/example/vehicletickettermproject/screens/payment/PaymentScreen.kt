package com.example.vehicletickettermproject.screens.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vehicletickettermproject.screens.home.HomeViewModel

@Composable
fun PaymentScreen(
    navController: NavController,
    journeyId: String,
    selectedSeat: String,
    homeViewModel: HomeViewModel = viewModel()
) {
    val busJourneys by homeViewModel.busJourneys.collectAsState()
    val busJourney = busJourneys.find { it.id == journeyId }




    busJourney?.let { journey ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Confirm Payment", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "From: ${journey.fromPlace}")
            Text(text = "To: ${journey.toPlace}")
            Text(text = "Date: ${journey.getFormattedBeginDate()}")
            Text(text = "Time: ${journey.getFormattedBeginTime()}")
            Text(text = "Seat: $selectedSeat")
            Text(text = "Price: ${journey.price} TL")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                homeViewModel.confirmReservation(journeyId, selectedSeat) {
                    navController.popBackStack()
                    navController.navigate("home")
                }
            }) {
                Text(text = "Confirm Payment")
            }
        }
    }
}