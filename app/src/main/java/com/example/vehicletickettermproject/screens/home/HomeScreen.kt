package com.example.vehicletickettermproject.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vehicletickettermproject.components.BeginDatePicker
import com.example.vehicletickettermproject.components.BusJourneyItem
import com.example.vehicletickettermproject.components.PlaceDropdownMenu
import java.text.SimpleDateFormat

@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = viewModel()){
    val upcomingBusJourneys by homeViewModel.upcomingBusJourneys.collectAsState()


    val fromPlace by homeViewModel.fromPlace.collectAsState()
    val toPlace by homeViewModel.toPlace.collectAsState()
    val beginDate by homeViewModel.beginDate.collectAsState()
    val (showDatePicker, setShowDatePicker) = remember { mutableStateOf(false) }

    val allPlaces = homeViewModel.allPlaces

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        // From Place Dropdown
        PlaceDropdownMenu(
            selectedPlace = fromPlace,
            onPlaceSelected = { homeViewModel.updateFromPlace(it) },
            label = "From Place",
            allPlaces = allPlaces
        )

        Spacer(modifier = Modifier.height(4.dp))

        // To Place Dropdown
        PlaceDropdownMenu(selectedPlace = toPlace,
            onPlaceSelected = {homeViewModel.updateToPlace(it)},
            label = "To Place" , allPlaces = allPlaces)

        Spacer(modifier = Modifier.height(4.dp))

        // Begin Date Picker
        Button(onClick = { setShowDatePicker(true) }) {
            if(beginDate != null)Text("${SimpleDateFormat("yyyy-MM-dd").format(beginDate)}") else Text(text = "Select Date")
        }
        if (showDatePicker) {
            BeginDatePicker(
                context = LocalContext.current,
                onDateSelected = { year, month, day ->
                    homeViewModel.updateBeginDate(year, month, day)
                    setShowDatePicker(false)
                },
                onDismissRequest = { setShowDatePicker(false) }
            )
        }


        Button(colors = buttonColors(Color.Red),onClick = { homeViewModel.clearFilters() }) {
            Text(text = "Clear Filters")
        }



        Spacer(modifier = Modifier.height(8.dp))


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