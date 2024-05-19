package com.example.vehicletickettermproject.screens.reservations

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
import com.example.vehicletickettermproject.components.ReservationItem
import com.example.vehicletickettermproject.screens.home.HomeViewModel

@Composable
fun ReservationsScreen(navController: NavController, homeViewModel: HomeViewModel = viewModel()){
    val reservations by homeViewModel.reservations.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Your Reservations", style = MaterialTheme.typography.headlineSmall)
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(reservations) { (reservation, journey) ->
                Column(modifier = Modifier.padding(8.dp)) {
                    ReservationItem(reservation = reservation, journey = journey)
                }
            }
        }
    }
}