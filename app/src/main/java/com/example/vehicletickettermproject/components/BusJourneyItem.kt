package com.example.vehicletickettermproject.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vehicletickettermproject.R
import com.example.vehicletickettermproject.VehicleTicketScreens
import com.example.vehicletickettermproject.data.BusJourney


@Composable
fun BusJourneyItem(journey: BusJourney,navController : NavController) {
    val cardColor = if (journey.isFull) Color.Gray else colorResource(id = R.color.alternative)

    Card(
        backgroundColor = cardColor,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("${VehicleTicketScreens.journeydetails.name}/${journey.id}")
            }

    ) {
        Column(
            modifier = Modifier
                .background(
                    color = cardColor,
                    shape = RoundedCornerShape(10.dp)
                )

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Text(
                        text = journey.fromPlace,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF764754),
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(top = 14.dp)
                    )
                    Text(
                        text = "->",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD24545),
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(top = 13.dp)
                    )
                    Text(
                        text = journey.toPlace,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF764754),
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(top = 14.dp)
                    )
                }
                Text(
                    text = "${journey.price} TL",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF764754),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(top = 14.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(19.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 15.dp)
                ) {
                    Text(
                        text = "Date:",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD24545),
                            textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = journey.getFormattedBeginDate(),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF764754),
                            textAlign = TextAlign.Center
                        )
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(19.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(top = 15.dp)
                    ) {
                        Text(
                            text = "Time:",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFD24545),
                                textAlign = TextAlign.Center
                            )
                        )
                        Text(
                            text = journey.getFormattedBeginTime(),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF764754),
                                textAlign = TextAlign.Center
                            )
                        )
                    }
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
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD24545),
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    text = journey.duration,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF764754),
                        textAlign = TextAlign.Center
                    )
                )
            }
            Spacer(modifier = Modifier.padding(11.dp))
        }
    }
}