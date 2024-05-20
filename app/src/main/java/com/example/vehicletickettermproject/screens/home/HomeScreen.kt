package com.example.vehicletickettermproject.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vehicletickettermproject.components.BusJourneyItem

@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = viewModel()){
    val user by homeViewModel.user.collectAsState()
    val upcomingBusJourneys by homeViewModel.upcomingBusJourneys.collectAsState()
    val isUserDataLoading by homeViewModel.isUserDataLoading.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isUserDataLoading) {
            CircularProgressIndicator()
        } else {
            user?.let {
                Text(text = "Welcome, ${it.firstname} ${it.lastname}!")
                Text(text = "Email: ${it.email}")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Available Bus Journeys", style = MaterialTheme.typography.headlineSmall)
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(upcomingBusJourneys) { upcomingBusJourney ->
                BusJourneyItem(upcomingBusJourney,navController)
            }
        }

    }

}