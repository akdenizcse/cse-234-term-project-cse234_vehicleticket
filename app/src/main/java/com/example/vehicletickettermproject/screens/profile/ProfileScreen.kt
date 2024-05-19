package com.example.vehicletickettermproject.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
fun ProfileScreen(navController: NavController, homeViewModel: HomeViewModel = viewModel()){
    val user by homeViewModel.user.collectAsState()
    val pastTravels by homeViewModel.pastTravels.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        user?.let {
            Text(text = "Welcome, ${it.firstname} ${it.lastname}!")
            Text(text = "Email: ${it.email}")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Past Travels", style = MaterialTheme.typography.headlineSmall)
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(pastTravels) { (pastReservation, pastJourney) ->
                ReservationItem(reservation = pastReservation, journey = pastJourney)
            }
        }
    }
}