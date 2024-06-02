@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.example.vehicletickettermproject.screens.payment

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vehicletickettermproject.R
import com.example.vehicletickettermproject.VehicleTicketScreens
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
    val backgroundColor = colorResource(id = R.color.secondary)




    busJourney?.let { journey ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            TopAppBar(
                title = {
                    androidx.compose.material3.Text(
                        text = "CONFIRM PAYMENT",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight(800),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                },
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
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_arrow),
                        contentDescription = "Back",
                        tint = colorResource(id = R.color.primary),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.height(60.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(50.dp),
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
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
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
                                    modifier = Modifier.padding(top = 13.dp)
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
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
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
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(top = 15.dp, start = 20.dp)
                                ) {

                                    Text(
                                        text = "Seat: ",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight(800),
                                            color = Color(0xFFD24545),
                                            textAlign = TextAlign.Center,
                                        )
                                    )
                                    Text(
                                        text = "$selectedSeat",
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
                    }
                }


                    androidx.compose.material3.Button(
                        modifier = Modifier
                            .height(35.dp)
                            .width(200.dp)
                            .align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD24545)),
                        shape = RoundedCornerShape(12.dp),
                        onClick = {
                            homeViewModel.confirmReservation(journeyId, selectedSeat) {
                                navController.popBackStack()
                                navController.navigate(VehicleTicketScreens.search.name)
                            }
                        }

                    ) {
                        Text(text = "Confirm Payment", color = Color.White)
                    }
                }
            }
        }
    }

