package com.example.vehicletickettermproject.screens.journeydetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                title = { Text(
                    text = "JOURNEY DETAILS",
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
                    .padding(16.dp)
            ) {
                IconButton(
                    onClick = { navController.popBackStack()}
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_arrow),
                        contentDescription = "Back",
                        tint = colorResource(id = R.color.primary),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .width(286.dp)
                            .height(115.dp)
                            .background(
                                colorResource(id = R.color.alternative),
                                shape = RoundedCornerShape(10.dp)
                            )
                    ){
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(7.dp)
                            ) {
                                Text(
                                    text = "${journey.fromPlace}",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight(800),
                                        color = Color(0xFF764754),
                                        textAlign = TextAlign.Center,
                                    ),
                                    modifier = Modifier
                                        .padding(top = 14.dp)
                                )
                                Text(
                                    text = "->",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight(800),
                                        color = Color(0xFFD24545),
                                        textAlign = TextAlign.Center,
                                    ),
                                    modifier = Modifier
                                        .padding(top = 13.dp)
                                )
                                Text(
                                    text = "${journey.toPlace}",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight(800),
                                        color = Color(0xFF764754),
                                        textAlign = TextAlign.Center,
                                    ),
                                    modifier = Modifier
                                        .padding(top = 14.dp)
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "${journey.price} TL",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight(800),
                                        color = Color(0xFF764754),
                                        textAlign = TextAlign.Center,
                                    ),
                                    modifier = Modifier.padding(top = 14.dp)
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 15.dp, start = 16.dp)
                            ) {
                                Text(
                                    text = "Date:",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight(800),
                                        color = Color(0xFFD24545),
                                        textAlign = TextAlign.Center,
                                    )
                                )
                                Text(
                                    text = "${journey.getFormattedBeginDate()}",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight(800),
                                        color = Color(0xFF764754),
                                        textAlign = TextAlign.Center,
                                    )
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 15.dp, start = 19.dp)
                            ) {
                                Text(
                                    text = "Time:",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight(800),
                                        color = Color(0xFFD24545),
                                        textAlign = TextAlign.Center,
                                    )
                                )
                                Text(
                                    text = "${journey.getFormattedBeginTime()}",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight(800),
                                        color = Color(0xFF764754),
                                        textAlign = TextAlign.Center,
                                    )
                                )
                            }

                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 15.dp, start = 15.dp)
                        ) {
                            Text(
                                text = "Duration:",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight(800),
                                    color = Color(0xFFD24545),
                                    textAlign = TextAlign.Center,
                                )
                            )
                            Text(
                                text = "${journey.duration}",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight(800),
                                    color = Color(0xFF764754),
                                    textAlign = TextAlign.Center,
                                )
                            )
                        }
                    }
                }

                Button(
                    onClick = { showSeatingPlan = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD24545)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "See Seating Plan", color = Color.White, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Select one of the available seats",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight(800),
                        color = Color(0xFF764754),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(15.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(journey.availableSeats.filter { it.value == null }.keys.map{it.toInt()}.sorted().toList()) { seatNumber ->
                        Text(
                            text = "Seat $seatNumber",
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    navController.navigate("${VehicleTicketScreens.payment.name}/$journeyId/$seatNumber")
                                },
                            color = Color(0xFFD24545),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
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
