package com.example.vehicletickettermproject.screens.reservations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vehicletickettermproject.R
import com.example.vehicletickettermproject.components.ReservationItem
import com.example.vehicletickettermproject.screens.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationsScreen(navController: NavController, homeViewModel: HomeViewModel = viewModel()){
    val upcomingTravels by homeViewModel.upcomingTravels.collectAsState()

    val backgroundColor = colorResource(id = R.color.secondary)

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        TopAppBar(
            title = { Text(
                text = "RESERVATIONS",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight(800),
                    color = Color(0xFFFFFFFF),
                )
            ) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorResource(id = R.color.primary),
                titleContentColor = colorResource(id = R.color.white)
            )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(upcomingTravels) { (upcomingTravel, upcomingBusJourney) ->
                    Column() {
                        ReservationItem(
                            reservation = upcomingTravel,
                            journey = upcomingBusJourney,
                            onCancelReservation = { homeViewModel.cancelReservation(it) },
                            isCancellable = true)
                    }
                }
            }
        }
    }

}