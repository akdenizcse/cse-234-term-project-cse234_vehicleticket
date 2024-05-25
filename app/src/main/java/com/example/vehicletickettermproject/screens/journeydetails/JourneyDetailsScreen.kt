package com.example.vehicletickettermproject.screens.journeydetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.layout.ContentScale
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

    val backgroundColor = colorResource(id = R.color.secondary)

    busJourney?.let { journey ->

        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
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

                Button(
                    onClick = { showSeatingPlan = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD24545)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "See Seating Plan", color = Color.White)
                }

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
            if (showSeatingPlan) {
                PhotoDialog(onDismiss = { showSeatingPlan = false })
            }
        }
    }
}
@Composable
fun PhotoDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Seating Plan", color = Color(0xFFD24545))
        },
        text = {
            Image(
                painter = painterResource(id = R.drawable.grid),
                contentDescription = "Your photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Fit
            )
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("OK", color = Color(0xFFD24545))
            }
        },
        containerColor = Color(0xFFE6BAA3)
    )
}
