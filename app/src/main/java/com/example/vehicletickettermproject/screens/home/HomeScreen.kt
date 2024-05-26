package com.example.vehicletickettermproject.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ButtonDefaults.shape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vehicletickettermproject.R
import com.example.vehicletickettermproject.components.BeginDatePicker
import com.example.vehicletickettermproject.components.BusJourneyItem
import com.example.vehicletickettermproject.components.PlaceDropdownMenu
import com.example.vehicletickettermproject.screens.signin.SignInViewModel
import java.text.SimpleDateFormat

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = viewModel(), signInViewModel: SignInViewModel = viewModel()){
    val upcomingBusJourneys by homeViewModel.upcomingBusJourneys.collectAsState()

    val user by homeViewModel.user.collectAsState()
    val userName = "${user?.firstname ?: ""} ${user?.lastname ?: ""}"

    val fromPlace by homeViewModel.fromPlace.collectAsState()
    val toPlace by homeViewModel.toPlace.collectAsState()
    val beginDate by homeViewModel.beginDate.collectAsState()
    val (showDatePicker, setShowDatePicker) = remember { mutableStateOf(false) }

    val allPlaces = homeViewModel.allPlaces
    val backgroundColor = colorResource(id = R.color.secondary)

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopAppBar(
            title = { Text(text = userName) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorResource(id = R.color.primary),
                titleContentColor = colorResource(id = R.color.white)
            ),
            actions = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.logout), // Logout ikonu için kaynağı değiştirin
                        contentDescription = "Logout",
                        modifier = Modifier
                            .clickable {
                                homeViewModel.clearState()
                                signInViewModel.signOut(navController)
                            }
                            .padding(8.dp),
                        tint = Color.White
                    )
                }

            }
        )
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

            // To Place Dropdown
            PlaceDropdownMenu(selectedPlace = toPlace,
                onPlaceSelected = {homeViewModel.updateToPlace(it)},
                label = "To Place" , allPlaces = allPlaces)
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { setShowDatePicker(true) },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    if(beginDate != null)Text("${SimpleDateFormat("yyyy-MM-dd").format(beginDate)}")
                    else Text(
                        text = "Select Date",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight(800),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    )
                }

                Button(
                    onClick = { homeViewModel.clearFilters() },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.alternative_2)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Clear Filters",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight(800),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    )
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
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Available Bus Journeys",
                style = TextStyle(
                    fontSize = 27.sp,
                    fontWeight = FontWeight(800),
                    color = Color(0xFFD24545),
                    textAlign = TextAlign.Center,
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(upcomingBusJourneys) { upcomingBusJourney ->
                    BusJourneyItem(upcomingBusJourney,navController)
                }
            }

        }

    }


}