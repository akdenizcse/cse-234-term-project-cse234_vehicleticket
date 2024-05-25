package com.example.vehicletickettermproject.screens.journeydetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vehicletickettermproject.R
import com.example.vehicletickettermproject.VehicleTicketScreens
import com.example.vehicletickettermproject.screens.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JourneyDetailsScreen(navController: NavController, journeyId: String, homeViewModel: HomeViewModel = viewModel()) {
    val busJourneys by homeViewModel.busJourneys.collectAsState()
    val busJourney = busJourneys.find { it.id == journeyId }
    var showSeatingPlan by remember { mutableStateOf(false) }

    busJourney?.let { journey ->

        Column (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TopAppBar(
                title = { Text("JOURNEY DETAILS", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.primary),
                    titleContentColor = colorResource(id = R.color.white)
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.back_arrow), // Placeholder icon
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
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
}